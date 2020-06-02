package com.ebibli.controller;

import com.ebibli.dto.LivreDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class ReservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(value = "/reservation/creation")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationDto reservation) {
        LOGGER.info("Dans ReservationController - createReservation");

        ReservationDto newReservation = reservationService.createReservation(reservation);
        if (newReservation != null) {
            return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newReservation.getId())
                    .toUri())
                    .build();
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/reservation/suppression/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable ("reservationId") Integer reservationId) {
        LOGGER.info("Dans ReservationController - cancelReservation");
        reservationService.cancelReservation(reservationId);
        return new ResponseEntity<>((HttpStatus.OK));
    }

    @GetMapping(value = "/reservations/ouvrage/{ouvrageId}")
    public ResponseEntity<List<ReservationDto>> getAllReservationsByOuvrage(@PathVariable ("ouvrageId") Integer ouvrageId) {
        LOGGER.info("Dans ReservationController - getAllReservationsByOuvrage");
        return new ResponseEntity<>(reservationService.getAllReservationsByOuvrage(ouvrageId), HttpStatus.OK);
    }

    @GetMapping(value = "/reservations/abonne/{emprunteurId}")
    public ResponseEntity<List<ReservationDto>> getAllReservationsByEmprunteur(@PathVariable ("emprunteurId") Integer emprunteurId) {
        LOGGER.info("Dans ReservationController - getAllReservationsByEmprunteur");
        return new ResponseEntity<>(reservationService.getAllReservationsByEmprunteur(emprunteurId), HttpStatus.OK);
    }

    @PostMapping(value = "/reservation/ notification")
    public ResponseEntity<Void> checkNextReservation(@RequestBody LivreDto livre) {
        LOGGER.info("Dans ReservationController - checkNextReservation");
        reservationService.checkNextReservation(livre);
        return new ResponseEntity<>((HttpStatus.OK));
    }
}
