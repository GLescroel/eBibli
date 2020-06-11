package com.ebibli.service;

import com.ebibli.domain.LivreClient;
import com.ebibli.domain.ReservationClient;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.exception.FunctionalException;
import com.ebibli.mapper.EmpruntMapper;
import com.ebibli.repository.EmpruntRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmpruntService {

    private static final EmpruntMapper EMPRUNT_MAPPER = Mappers.getMapper(EmpruntMapper.class);
    private final UtilisateurClient utilisateurClient;
    private final LivreClient livreClient;
    private final EmpruntRepository empruntRepository;
    private final ReservationClient reservationClient;

    public EmpruntService(EmpruntRepository empruntRepository, UtilisateurClient utilisateurClient, LivreClient livreClient, ReservationClient reservationClient) {
        this.empruntRepository = empruntRepository;
        this.utilisateurClient = utilisateurClient;
        this.livreClient = livreClient;
        this.reservationClient = reservationClient;
    }


    public EmpruntDto getEmpruntById(Integer empruntId) {
        return EMPRUNT_MAPPER.map((empruntRepository.findById(empruntId)).orElse(null));
    }

    public EmpruntDto getEmpruntEnCoursByLivre(Integer livreId) {
        return EMPRUNT_MAPPER.map(empruntRepository.findEmpruntByLivre_IdAndEncoursIsTrue(livreId));
    }

    public EmpruntDto newPret(Integer emprunteurId, Integer livreId) {

        UtilisateurDto emprunteur = utilisateurClient.getUtilisateurById(emprunteurId);
        LivreDto livre = livreClient.getLivreById(livreId);

        if (livre == null) {
            throw new FunctionalException("Réservation impossible : Ce livre n'est pas référencé");
        }
        if (emprunteur == null) {
            throw new FunctionalException("Réservation impossible : Cet abonné n'est pas référencé");
        }
        if (Boolean.TRUE.equals(livre.getReserve()) && livre.getNextEmprunteur().getId().equals(emprunteurId)) {
            throw new FunctionalException("Réservation impossible : Ce livre est réservé par un autre abonné");
        }
        if (getEmpruntEnCoursByLivre(livreId) != null) {
            throw new FunctionalException("Réservation impossible : ce livre est déjà emprunté");
        }

        livreClient.setIndisponible(livre.getId());
        livreClient.removeReservation(livre.getId());
        reservationClient.cancelReservation(livre.getOuvrage().getId(), emprunteurId);
        EmpruntDto emprunt = new EmpruntDto()
                .builder()
                .livre(livre)
                .emprunteur(emprunteur)
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
        if(!emprunt.getLivre().getBibliotheque().getId().equals(bibliothequeId)) {
            throw new FunctionalException("Un livre ne peut être rendu que dans sa bibliothèque d'affectation");
        }
        emprunt.setEncours(false);
        emprunt.setDateRetour(Date.valueOf(LocalDate.now()));

        emprunt.setLivre(livreClient.setDisponible(livreId));

        reservationClient.notificationRetourLivre(emprunt.getLivre());

        return EMPRUNT_MAPPER.map(empruntRepository.save(EMPRUNT_MAPPER.map(emprunt)));
    }

    public EmpruntDto upgradePret(Integer empruntId) {
        EmpruntDto emprunt = getEmpruntById(empruntId);
        if(emprunt == null) {
            return null;
        }
        if (Boolean.TRUE.equals(emprunt.getProlonge()) || Boolean.TRUE.equals(emprunt.getEnRetard()) || Boolean.FALSE.equals(emprunt.getEncours())) {
            return emprunt;
        }
        if (emprunt.getDateRetourPrevu().before(Date.valueOf(LocalDate.now()))) {
            return emprunt;
        }
        emprunt.setDateRetourPrevu(Date.valueOf(emprunt.getDateRetourPrevu().toLocalDate().plusWeeks(4)));
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
