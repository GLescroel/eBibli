package com.ebibli.service;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.LivreClient;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.emailconfiguration.EmailConfiguration;
import com.ebibli.exception.FunctionalException;
import com.ebibli.mapper.ReservationMapper;
import com.ebibli.repository.ReservationRepository;
import com.ebibli.transport.MyTransport;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Service
public class ReservationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

    private static final ReservationMapper RESERVATION_MAPPER = Mappers.getMapper(ReservationMapper.class);

    private LivreClient livreClient;
    private EmpruntClient empruntClient;
    private EmailConfiguration emailConfiguration;
    private ReservationRepository reservationRepository;
    private  MyTransport myTransport;

    public ReservationService(ReservationRepository reservationRepository, LivreClient livreClient, EmpruntClient empruntClient, EmailConfiguration emailConfiguration, MyTransport myTransport) {
        this.reservationRepository = reservationRepository;
        this.livreClient = livreClient;
        this.empruntClient = empruntClient;
        this.emailConfiguration = emailConfiguration;
        this.myTransport = myTransport;
    }

    public List<ReservationDto> getAllReservationsByOuvrage(Integer ouvrageId) {
        return RESERVATION_MAPPER.reservationsToDtos(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(ouvrageId));
    }

    public List<ReservationDto> getAllReservationsByEmprunteur(Integer emprunteurId) {
        return RESERVATION_MAPPER.reservationsToDtos(reservationRepository.findAllByEmprunteur_IdOrderByDateReservation(emprunteurId));
    }

    public ReservationDto saveReservation(ReservationDto reservation) {
        return RESERVATION_MAPPER.map(reservationRepository.save(RESERVATION_MAPPER.map(reservation)));
    }

    public ReservationDto createReservation(ReservationDto reservation) {
        isReservationRequestValid(reservation);
        reservation.setDateReservation(Date.valueOf(LocalDate.now()));
        reservation.setAlerte(false);
        reservation.setDateAlerte(null);
        return saveReservation(reservation);
    }

    private boolean isReservationRequestValid(ReservationDto newReservation) {
        if (newReservation.getOuvrage() == null || newReservation.getEmprunteur() == null) {
            throw new FunctionalException(
                    "Une réservation doit contenir un ouvrage et un emprunteur");
        }
        //check if the same reservation was not made already
        List<ReservationDto> reservations = getAllReservationsByOuvrage(newReservation.getOuvrage().getId());
        for (ReservationDto reservation : reservations) {
            if (reservation.getEmprunteur().getId().equals(newReservation.getEmprunteur().getId()) &&
                    reservation.getOuvrage().getId().equals(newReservation.getOuvrage().getId())) {
                throw new FunctionalException(
                        "Une réservation identique existe déjà");
            }
        }
        //check if the limit of reservation is not reached
        if (reservations.size() >= 2 * livreClient.getLivresByOuvrage(newReservation.getOuvrage().getId()).size()) {
            throw new FunctionalException(
                    "Le nombre macimal de réservations pour cet ouvrage est déjà atteint");
        }
        //check if the emprunteur has not the same book already
        List<EmpruntDto> empruntsEnCours = empruntClient.findEmpruntsEnCoursByUtilisateur(newReservation.getEmprunteur().getId());
        for (EmpruntDto empruntEnCours : empruntsEnCours) {
            if (empruntEnCours.getLivre().getOuvrage().getId().equals(newReservation.getOuvrage().getId())) {
                throw new FunctionalException(
                        "Un exemplaire de cet ouvrage est en cours d'emprunt par cet abonné");
            }
        }
        return true;
    }

    public void cancelReservation(Integer reservationId) throws MessagingException {
        ReservationDto reservation = RESERVATION_MAPPER.map(reservationRepository.getOne(reservationId));
        if (Boolean.TRUE.equals(reservation.getAlerte())) {
            for (LivreDto livre : livreClient.getLivresByOuvrage(reservation.getOuvrage().getId())) {
                if (livre.getNextEmprunteur() != null && livre.getNextEmprunteur().getId().equals(reservation.getEmprunteur().getId())) {
                    checkNextReservation(livre);
                }
            }
        }
        reservationRepository.deleteById(reservationId);
    }

    public void checkNextReservation(LivreDto livre) throws MessagingException {
        List<ReservationDto> reservations = getAllReservationsByOuvrage(livre.getOuvrage().getId());
        for (ReservationDto reservation : reservations) {
            if (Boolean.FALSE.equals(reservation.getAlerte())) {
                sendAlert(reservation.getEmprunteur());
                reservation.setAlerte(true);
                reservation.setDateAlerte(Date.valueOf(LocalDate.now()));
                reservation.setDateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)));
                livreClient.setLivreReserve(livre.getId(), reservation.getEmprunteur().getId());
                saveReservation(reservation);
                return;
            }
        }
        livreClient.setLivreReserve(livre.getId(), 0);
    }

    private void sendAlert(UtilisateurDto emprunteur) throws MessagingException {
        LOGGER.info(">>>>>>>>>>> ENVOI EMAIL {} <<<<<<<<<<<<<<<<<<<", emprunteur.getEmail());

        Properties prop = new Properties();
        prop.put("mail.transport.protocol", emailConfiguration.getProtocol());
        prop.put("mail.smtp.auth", emailConfiguration.getAuth());
        prop.put("mail.smtp.starttls.enable", emailConfiguration.getStarttls());
        prop.put("mail.smtp.host", emailConfiguration.getHost());
        prop.put("mail.smtp.port", emailConfiguration.getPort());

        Session session = Session.getDefaultInstance(prop, null);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("eBibli@oc.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(emprunteur.getEmail()));
        message.setSubject("eBibli : votre réservation");

        String body = "Votre livre est disponible";
        message.setText(body, "UTF-8", "html");

        myTransport.send(message);
    }

    public void cancelReservation(Integer ouvrageId, Integer emprunteurId) throws MessagingException {
        List<ReservationDto> reservations = getAllReservationsByOuvrage(ouvrageId);
        for (ReservationDto reservation : reservations) {
            if (reservation.getEmprunteur().getId().equals(emprunteurId)) {
                cancelReservation(reservation.getId());
            }
        }
    }

    public List<ReservationDto> getAllReservationsToCancel() {
        return RESERVATION_MAPPER.reservationsToDtos(reservationRepository.findAllByDateRetraitMaxBeforeOrderById(Date.valueOf(LocalDate.now())));
    }
}
