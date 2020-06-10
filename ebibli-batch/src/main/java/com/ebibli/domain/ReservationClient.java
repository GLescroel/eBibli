package com.ebibli.domain;

import com.ebibli.dto.ReservationDto;

import java.util.List;

public interface ReservationClient {
    List<ReservationDto> getAllReservationToCancel();

    void cancelReservation(Integer reservationId);
}
