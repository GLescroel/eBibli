package com.ebibli.infrastructure.rest.reservation;

import com.ebibli.domain.ReservationClient;
import com.ebibli.dto.ReservationDto;

import java.util.List;

public class RestReservationClient implements ReservationClient {

    private final ReservationClientApi reservationClientApi;

    public RestReservationClient(ReservationClientApi reservationClientApi) {
        this.reservationClientApi = reservationClientApi;
    }

    @Override
    public List<ReservationDto> getReservationsByUtilisateur(Integer idUtilisateur) {
        return reservationClientApi.getReservationsByUtiisateur(idUtilisateur);
    }

    @Override
    public List<ReservationDto> getReservationsByOuvrage(Integer ouvrageId) {
        return reservationClientApi.getReservationsByOuvrage(ouvrageId);
    }

    @Override
    public void cancelReservation(Integer reservationId) {
        reservationClientApi.cancelReservation(reservationId);
    }

    @Override
    public void createReservation(ReservationDto reservation) {
        reservationClientApi.createReservation(reservation);
    }
}
