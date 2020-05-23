package com.ebibli.domain;

import com.ebibli.dto.LivreDto;

public interface LivreClient {

    LivreDto getLivreById(Integer livreId);
    LivreDto setIndisponible(Integer livreId);

    LivreDto setDisponible(Integer livreId);
}
