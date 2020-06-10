package com.ebibli.infrastructure.rest.utilisateur;

import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.UtilisateurDto;

public class RestUtilisateurClient implements UtilisateurClient {

    private final UtilisateurClientApi utilisateurClientApi;

    public RestUtilisateurClient(UtilisateurClientApi biblioClientApi) {
        this.utilisateurClientApi = biblioClientApi;
    }

    @Override
    public UtilisateurDto getUtilisateurByEmail(String email) {
        return utilisateurClientApi.getUtilisateurByEmail(email);
    }

    @Override
    public UtilisateurDto save(UtilisateurDto utilisateur) {
        return utilisateurClientApi.save(utilisateur);
    }

    @Override
    public Boolean delete(UtilisateurDto utilisateur) {
        return utilisateurClientApi.delete(utilisateur);

    }

    @Override
    public UtilisateurDto getUtilisateurById(Integer utilisateurId) {
        return utilisateurClientApi.getUtilisateurById(utilisateurId);
    }
}
