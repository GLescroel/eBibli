package com.ebibli.controller;

import com.ebibli.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    private static final String ATTRIBUTE_RESERVATIONS = "reservations";
    private static final String VIEW_RESERVATION = "reservation";

    @Autowired
    private ReservationService reservationService;

    @GetMapping(value = "/Utilisateur/{id}/mesReservations")
    public String viewMyReservations(Model model, @PathVariable("id") Integer idUtilisateur) {
        LOGGER.info(">>>>> Dans ReservationController - viewMyReservations");

        model.addAttribute(ATTRIBUTE_RESERVATIONS, reservationService.displayReservationsByUtilisateur(idUtilisateur));
        return VIEW_RESERVATION;
    }

    @PostMapping(value = "/reservation/{utilisateurId}/annulation/{reservationId}")
    public String cancelReservation(Model model, @PathVariable ("utilisateurId") Integer utilisateurId,
                                    @PathVariable ("reservationId") Integer reservationId) {
        LOGGER.info(">>>>> Dans ReservationController - cancelReservation");

        reservationService.cancelReservation(reservationId);
        model.addAttribute(ATTRIBUTE_RESERVATIONS, reservationService.displayReservationsByUtilisateur(utilisateurId));
        return VIEW_RESERVATION;
    }

    @PostMapping(value = "/reservation/{utilisateurId}/ouvrage/{ouvrageId}")
    public String createReservation(Model model, @PathVariable ("utilisateurId") Integer utilisateurId,
                                    @PathVariable ("ouvrageId") Integer ouvrageId) {
        LOGGER.info(">>>>> Dans ReservationController - createReservation");

        reservationService.createReservation(utilisateurId, ouvrageId);
        model.addAttribute(ATTRIBUTE_RESERVATIONS, reservationService.displayReservationsByUtilisateur(utilisateurId));
        return VIEW_RESERVATION;
    }

}
