package com.ebibli.infrastructure.rest.bibliotheque;

import com.ebibli.domain.BibliothequeClient;
import com.ebibli.dto.BibliothequeDto;

import java.util.List;

public class RestBibliothequeClient implements BibliothequeClient {

    private final BibliothequeClientApi bibliothequeClientApi;

    public RestBibliothequeClient(BibliothequeClientApi bibliothequeClientApi) {
        this.bibliothequeClientApi = bibliothequeClientApi;
    }

    @Override
    public BibliothequeDto getBibliotheque(Integer bibliothequeId) {
        return bibliothequeClientApi.getBibliothequeById(bibliothequeId);
    }

    @Override
    public List<BibliothequeDto> getAllBibliotheques() {
        return bibliothequeClientApi.getAllBibliotheques();
    }

}
