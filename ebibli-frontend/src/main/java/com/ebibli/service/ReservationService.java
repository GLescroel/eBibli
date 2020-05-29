package com.ebibli.service;

import com.ebibli.domain.ReservationClient;
import com.ebibli.dto.ReservationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationClient reservationClient;

    public ReservationService(ReservationClient reservationClient) {
        this.reservationClient = reservationClient;
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
}