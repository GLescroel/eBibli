package com.ebibli.controller;

import com.ebibli.dto.EmpruntDto;
import com.ebibli.service.EmpruntService;
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
public class EmpruntController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpruntController.class);

    private EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    @PostMapping(value = "/emprunt/{livreId}/{emprunteurId}")
    public ResponseEntity<EmpruntDto> takeLivre(@PathVariable ("livreId") Integer livreId,
                                                @PathVariable ("emprunteurId") Integer emprunteurId) {
        LOGGER.info("Dans EmpruntController - takeLivre");
        EmpruntDto empruntCree = empruntService.newPret(emprunteurId, livreId);
        if (empruntCree == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return new ResponseEntity<>(empruntCree, HttpStatus.CREATED);
        }
    }

    @PostMapping(value = "/retour/{bibliothequeId}/{livreId}")
    public ResponseEntity<EmpruntDto> returnLivre(@PathVariable ("bibliothequeId") Integer bibliothequeId,
                                                  @PathVariable ("livreId") Integer livreId) {
        LOGGER.info("Dans EmpruntController - returnLivre");
        return new ResponseEntity<>(empruntService.closePret(bibliothequeId, livreId), HttpStatus.OK);
    }

    @PostMapping(value = "/prolongation/{empruntId}")
    public ResponseEntity<EmpruntDto> upgradePret(@PathVariable ("empruntId") Integer empruntId) {
        LOGGER.info("Dans EmpruntController - upgradePret");
        return new ResponseEntity<>(empruntService.upgradePret(empruntId), HttpStatus.OK);
    }

    @GetMapping(value = "/emprunts/abonne/{emprunteurId}")
    public ResponseEntity<List<EmpruntDto>> getEmpruntsByUtilisateur(@PathVariable ("emprunteurId") Integer emprunteurId) {
        LOGGER.info("Dans EmpruntController - getEmpruntsByUtilisateur");
        return new ResponseEntity<>(empruntService.getEmpruntsByUtilisateur(emprunteurId), HttpStatus.OK);
    }

    @GetMapping(value = "/emprunts/encours/abonne/{emprunteurId}")
    public ResponseEntity<List<EmpruntDto>> getEmpruntsEnCoursByUtilisateur(@PathVariable ("emprunteurId") Integer emprunteurId) {
        LOGGER.info("Dans EmpruntController - getEmpruntsEnCoursByUtilisateur");
        return new ResponseEntity<>(empruntService.getEmpruntsEnCoursByUtilisateur(emprunteurId), HttpStatus.OK);
    }

    @GetMapping(value = "/emprunts/termine/abonne/{emprunteurId}")
    public ResponseEntity<List<EmpruntDto>> getEmpruntsTermineByUtilisateur(@PathVariable ("emprunteurId") Integer emprunteurId) {
        LOGGER.info("Dans EmpruntController - getEmpruntsTermineByUtilisateur");
        return new ResponseEntity<>(empruntService.getEmpruntsTermineByUtilisateur(emprunteurId), HttpStatus.OK);
    }

    @GetMapping(value = "/emprunts/retard")
    public ResponseEntity<List<EmpruntDto>> getEmpruntsEnCoursRetard() {
        LOGGER.info("Dans EmpruntController - getEmpruntsEnCoursRetard");
        return new ResponseEntity<>(empruntService.getEmpruntsEnCoursRetard(), HttpStatus.OK);
    }

    @GetMapping(value = "/emprunt/encours/livre/{livreId}")
    public ResponseEntity<EmpruntDto> getEmpruntEnCoursByLivre(@PathVariable ("livreId") Integer livreId) {
        LOGGER.info("Dans EmpruntController - getEmpruntEnCoursByLivre");
        return new ResponseEntity<>(empruntService.getEmpruntEnCoursByLivre(livreId), HttpStatus.OK);
    }

    @PostMapping(value = "emprunt/suppression_emprunteur/{empruntId}")
    public ResponseEntity<EmpruntDto> suppressionEmprunteur(@PathVariable("empruntId") Integer empruntId) {
        LOGGER.info("Dans EmpruntController - suppressionEmprunteur");
        return new ResponseEntity<>(empruntService.suppressionEmprunteur(empruntId), HttpStatus.OK);
    }
}
