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
    public List<LivreDto> getAllLivresByBibliotheque(Integer bibliothequeId) {
        return livreClientApi.getAllLivresByBibliotheque(bibliothequeId);
    }

    @Override
    public List<LivreDto> getDispoByOuvrage(Integer ouvrageId) {
        return livreClientApi.getDispoByOuvrage(ouvrageId);
    }

}
