package com.ebibli.controller;

import com.ebibli.dto.LivreDto;
import com.ebibli.service.LivreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LivreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LivreController.class);

    private LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping(value = "/livres")
    public ResponseEntity<List<LivreDto>> getAllLivres() {
        LOGGER.info("Dans LivreController - getAllLivres");
        return new ResponseEntity<>(livreService.getAllLivres(), HttpStatus.OK);
    }

    @GetMapping(value = "/livresDispo")
    public ResponseEntity<List<LivreDto>> getAllLivresDispo() {
        LOGGER.info("Dans LivreController - getAllLivresDispo");
        return new ResponseEntity<>(livreService.getAllLivresDispo(), HttpStatus.OK);
    }

    @GetMapping(value = "/livresDispo/ouvrage/{ouvrageId}")
    public ResponseEntity<List<LivreDto>> getAllLivresDispoByOuvrage(@PathVariable ("ouvrageId") Integer ouvrageId) {
        LOGGER.info("Dans LivreController - getAllLivresDispoByOuvrage");
        return new ResponseEntity<>(livreService.getAllLivresDispoByOuvrage(ouvrageId), HttpStatus.OK);
    }

    @GetMapping(value = "/livres/ouvrage/{ouvrageId}")
    public ResponseEntity<List<LivreDto>> getAllLivresByOuvrage(@PathVariable ("ouvrageId") Integer ouvrageId) {
        LOGGER.info("Dans LivreController - getAllLivresByOuvrage");
        return new ResponseEntity<>(livreService.getAllLivresByOuvrage(ouvrageId), HttpStatus.OK);
    }

    @GetMapping(value = "/livre/{id}")
    public ResponseEntity<LivreDto> getLivre(@PathVariable ("id") Integer id) {
        LOGGER.info("Dans LivreController - getLivre");
        LivreDto livre = livreService.getLivre(id);
        if (livre != null) {
            return new ResponseEntity<>(livre, HttpStatus.OK);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/livres/{bibliothequeId}")
    public ResponseEntity<List<LivreDto>> getLivresByBibliotheque(@PathVariable ("bibliothequeId") Integer id) {
        LOGGER.info("Dans LivreController - getLivresByBibliotheque");
        return new ResponseEntity<>(livreService.getLivresByBibliotheque(id), HttpStatus.OK);
    }

    @PostMapping(value = "/livre/{livreId}/emprunt")
    public ResponseEntity<LivreDto> setPret(@PathVariable("livreId") Integer livreId) {
        LOGGER.info("Dans LivreController - setPret");
        return new ResponseEntity<>(livreService.setPret(livreId), HttpStatus.OK);
    }

    @PostMapping(value = "/livre/{livreId}/retour")
    public ResponseEntity<LivreDto> setRetour(@PathVariable("livreId") Integer livreId) {
        LOGGER.info("Dans LivreController - setRetour");
        return new ResponseEntity<>(livreService.setRetour(livreId), HttpStatus.OK);
    }

    @PostMapping(value = "/livre/{livreId}/reserve/{abonneId}")
    public ResponseEntity<LivreDto> setReserve(@PathVariable("livreId") Integer livreId, @PathVariable ("abonneId") Integer abonneId) {
        LOGGER.info("Dans LivreController - setReserve");
        return new ResponseEntity<>(livreService.setReserve(livreId, abonneId), HttpStatus.OK);
    }

    @PostMapping(value = "/livre/{livreId}/cancelReservation")
    public ResponseEntity<LivreDto> cancelReservation(@PathVariable("livreId") Integer livreId) {
        LOGGER.info("Dans LivreController - cancelReservation");
        return new ResponseEntity<>(livreService.cancelReservation(livreId), HttpStatus.OK);
    }

}
