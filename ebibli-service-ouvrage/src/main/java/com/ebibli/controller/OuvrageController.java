package com.ebibli.controller;

import com.ebibli.dto.OuvrageDto;
import com.ebibli.service.OuvrageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OuvrageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OuvrageController.class);

    private OuvrageService ouvrageService;

    public OuvrageController(OuvrageService ouvrageService) {
        this.ouvrageService = ouvrageService;
    }

    @GetMapping(value = "/ouvrages")
    public ResponseEntity<List<OuvrageDto>> getAllTitles() {
        LOGGER.info("Dans OuvrageController - getAllTitles");
        return new ResponseEntity<>(ouvrageService.getAllTitles(), HttpStatus.OK);
    }

    @GetMapping(value = "/ouvrage/id/{ouvrageId}")
    public ResponseEntity<OuvrageDto> getOuvrageById(@PathVariable ("ouvrageId") Integer ouvrageId) {
        LOGGER.info("Dans OuvrageController - getOuvrageById");
        return new ResponseEntity<>(ouvrageService.getOuvrageById(ouvrageId), HttpStatus.OK);
    }
}
