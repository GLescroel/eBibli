package com.ebibli.service;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.LivreClient;
import com.ebibli.dto.LivreDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    private final LivreClient livreClient;
    private final EmpruntClient empruntClient;

    public LivreService(LivreClient livreClient, EmpruntClient empruntClient) {
        this.livreClient = livreClient;
        this.empruntClient = empruntClient;
    }

    public List<LivreDto> getAllLivresByBibliotheque(Integer bibliothequeId) {
        List<LivreDto> livres = livreClient.getAllLivresByBibliotheque(bibliothequeId);
        for (LivreDto livre : livres) {
            if (!livre.getDisponible()) {
                livre.setEmpruntEnCours(empruntClient.findEmpruntEnCoursByLivre(livre.getId()));
            }
        }
        return livres;
    }

    public List<LivreDto> getDispoByOuvrage(Integer ouvrageId) {
        return livreClient.getDispoByOuvrage(ouvrageId);
    }
}
