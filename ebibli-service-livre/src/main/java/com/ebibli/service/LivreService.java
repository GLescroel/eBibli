package com.ebibli.service;

import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.LivreDto;
import com.ebibli.mapper.LivreMapper;
import com.ebibli.repository.LivreRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    private static final LivreMapper LIVRE_MAPPER = Mappers.getMapper(LivreMapper.class);

    private LivreRepository livreRepository;

    private UtilisateurClient utilisateurClient;

    public LivreService(LivreRepository livreRepository, UtilisateurClient utilisateurClient) {
        this.utilisateurClient = utilisateurClient;
        this.livreRepository = livreRepository;
    }

    public List<LivreDto> getAllLivres() {
        return LIVRE_MAPPER.livresToLivreDtos(livreRepository.findAll());
    }

    public List<LivreDto> getAllLivresDispo() {
        return LIVRE_MAPPER.livresToLivreDtos(livreRepository.findAllByDisponibleTrue());
    }

    public LivreDto getLivre(int id) {
        return LIVRE_MAPPER.map(livreRepository.getOne(id));
    }

    public List<LivreDto> getLivresByBibliotheque(Integer id) {
        return LIVRE_MAPPER.livresToLivreDtos(livreRepository.findAllByBibliotheque_IdOrderByOuvrageAsc(id));
    }

    public LivreDto setPret(Integer livreId) {
        LivreDto livre = LIVRE_MAPPER.map(livreRepository.findById(livreId).orElse(null));
        if (livre != null) {
            livre.setDisponible(false);
            livre = LIVRE_MAPPER.map(livreRepository.save(LIVRE_MAPPER.map(livre)));
        }
        return livre;
    }

    public List<LivreDto> getAllLivresDispoByOuvrage(Integer ouvrageId) {
        return LIVRE_MAPPER.livresToLivreDtos(livreRepository.findLivresByOuvrage_IdAndDisponibleIsTrueAndReserveIsFalseOrderByBibliotheque(ouvrageId));
    }

    public List<LivreDto> getAllLivresByOuvrage(Integer ouvrageId) {
        return LIVRE_MAPPER.livresToLivreDtos(livreRepository.findLivresByOuvrage_IdOrderByBibliotheque(ouvrageId));
    }

    public LivreDto setRetour(Integer livreId) {
        LivreDto livre = LIVRE_MAPPER.map(livreRepository.findById(livreId).orElse(null));
        if (livre != null) {
            livre.setDisponible(true);
            livre = LIVRE_MAPPER.map(livreRepository.save(LIVRE_MAPPER.map(livre)));
        }
        return livre;
    }

    public LivreDto setReserve(Integer livreId, Integer abonneId) {
        LivreDto livre = LIVRE_MAPPER.map(livreRepository.findById(livreId).orElse(null));
        if (abonneId != 0) {
            livre.setReserve(true);
            livre.setNextEmprunteur(utilisateurClient.getUtilisateurById(abonneId));
        } else {
            livre.setReserve(false);
            livre.setNextEmprunteur(null);
        }
        livre = LIVRE_MAPPER.map(livreRepository.save(LIVRE_MAPPER.map(livre)));
        return livre;
    }

    public LivreDto cancelReservation(Integer livreId) {
        LivreDto livre = getLivre(livreId);
        livre.setNextEmprunteur(null);
        livre.setReserve(false);
        return LIVRE_MAPPER.map(livreRepository.save(LIVRE_MAPPER.map(livre)));
    }
}
