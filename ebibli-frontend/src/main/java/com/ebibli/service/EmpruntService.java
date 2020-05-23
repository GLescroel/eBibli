package com.ebibli.service;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.dto.EmpruntDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpruntService {

    private final EmpruntClient empruntClient;

    public EmpruntService(EmpruntClient empruntClient) {
        this.empruntClient = empruntClient;

    }
    public EmpruntDto upgradePret(Integer empruntId) {
        return empruntClient.upgradeEmprunt(empruntId);
    }

    public List<EmpruntDto> findEmpruntsEnCoursByUtilisateur(Integer userid) {
        return empruntClient.findEmpruntsEnCoursByUtilisateur(userid);
    }

    public List<EmpruntDto> findEmpruntsTermineByUtilisateur(Integer userid) {
        return empruntClient.findEmpruntsTermineByUtilisateur(userid);
    }

}
