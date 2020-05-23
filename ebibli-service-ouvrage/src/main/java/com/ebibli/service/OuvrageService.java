package com.ebibli.service;

import com.ebibli.dto.OuvrageDto;
import com.ebibli.mapper.OuvrageMapper;
import com.ebibli.repository.OuvrageRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OuvrageService {

    private static final OuvrageMapper OUVRAGE_MAPPER = Mappers.getMapper(OuvrageMapper.class);

    @Autowired
    private OuvrageRepository ouvrageRepository;

    public List<OuvrageDto> getAllTitles() {
        return OUVRAGE_MAPPER.ouvragesToouvrageDtos(ouvrageRepository.findAll());
    }


    public List<OuvrageDto> filterTitles(String recherche) {
        return OUVRAGE_MAPPER.ouvragesToouvrageDtos(ouvrageRepository.findOuvragesByTitreContainsOrderByTitre(recherche));
    }
}
