package com.ebibli.domain;

import com.ebibli.dto.LivreDto;

public interface ReservationClient {

    void notificationRetourLivre(LivreDto livre);
    void cancelReservation(Integer id, Integer emprunteurId);
}
