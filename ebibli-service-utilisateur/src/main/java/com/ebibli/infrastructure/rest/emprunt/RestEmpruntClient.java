package com.ebibli.infrastructure.rest.emprunt;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.dto.EmpruntDto;

import java.util.List;

public class RestEmpruntClient implements EmpruntClient {

    private final EmpruntClientApi empruntClientApi;

    public RestEmpruntClient(EmpruntClientApi empruntClientApi) {
        this.empruntClientApi = empruntClientApi;
    }

    @Override
    public List<EmpruntDto> findEmpruntsEnCoursByUtilisateur(Integer userId) {
        return empruntClientApi.findEmpruntsEnCoursByUtilisateur(userId);
    }

    @Override
    public List<EmpruntDto> findEmpruntsTermineByUtilisateur(Integer userId) {
        return empruntClientApi.findEmpruntsTermineByUtilisateur(userId);
    }

    @Override
    public EmpruntDto suppressionEmprunteur(Integer empruntId) {
        return empruntClientApi.suppressionEmprunteur(empruntId);
    }
}
