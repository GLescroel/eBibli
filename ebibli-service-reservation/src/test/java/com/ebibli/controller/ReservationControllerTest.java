package com.ebibli.controller;

import com.ebibli.dto.ReservationDto;
import com.ebibli.service.ReservationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class ReservationControllerTest {

    private ReservationService reservationService = Mockito.mock(ReservationService.class);
    private ReservationController reservationController = new ReservationController(reservationService);

    @Test
    void testShouldGetAllReservationsByOuvrage() {
        List<ReservationDto> reservationsList = new ArrayList<>();
        reservationsList.add(new ReservationDto());
        reservationsList.add(new ReservationDto());
        Mockito.when(reservationService.getAllReservationsByOuvrage(Mockito.anyInt())).thenReturn(reservationsList);

        List<ReservationDto> reservations = reservationController.getAllReservationsByOuvrage(2).getBody();

        Assert.assertEquals(2, reservations.size());
    }

}