package com.ebibli.controller;

import com.ebibli.dto.BibliothequeDto;
import com.ebibli.service.BibliothequeService;
import com.ebibli.service.LivreService;
import com.ebibli.service.OuvrageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomepageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomepageController.class);
    private static final BibliothequeDto DEFAULT_BIBLIOTHEQUE = new BibliothequeDto().builder().id(0).nom("Sélectionner une bibliothèque").build();

    private static final String VIEW_HOMEPAGE = "homepage";
    private static final String ATTRIBUTE_BIBLIOTHEQUES = "bibliotheques";
    private static final String ATTRIBUTE_BIBLIOTHEQUE_SELECTIONNEE = "bibliothequeSelectionnee";
    private static final String ATTRIBUTE_URL_BACKEND = "urlBackend";
    private static final String ATTRIBUTE_OUVRAGES = "ouvrages";
    private static final String ATTRIBUTE_LIVRES = "livres";

    @Autowired
    private BibliothequeService bibliothequeService;
    @Autowired
    private OuvrageService ouvrageService;
    @Autowired
    private LivreService livreService;

    @Value("${clients.com-ebibli-ouvrage.endpoint}")
    private String host;

    @GetMapping(value = "/")
    public ModelAndView viewHomepage() {
        LOGGER.info("HomepageController -- viewHomepage");

        ModelAndView modelAndview = new ModelAndView(VIEW_HOMEPAGE);
        modelAndview.addObject(ATTRIBUTE_BIBLIOTHEQUES, bibliothequeService.getAllBibliotheques());
        modelAndview.addObject(ATTRIBUTE_BIBLIOTHEQUE_SELECTIONNEE, DEFAULT_BIBLIOTHEQUE);
        modelAndview.addObject(ATTRIBUTE_OUVRAGES, ouvrageService.getAllOuvrages(null));
        modelAndview.addObject(ATTRIBUTE_URL_BACKEND, host+"/");

        return modelAndview;
    }

    @GetMapping(value = "/abonne/{abonneId}")
    public ModelAndView viewHomepageAbonne(@PathVariable ("abonneId") Integer abonneId) {
        LOGGER.info("HomepageController -- viewHomepageAbonne");

        ModelAndView modelAndview = new ModelAndView(VIEW_HOMEPAGE);
        modelAndview.addObject(ATTRIBUTE_BIBLIOTHEQUES, bibliothequeService.getAllBibliotheques());
        modelAndview.addObject(ATTRIBUTE_BIBLIOTHEQUE_SELECTIONNEE, DEFAULT_BIBLIOTHEQUE);
        modelAndview.addObject(ATTRIBUTE_OUVRAGES, ouvrageService.getAllOuvrages(abonneId));
        modelAndview.addObject(ATTRIBUTE_URL_BACKEND, host+"/");

        return modelAndview;
    }

    @GetMapping(value = "/Bibliotheque/{bibliothequeId}")
    public ModelAndView viewBibliotheque(@PathVariable(name = "bibliothequeId") Integer bibliothequeId) {
        LOGGER.info("HomepageController -- updateHomePage");

        ModelAndView modelAndview = new ModelAndView(VIEW_HOMEPAGE);
        modelAndview.addObject(ATTRIBUTE_BIBLIOTHEQUES, bibliothequeService.getAllBibliotheques());
        modelAndview.addObject(ATTRIBUTE_BIBLIOTHEQUE_SELECTIONNEE, bibliothequeService.getBibliotheque(bibliothequeId));
        modelAndview.addObject(ATTRIBUTE_LIVRES, livreService.getAllLivresByBibliotheque(bibliothequeId));
        modelAndview.addObject(ATTRIBUTE_URL_BACKEND, host+"/");

        return modelAndview;
    }
}
