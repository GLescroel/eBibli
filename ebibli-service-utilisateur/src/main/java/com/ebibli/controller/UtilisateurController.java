package com.ebibli.controller;

import com.ebibli.domain.Utilisateur;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.Email;
import java.util.List;

@RestController
public class UtilisateurController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurController.class);

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping(value = "/utilisateurs")
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        LOGGER.info("Dans UtilisateurController - getAllUsers");
        return new ResponseEntity<>(utilisateurService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/utilisateur/email/{email}")
    public ResponseEntity<UtilisateurDto> checkIfUserExists(@Email @PathVariable("email") String email) {
        LOGGER.info("Dans UtilisateurController - checkIfUserExists(" + email + ")");
        UtilisateurDto utilisateur = utilisateurService.getUserByEmail(email);
        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }

    @GetMapping(value = "/utilisateur/id/{id}")
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable("id") Integer id) {
        LOGGER.info("Dans UtilisateurController - getUtilisateurById(" + id + ")");
        UtilisateurDto utilisateur = utilisateurService.getUtlisateurById(id);
        if (utilisateur == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return new ResponseEntity<>(utilisateur, HttpStatus.OK);
        }
    }


    @PostMapping(value = "/utilisateur/creation")
    public ResponseEntity<Void> addUser(@RequestBody UtilisateurDto utilisateur) {
        LOGGER.info("Dans UtilisateurController - addUser");
        if (utilisateur == null) {
            return ResponseEntity.noContent().build();
        }
        UtilisateurDto newUtilisateur = utilisateurService.save(utilisateur);
        if (newUtilisateur != null) {
            return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newUtilisateur.getId())
                    .toUri())
                    .build();
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/utilisateur/suppression")
    public ResponseEntity<Boolean> deleteUser(@RequestBody UtilisateurDto utilisateur) {
        LOGGER.info("Dans UtilisateurController - deleteUser");
        if (utilisateur == null) {
            return ResponseEntity.noContent().build();
        }
        if (utilisateurService.delete(utilisateur)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
