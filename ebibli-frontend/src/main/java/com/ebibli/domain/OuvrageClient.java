package com.ebibli.domain;

import com.ebibli.dto.OuvrageDto;

import java.util.List;

public interface OuvrageClient {

    List<OuvrageDto> getAllOuvrages();

    OuvrageDto getOuvrageById(Integer ouvrageId);
}
