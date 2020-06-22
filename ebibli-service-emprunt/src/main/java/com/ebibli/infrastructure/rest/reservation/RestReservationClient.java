package com.ebibli.infrastructure.rest.reservation;

import com.ebibli.domain.ReservationClient;
import com.ebibli.dto.LivreDto;

public class RestReservationClient implements ReservationClient {

    private final ReservationClientApi reservationClientApi;

    public RestReservationClient(ReservationClientApi reservationClientApi) {
        this.reservationClientApi = reservationClientApi;
    }

    @Override
    public void notificationRetourLivre(LivreDto livre) {
        reservationClientApi.notificationLivreEnRetour(livre);
    }

    @Override
    public void cancelReservation(Integer ouvrageId, Integer emprunteurId) {
        reservationClientApi.cancelReservation(ouvrageId, emprunteurId);
    }
}
