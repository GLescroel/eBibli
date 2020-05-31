package com.ebibli.service;

import com.ebibli.domain.OuvrageClient;
import com.ebibli.domain.ReservationClient;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.ReservationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationClient reservationClient;
    private final UtilisateurClient utilisateurClient;
    private final OuvrageClient ouvrageClient;

    public ReservationService(ReservationClient reservationClient,
                              OuvrageClient ouvrageClient,
                              UtilisateurClient utilisateurClient) {
        this.reservationClient = reservationClient;
        this.ouvrageClient = ouvrageClient;
        this.utilisateurClient = utilisateurClient;
    }

    public List<ReservationDto> getReservationsByUtilisateur(Integer idUtilisateur) {
        List<ReservationDto> reservations = reservationClient.getReservationsByUtilisateur(idUtilisateur);
        for (ReservationDto reservation : reservations) {
            List<ReservationDto> reservationsOuvrage = reservationClient.getReservationsByOuvrage(reservation.getOuvrage().getId());
            for (int position = 0 ; position < reservationsOuvrage.size(); position++ ) {
                if (idUtilisateur.equals(reservationsOuvrage.get(position).getEmprunteur().getId())) {
                    reservation.setPosition(position+1);
                }
            }
        }
        return reservations;
    }

    public void cancelReservation(Integer reservationId) {
        reservationClient.cancelReservation(reservationId);
    }

    public void createReservation(Integer utilisateurId, Integer ouvrageId) {
        ReservationDto reservation = new ReservationDto()
                .builder()
                .ouvrage(ouvrageClient.getOuvrageById(ouvrageId))
                .emprunteur(utilisateurClient.getUtilisateurById(utilisateurId))
                .build();
        reservationClient.createReservation(reservation);
    }

    public List<ReservationDto> getReservationsByOuvrage(Integer ouvrageId) {
        return reservationClient.getReservationsByOuvrage(ouvrageId);
    }
}