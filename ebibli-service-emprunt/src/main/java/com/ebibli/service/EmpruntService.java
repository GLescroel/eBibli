package com.ebibli.service;

import com.ebibli.domain.LivreClient;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.mapper.EmpruntMapper;
import com.ebibli.repository.EmpruntRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class EmpruntService {

    private static final EmpruntMapper EMPRUNT_MAPPER = Mappers.getMapper(EmpruntMapper.class);
    private final UtilisateurClient utilisateurClient;
    private final LivreClient livreClient;

    @Autowired
    private EmpruntRepository empruntRepository;

    public EmpruntService(UtilisateurClient utilisateurClient, LivreClient livreClient) {
        this.utilisateurClient = utilisateurClient;
        this.livreClient = livreClient;
    }


    public EmpruntDto getEmpruntById(Integer empruntId) {
        return EMPRUNT_MAPPER.map(empruntRepository.findById(empruntId).get());
    }

    public EmpruntDto getEmpruntEnCoursByLivre(Integer livreId) {
        return EMPRUNT_MAPPER.map(empruntRepository.findEmpruntByLivre_IdAndEncoursIsTrue(livreId));
    }

    public EmpruntDto newPret(Integer emprunteurId, Integer livreId) {

        EmpruntDto emprunt = new EmpruntDto()
                .builder()
                .livre(livreClient.setIndisponible(livreId))
                .emprunteur(utilisateurClient.getUtilisateurById(emprunteurId))
                .dateEmprunt(Date.valueOf(LocalDate.now()))
                .dateRetourPrevu(Date.valueOf(  LocalDate.now().plusWeeks(4)))
                .encours(true)
                .prolonge(false)
                .enRetard(false)
                .build();

        return EMPRUNT_MAPPER.map(empruntRepository.save(EMPRUNT_MAPPER.map(emprunt)));
    }

    public EmpruntDto closePret(Integer bibliothequeId, Integer livreId) {
        EmpruntDto emprunt = getEmpruntEnCoursByLivre(livreId);
        if(emprunt == null) {
            return null;
        }
        if(emprunt.getLivre().getBibliotheque().getId() != bibliothequeId) {
            return emprunt;
        }
        emprunt.setEncours(false);
        emprunt.setDateRetour(Date.valueOf(LocalDate.now()));

        emprunt.setLivre(livreClient.setDisponible(livreId));

        return EMPRUNT_MAPPER.map(empruntRepository.save(EMPRUNT_MAPPER.map(emprunt)));
    }

    public EmpruntDto upgradePret(Integer empruntId) {
        EmpruntDto emprunt = getEmpruntById(empruntId);
        if(emprunt == null) {
            return null;
        }
        if (emprunt.getProlonge() == true || emprunt.getEncours() == false) {
            return emprunt;
        }
        emprunt.setDateRetourPrevu(Date.valueOf(emprunt.getDateRetourPrevu().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusWeeks(4)));
        emprunt.setProlonge(true);
        if (emprunt.getDateRetourPrevu().after(Date.valueOf(LocalDate.now()))) {
            emprunt.setEnRetard(false);
        }
        return EMPRUNT_MAPPER.map(empruntRepository.save(EMPRUNT_MAPPER.map(emprunt)));
    }

    public List<EmpruntDto> getEmpruntsByUtilisateur(Integer emprunteurId) {
        return EMPRUNT_MAPPER.empruntsToDtos(empruntRepository.findEmpruntsByEmprunteur_IdOrderByDateEmpruntAsc(emprunteurId));
    }

    public List<EmpruntDto> getEmpruntsEnCoursByUtilisateur(Integer emprunteurId) {
        return EMPRUNT_MAPPER.empruntsToDtos(empruntRepository.findEmpruntsByEmprunteur_IdAndEncoursIsTrueOrderByDateEmpruntAsc(emprunteurId));
    }

    public List<EmpruntDto> getEmpruntsTermineByUtilisateur(Integer emprunteurId) {
        return EMPRUNT_MAPPER.empruntsToDtos(empruntRepository.findEmpruntsByEmprunteur_IdAndEncoursIsFalseOrderByDateEmpruntAsc(emprunteurId));
    }

    public List<EmpruntDto> getEmpruntsEnCoursRetard() {
        List<EmpruntDto> emprunts = EMPRUNT_MAPPER.empruntsToDtos(empruntRepository.findEmpruntsByEncoursIsTrueAndDateRetourPrevuBeforeOrderByEmprunteur(Date.valueOf(LocalDate.now())));
        for (EmpruntDto emprunt : emprunts) {
            emprunt.setEnRetard(true);
            empruntRepository.save(EMPRUNT_MAPPER.map(emprunt));
        }
        return emprunts;
    }

    public EmpruntDto suppressionEmprunteur(Integer empruntId) {
        EmpruntDto emprunt = EMPRUNT_MAPPER.map(empruntRepository.getOne(empruntId));
        emprunt.setEmprunteur(null);
        return EMPRUNT_MAPPER.map(empruntRepository.save(EMPRUNT_MAPPER.map(emprunt)));
    }
}
