package com.ebibli.service;

import com.ebibli.domain.OuvrageClient;
import com.ebibli.dto.BibliothequeDto;
import com.ebibli.dto.Disponibilite;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.ReservationDto;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OuvrageService {

    private final OuvrageClient ouvrageClient;
    private final LivreService livreService;
    private final ReservationService reservationService;
    private final EmpruntService empruntService;

    public OuvrageService(OuvrageClient ouvrageClient, LivreService livreService, ReservationService reservationService, EmpruntService empruntService) {
        this.ouvrageClient = ouvrageClient;
        this.livreService = livreService;
        this.reservationService = reservationService;
        this.empruntService = empruntService;
    }

    public List<OuvrageDto> getAllOuvrages(Integer abonneId) {
        List<OuvrageDto> ouvrages = ouvrageClient.getAllOuvrages();
        return setDisponibilites(ouvrages, abonneId);
    }

    private List<OuvrageDto> setDisponibilites(List<OuvrageDto> ouvrages, Integer abonneId) {
        for (OuvrageDto ouvrage : ouvrages) {
            ouvrage.setDisponibilite(getOuvrageDispo(ouvrage.getId()));

            if (ouvrage.getDisponibilite().isEmpty()) {
                setReservationInfo(ouvrage, abonneId);
            }
        }
        return ouvrages;
    }

    private void setReservationInfo(OuvrageDto ouvrage, Integer abonneId) {
        List<LivreDto> livresOuvrage = livreService.getAllLivresByOuvrage(ouvrage.getId());
        ouvrage.setReservationListSizeMax(livresOuvrage.size() * 2);
        ouvrage.setReservations(reservationService.getReservationsByOuvrage(ouvrage.getId()));
        Date nextRetour = Date.valueOf(LocalDate.now());
        for (LivreDto livre : livresOuvrage) {
            livreService.setEmpruntEncours(livre);
            if (livre.getEmpruntEnCours().getDateRetourPrevu().before(nextRetour)) {
                nextRetour = livre.getEmpruntEnCours().getDateRetourPrevu();
            }
        }
        ouvrage.setNextRetourPrevu(nextRetour);
        if (abonneId != null) {
            ouvrage.setReservationAvailable(checkReservationAvailable(ouvrage, abonneId));
        }
    }

    private Boolean checkReservationAvailable(OuvrageDto ouvrage, Integer abonneId) {
        ouvrage.setReservationEnCours(false);
        ouvrage.setEmpruntEnCours(false);
        boolean reservationAvailable = true;
        //check if the reservation list is full
        if (ouvrage.getReservations().size() >= ouvrage.getReservationListSizeMax()) {
            reservationAvailable = false;
        }
        //check if the user has not one book of this ouvrage already
        List<EmpruntDto> empruntsEnCours = empruntService.findEmpruntsEnCoursByUtilisateur(abonneId);
        if (!empruntsEnCours.isEmpty()) {
            for (EmpruntDto emprunt : empruntsEnCours) {
                if (emprunt.getLivre().getOuvrage().getId().equals(ouvrage.getId())) {
                    ouvrage.setEmpruntEnCours(true);
                    reservationAvailable = false;
                }
            }
        }
        //check if the user has not already set a reservation for this ouvrage
        for (ReservationDto reservation : ouvrage.getReservations()) {
            if (reservation.getEmprunteur().getId().equals(abonneId)) {
                ouvrage.setReservationEnCours(true);
                reservationAvailable = false;
            }
        }

        return reservationAvailable;
    }

    private List<Disponibilite> getOuvrageDispo(Integer ouvrageId) {
        List<Disponibilite> disponibilites = new ArrayList<>();
        List<LivreDto> livres = livreService.getDispoByOuvrage(ouvrageId);
        HashMap<BibliothequeDto, Integer> disponibilitesMap = new HashMap<>();
        for (LivreDto livre : livres) {
            boolean newBibli = true;
            for (BibliothequeDto bibli : disponibilitesMap.keySet()) {
                if (bibli.getId().equals(livre.getBibliotheque().getId())) {
                    disponibilitesMap.put(bibli, disponibilitesMap.get(bibli) + 1);
                    newBibli = false;
                }
            }
            if (newBibli) {
                disponibilitesMap.put(livre.getBibliotheque(), 1);
            }
        }
        for (BibliothequeDto bibli : disponibilitesMap.keySet()) {
            Disponibilite disponibilite = new Disponibilite();
            disponibilite.setBibliotheque(bibli);
            disponibilite.setDispo(disponibilitesMap.get(bibli));
            disponibilites.add(disponibilite);
        }
        return disponibilites;
    }

    //TODO supprimer
    public List<OuvrageDto> filterOuvrages(String recherche) {
        List<OuvrageDto> ouvrages = ouvrageClient.filterOuvrages(recherche);
        return setDisponibilites(ouvrages, null);
    }
}
