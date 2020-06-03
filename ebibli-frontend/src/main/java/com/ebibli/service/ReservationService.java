package com.ebibli.service;

import com.ebibli.domain.OuvrageClient;
import com.ebibli.domain.ReservationClient;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.ReservationDto;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationClient reservationClient;
    private final UtilisateurClient utilisateurClient;
    private final OuvrageClient ouvrageClient;
    private final LivreService livreService;

    public ReservationService(ReservationClient reservationClient,
                              OuvrageClient ouvrageClient,
                              UtilisateurClient utilisateurClient,
                              LivreService livreService) {
        this.reservationClient = reservationClient;
        this.ouvrageClient = ouvrageClient;
        this.utilisateurClient = utilisateurClient;
        this.livreService = livreService;
    }

    public List<ReservationDto> displayReservationsByUtilisateur(Integer idUtilisateur) {
        List<ReservationDto> reservations = reservationClient.getReservationsByUtilisateur(idUtilisateur);
        for (ReservationDto reservation : reservations) {
            computeReservationInfoForUser(reservation, idUtilisateur);
        }
        return reservations;
    }

    private void computeReservationInfoForUser(ReservationDto reservation, Integer idUtilisateur) {
        List<ReservationDto> reservationsOuvrage = reservationClient.getReservationsByOuvrage(reservation.getOuvrage().getId());
        for (int position = 0; position < reservationsOuvrage.size(); position++) {
            if (idUtilisateur.equals(reservationsOuvrage.get(position).getEmprunteur().getId())) {
                reservation.setPosition(position + 1);
            }
        }

        List<LivreDto> livresOuvrage = livreService.getAllLivresByOuvrage(reservation.getOuvrage().getId());
        Date nextRetour = Date.valueOf(LocalDate.now().plusWeeks(4));
        for (LivreDto livre : livresOuvrage) {
            livreService.setEmpruntEncours(livre);
            if ( livre.getEmpruntEnCours() != null && livre.getEmpruntEnCours().getDateRetourPrevu().before(nextRetour)) {
                nextRetour = livre.getEmpruntEnCours().getDateRetourPrevu();
            }
            if (livre.getEmpruntEnCours() == null && livre.getNextEmprunteur().getId().equals(idUtilisateur)) {
                reservation.setLivre(livre);
            }
        }
        reservation.getOuvrage().setNextRetourPrevu(nextRetour);
    }

    public void cancelReservation(Integer reservationId) {
        reservationClient.cancelReservation(reservationId);
    }

    public void createReservation(Integer utilisateurId, Integer ouvrageId) {
        ReservationDto reservation = new ReservationDto()
                .builder()
                .ouvrage(ouvrageClient.getOuvrageById(ouvrageId))
                .emprunteur(utilisateurClient.getUtilisateurById(utilisateurId))
                .build();
        reservationClient.createReservation(reservation);
    }

    public List<ReservationDto> getReservationsByOuvrage(Integer ouvrageId) {
        return reservationClient.getReservationsByOuvrage(ouvrageId);
    }
}