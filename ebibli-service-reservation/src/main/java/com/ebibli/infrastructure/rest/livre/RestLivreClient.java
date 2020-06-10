package com.ebibli.infrastructure.rest.livre;

import com.ebibli.domain.LivreClient;
import com.ebibli.dto.LivreDto;

import java.util.List;

public class RestLivreClient implements LivreClient {

    private final LivreClientApi livreClientApi;

    public RestLivreClient(LivreClientApi livreClientApi) {
        this.livreClientApi = livreClientApi;
    }

    @Override
    public List<LivreDto> getLivresByOuvrage(Integer ouvrageId) {
        return livreClientApi.getLivresByOuvrage(ouvrageId);
    }

    @Override
    public void setLivreReserve(Integer livreId, Integer abonneId) {
        livreClientApi.setLivreReserve(livreId, abonneId);
    }

}
