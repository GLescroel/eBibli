package com.ebibli.service;

import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.exception.FunctionalException;
import com.ebibli.infrastructure.rest.emprunt.EmpruntClientApi;
import com.ebibli.infrastructure.rest.livre.LivreClientApi;
import com.ebibli.mapper.ReservationMapper;
import com.ebibli.repository.ReservationRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private static final ReservationMapper RESERVATION_MAPPER = Mappers.getMapper(ReservationMapper.class);

    @Autowired
    private LivreClientApi livreClientApi;
    @Autowired
    private EmpruntClientApi empruntClientApi;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationDto> getAllReservationsByOuvrage(Integer ouvrageId) {
        return RESERVATION_MAPPER.reservationsToDtos(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(ouvrageId));
    }

    public List<ReservationDto> getAllReservationsByEmprunteur(Integer emprunteurId) {
        return RESERVATION_MAPPER.reservationsToDtos(reservationRepository.findAllByEmprunteur_IdOrderByDateReservation(emprunteurId));
    }

    public ReservationDto createReservation(ReservationDto reservation) throws FunctionalException {
        isReservationRequestValid(reservation);
        reservation.setDateReservation(Date.valueOf(LocalDate.now()));
        reservation.setAlerte(false);
        reservation.setDateAlerte(null);
        return RESERVATION_MAPPER.map(reservationRepository.save(RESERVATION_MAPPER.map(reservation)));
    }

    private boolean isReservationRequestValid(ReservationDto newReservation) throws FunctionalException {
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
        if (reservations.size() >= 2 * livreClientApi.getLivresByOuvrage(newReservation.getOuvrage().getId()).size()) {
            throw new FunctionalException(
                    "Le nombre macimal de réservations pour cet ouvrage est déjà atteint");
        }
        //check if the emprunteur has not the same book already
        List<EmpruntDto> empruntsEnCours = empruntClientApi.findEmpruntsEnCoursByUtilisateur(newReservation.getEmprunteur().getId());
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
}
