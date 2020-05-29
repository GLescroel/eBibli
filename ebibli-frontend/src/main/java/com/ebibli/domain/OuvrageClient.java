package com.ebibli.domain;

import com.ebibli.dto.OuvrageDto;

import java.util.List;

public interface OuvrageClient {

    List<OuvrageDto> getAllOuvrages();

    List<OuvrageDto> filterOuvrages(String recherche);

    OuvrageDto getOuvrageById(Integer ouvrageId);
}
