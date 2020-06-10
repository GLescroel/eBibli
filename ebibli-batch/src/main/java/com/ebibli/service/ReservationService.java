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

    public List<ReservationDto> getAllReservationToCancel() {
        return reservationClient.getAllReservationToCancel();
    }

    public void cancelReservation(Integer reservationId) {
        reservationClient.cancelReservation(reservationId);
    }
}
