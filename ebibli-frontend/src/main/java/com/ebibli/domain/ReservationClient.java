package com.ebibli.domain;

import com.ebibli.dto.ReservationDto;

import java.util.List;

public interface ReservationClient {
    List<ReservationDto> getReservationsByUtilisateur(Integer utilisateurId);

    List<ReservationDto> getReservationsByOuvrage(Integer ouvrageId);

    void cancelReservation(Integer reservationId);
}
