package com.ebibli.service;

import com.ebibli.dto.LivreDto;
import com.ebibli.mapper.LivreMapper;
import com.ebibli.repository.LivreRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    private static final LivreMapper LIVRE_MAPPER = Mappers.getMapper(LivreMapper.class);

    @Autowired
    private LivreRepository livreRepository;

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

    public LivreDto setReserve(Integer livreId) {
        LivreDto livre = LIVRE_MAPPER.map(livreRepository.findById(livreId).orElse(null));
        if (livre != null) {
            livre.setReserve(true);
            livre = LIVRE_MAPPER.map(livreRepository.save(LIVRE_MAPPER.map(livre)));
        }
        return livre;
    }
}
