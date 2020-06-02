package com.ebibli.infrastructure.rest.livre;

import com.ebibli.domain.LivreClient;
import com.ebibli.dto.LivreDto;

public class RestLivreClient implements LivreClient {

    private final LivreClientApi livreClientApi;

    public RestLivreClient(LivreClientApi livreClientApi) {
        this.livreClientApi = livreClientApi;
    }

    @Override
    public LivreDto getLivreById(Integer livreId) {
        return livreClientApi.getLivreById(livreId);
    }

    @Override
    public LivreDto setIndisponible(Integer livreId) {
        return livreClientApi.setIndisponible(livreId);
    }

    @Override
    public LivreDto setDisponible(Integer livreId) {
        return livreClientApi.setDisponible(livreId);
    }

    @Override
    public LivreDto removeReservation(Integer livreId) {
        return livreClientApi.removeReservation(livreId);
    }
}
