package com.ebibli.domain;

import com.ebibli.dto.LivreDto;

import java.util.List;

public interface LivreClient {

    List<LivreDto> getLivresByOuvrage(Integer ouvrageId);

    void setLivreReserve(Integer id);
}
