package com.ebibli.infrastructure.rest.ouvrage;

import com.ebibli.domain.OuvrageClient;
import com.ebibli.dto.OuvrageDto;

import java.util.List;

public class RestOuvrageClient implements OuvrageClient {

    private final OuvrageClientApi ouvrageClientApi;

    public RestOuvrageClient(OuvrageClientApi ouvrageClientApi) {
        this.ouvrageClientApi = ouvrageClientApi;
    }


    @Override
    public List<OuvrageDto> getAllOuvrages() {
        return ouvrageClientApi.getAllOuvrages();
    }

    @Override
    public List<OuvrageDto> filterOuvrages(String recherche) {
        return ouvrageClientApi.filterOuvrages(recherche);
    }

    @Override
    public OuvrageDto getOuvrageById(Integer ouvrageId) {
        return ouvrageClientApi.getOuvrageById(ouvrageId);
    }
}
