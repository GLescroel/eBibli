package com.ebibli.controller;

import com.ebibli.dto.UtilisateurDto;
import com.ebibli.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private static final String ATTRIBUTE_CONNECTED = "connected";
    private static final String ATTRIBUTE_LOGOUT = "logout";

    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(value = "/login")
    public String viewLoginPage(Model model) {
        LOGGER.info(">>>>> Dans LoginController - GetMapping");
        model.addAttribute(ATTRIBUTE_CONNECTED, null);
        model.addAttribute(ATTRIBUTE_LOGOUT, null);
        return "login";
    }

    @PostMapping(value = "/login")
    public String checkLogin(Model model,
                             @RequestParam(name="username") String email,
                             @RequestParam (name="password") String password) {
        LOGGER.info(">>>>> Dans LoginController - PostMapping");

        UtilisateurDto utilisateur = utilisateurService.findUtilisateurByEmail(email);
        if(utilisateur == null || !passwordEncoder.matches(password, utilisateur.getPassword())) {
            model.addAttribute(ATTRIBUTE_CONNECTED, null);
            model.addAttribute(ATTRIBUTE_LOGOUT, false);
        } else {
            model.addAttribute("nom", utilisateur.getNom());
            model.addAttribute("prenom", utilisateur.getPrenom());
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            model.addAttribute(ATTRIBUTE_CONNECTED, true);
            model.addAttribute(ATTRIBUTE_LOGOUT, false);
        }

        return "login";
    }

}
