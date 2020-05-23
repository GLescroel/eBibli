package com.ebibli.controller;

import com.ebibli.dto.EmpruntDto;
import com.ebibli.service.EmpruntService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmpruntController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpruntController.class);

    @Autowired
    private EmpruntService empruntService;

    @GetMapping(value = "/Utilisateur/{id}/mesEmprunts")
    public String viewEmpruntsPage(Model model, @PathVariable("id") Integer idUtilisateur) {
        LOGGER.info(">>>>> Dans EmpruntController - GetMapping");

        model.addAttribute("empruntsEnCours", empruntService.findEmpruntsEnCoursByUtilisateur(idUtilisateur));
        model.addAttribute("empruntsTermines", empruntService.findEmpruntsTermineByUtilisateur(idUtilisateur));
        return "emprunt";
    }

    @PostMapping(value = "/prolongation/{id}")
    public String upgradePret(Model model, @PathVariable("id") Integer pretId) {
        LOGGER.info(">>>>> Dans EmpruntController - upgradePret");

        EmpruntDto emprunt = empruntService.upgradePret(pretId);
        model.addAttribute("empruntsEnCours", empruntService.findEmpruntsEnCoursByUtilisateur(emprunt.getEmprunteur().getId()));
        model.addAttribute("empruntsTermines", empruntService.findEmpruntsTermineByUtilisateur(emprunt.getEmprunteur().getId()));
        return "emprunt";
    }

}
