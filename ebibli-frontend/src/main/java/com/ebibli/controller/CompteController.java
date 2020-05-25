package com.ebibli.controller;

import com.ebibli.dto.UtilisateurDto;
import com.ebibli.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

@Controller
public class CompteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompteController.class);

    private static final String ATTRIBUTE_DELETED = "deleted";
    private static final String ATTRIBUTE_MESSAGE = "message";

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping(value = "/compte")
    public String viewComptePage(Model model) {
        LOGGER.info(">>>>> Dans CompteController - GetMapping");

        model.addAttribute(ATTRIBUTE_DELETED, false);
        model.addAttribute(ATTRIBUTE_MESSAGE, "");
        return "compte";
    }

    @GetMapping(value = "/compte/suppression/{email}")
    public String suppressionCompteUtilisateur(Model model,
                                               @NotNull(message = "email must be not null") @PathVariable("email") String email) {
        LOGGER.info(">>>>> Dans CompteController - GetMapping suppression compte");

        UtilisateurDto utilisateur = utilisateurService.findUtilisateurByEmail(email);

        if (utilisateurService.remove(utilisateur)) {
            model.addAttribute(ATTRIBUTE_DELETED, true);
            model.addAttribute(ATTRIBUTE_MESSAGE, "Votre compte a été supprimé");
        } else {
            model.addAttribute(ATTRIBUTE_DELETED, false);
            model.addAttribute(ATTRIBUTE_MESSAGE, "Votre compte n'a pu  être supprimé suite à un problème technique ou à des emprunts encore en cours");
        }

        return "compte";
    }
}
