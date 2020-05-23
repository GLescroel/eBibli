package com.ebibli.controller;


import com.ebibli.dto.RoleDto;
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
public class FormCompteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormCompteController.class);

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/creationCompte")
    public String viewFormComptePage(Model model) {
        LOGGER.info(">>>>> Dans FormCompteController - GetMapping");
        return "formCompte";
    }

    @PostMapping(value = "/creationCompte")
    public String createUserAccount(Model model,
                                    @RequestParam(name = "nom") String nom,
                                    @RequestParam(name = "prenom") String prenom,
                                    @RequestParam(name = "username") String email,
                                    @RequestParam(name = "password") String password) throws IllegalAccessException, InstantiationException {
        LOGGER.info(">>>>> Dans FormCompteController - PostMapping");

        UtilisateurDto utilisateur = utilisateurService.findUtilisateurByEmail(email);
        if (utilisateur != null) {
            model.addAttribute("rejected", true);
            model.addAttribute("message", "cet email existe déjà dans nos bases");
        } else {
            utilisateur = new UtilisateurDto()
                    .builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .nom(nom)
                    .prenom(prenom)
                    .role(RoleDto.class.newInstance().roleAbonne())
                    .build();

            utilisateurService.save(utilisateur);

            model.addAttribute("nom", utilisateur.getNom());
            model.addAttribute("prenom", utilisateur.getPrenom());
            model.addAttribute("email", email);
            model.addAttribute("rejected", false);
            model.addAttribute("created", true);
            model.addAttribute("message", "Votre compte a été créé");
        }

        return "formCompte";
    }
}
