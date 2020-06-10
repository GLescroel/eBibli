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
    public List<ReservationDto> getAllReservationToCancel() {
        return reservationClientApi.getAllReservationToCancel();
    }

    @Override
    public void cancelReservation(Integer reservationId) {
        reservationClientApi.cancelReservation(reservationId);
    }
}
