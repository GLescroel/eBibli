package com.ebibli.service;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.LivreClient;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.exception.FunctionalException;
import com.ebibli.mapper.ReservationMapper;
import com.ebibli.repository.ReservationRepository;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

    private static final ReservationMapper RESERVATION_MAPPER = Mappers.getMapper(ReservationMapper.class);

    @Autowired
    private LivreClient livreClient;
    @Autowired
    private EmpruntClient empruntClient;

    @Autowired
    private ReservationRepository reservationRepository;

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

    public void cancelReservation(Integer reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public void checkNextReservation(LivreDto livre) {
        List<ReservationDto> reservations = getAllReservationsByOuvrage(livre.getOuvrage().getId());
        for (ReservationDto reservation : reservations) {
            if (!reservation.getAlerte()) {
                sendAlert(reservation.getEmprunteur());
                reservation.setAlerte(true);
                reservation.setDateAlerte(Date.valueOf(LocalDate.now()));
                reservation.setDateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)));
                livreClient.setLivreReserve(livre.getId(), reservation.getEmprunteur().getId());
                saveReservation(reservation);
                return;
            }
        }
    }

    private void sendAlert(UtilisateurDto emprunteur) {
        LOGGER.info(">>>>>>>>>>> ENVOI EMAIL {} <<<<<<<<<<<<<<<<<<<", emprunteur.getEmail());
    }

    public void cancelReservation(Integer ouvrageId, Integer emprunteurId) {
        List<ReservationDto> reservations = getAllReservationsByOuvrage(ouvrageId);
        for (ReservationDto reservation : reservations) {
            if (reservation.getEmprunteur().getId().equals(emprunteurId)) {
                cancelReservation(reservation.getId());
            }
        }
    }
}
