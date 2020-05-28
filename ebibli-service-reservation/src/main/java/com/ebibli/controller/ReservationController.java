package com.ebibli.controller;

import com.ebibli.dto.ReservationDto;
import com.ebibli.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/reservations/ouvrage/{ouvrageId}")
    public ResponseEntity<List<ReservationDto>> getAllReservationsByOuvrage(@PathVariable ("ouvrageId") Integer ouvrageId) {
        LOGGER.info("Dans ReservationController - getAllReservationsByOuvrage");
        return new ResponseEntity<>(reservationService.getAllReservationsByOuvrage(ouvrageId), HttpStatus.OK);
    }
}
