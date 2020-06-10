package com.ebibli.service;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.Utilisateur;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.mapper.UtilisateurMapper;
import com.ebibli.repository.UtilisateurRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    private static final UtilisateurMapper UTILISATEUR_MAPPER = Mappers.getMapper(UtilisateurMapper.class);

    private final EmpruntClient empruntClient;

    private UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, EmpruntClient empruntClient) {
        this.empruntClient = empruntClient;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    public UtilisateurDto getUserByEmail(String email) {
        return UTILISATEUR_MAPPER.map(utilisateurRepository.findUtilisateurByEmail(email.toUpperCase()));
    }

    public UtilisateurDto save(UtilisateurDto utilisateurDto) {
        return UTILISATEUR_MAPPER.map(utilisateurRepository.save(UTILISATEUR_MAPPER.map(utilisateurDto)));
    }

    public boolean delete(UtilisateurDto utilisateurDto) {
        if (!empruntClient.findEmpruntsEnCoursByUtilisateur(utilisateurDto.getId()).isEmpty())
            return false;
        for (EmpruntDto emprunt : empruntClient.findEmpruntsTermineByUtilisateur(utilisateurDto.getId())) {
            empruntClient.suppressionEmprunteur(emprunt.getId());
        }
        utilisateurRepository.delete(UTILISATEUR_MAPPER.map(utilisateurDto));
        return true;
    }

    public UtilisateurDto getUtlisateurById(Integer id) {
        return UTILISATEUR_MAPPER.map(utilisateurRepository.findById(id).orElse(null));
    }
}
