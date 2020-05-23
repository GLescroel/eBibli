package com.ebibli.domain;

import com.ebibli.dto.UtilisateurDto;

public interface UtilisateurClient {


    UtilisateurDto getUtilisateurByEmail(String email);

    UtilisateurDto save(UtilisateurDto utilisateur);

    Boolean delete(UtilisateurDto utilisateur);
}
