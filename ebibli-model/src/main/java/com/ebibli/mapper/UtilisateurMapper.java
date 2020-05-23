package com.ebibli.mapper;

import com.ebibli.dto.UtilisateurDto;
import com.ebibli.domain.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(uses = RoleMapper.class)
public interface UtilisateurMapper {

    Utilisateur map(UtilisateurDto utilisateurDto);
    UtilisateurDto map(Utilisateur utilisateur);
}
