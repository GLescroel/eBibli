package com.ebibli.service;

import com.ebibli.dto.BibliothequeDto;
import com.ebibli.mapper.BibliothequeMapper;
import com.ebibli.repository.BibliothequeRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BibliothequeService {

    private static final BibliothequeMapper BIBLIOTHEQUE_MAPPER = Mappers.getMapper(BibliothequeMapper.class);

    private BibliothequeRepository bibliothequeRepository;

    public BibliothequeService(BibliothequeRepository bibliothequeRepository) {
        this.bibliothequeRepository = bibliothequeRepository;
    }

    public List<BibliothequeDto> getAllBibliotheques() {
        return BIBLIOTHEQUE_MAPPER.bibliothequesToBibliothequeDtos(bibliothequeRepository.findAll());
    }

    public BibliothequeDto getBibliotheque(Integer bibliothequeId) {
        return BIBLIOTHEQUE_MAPPER.map(bibliothequeRepository.getOne(bibliothequeId));
    }
}
