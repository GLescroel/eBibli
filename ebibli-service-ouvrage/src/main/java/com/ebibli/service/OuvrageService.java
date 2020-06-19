package com.ebibli.service;

import com.ebibli.dto.OuvrageDto;
import com.ebibli.mapper.OuvrageMapper;
import com.ebibli.repository.OuvrageRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OuvrageService {

    private static final OuvrageMapper OUVRAGE_MAPPER = Mappers.getMapper(OuvrageMapper.class);

    private OuvrageRepository ouvrageRepository;

    public OuvrageService(OuvrageRepository ouvrageRepository) {
        this.ouvrageRepository = ouvrageRepository;
    }

    public List<OuvrageDto> getAllTitles() {
        return OUVRAGE_MAPPER.ouvragesToouvrageDtos(ouvrageRepository.findAll());
    }

    public OuvrageDto getOuvrageById(Integer ouvrageId) {
        return OUVRAGE_MAPPER.map(ouvrageRepository.findById(ouvrageId).orElse(null));
    }
}
