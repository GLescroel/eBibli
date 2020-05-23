package com.ebibli.domain;

import com.ebibli.dto.BibliothequeDto;

import java.util.List;

public interface BibliothequeClient {

    List<BibliothequeDto> getAllBibliotheques();

    BibliothequeDto getBibliotheque(Integer bibliothequeId);

}
