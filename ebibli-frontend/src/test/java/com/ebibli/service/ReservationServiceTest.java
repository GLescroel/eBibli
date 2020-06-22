package com.ebibli.service;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.OuvrageClient;
import com.ebibli.domain.ReservationClient;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.dto.UtilisateurDto;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class ReservationServiceTest {

    private final ReservationClient reservationClient = Mockito.mock(ReservationClient.class);
    private final UtilisateurClient utilisateurClient = Mockito.mock(UtilisateurClient.class);
    private final OuvrageClient ouvrageClient = Mockito.mock(OuvrageClient.class);
    private final LivreService livreService = Mockito.mock(LivreService.class);
    private final EmpruntClient empruntClient = Mockito.mock(EmpruntClient.class);

    private ReservationService reservationService = new ReservationService(reservationClient, ouvrageClient, utilisateurClient, livreService);

    @Test
    public void testShouldGetReservationsByUtilisateur() {

        LivreDto livre1 = new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).reserve(true).empruntEnCours(new EmpruntDto().builder().id(1).dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(2))).emprunteur(new UtilisateurDto().builder().id(2).build()).build()).build();
        LivreDto livre2 = new LivreDto().builder().id(2).ouvrage(new OuvrageDto().builder().id(2).build()).reserve(true).empruntEnCours(null).nextEmprunteur(new UtilisateurDto().builder().id(1).build()).build();
        LivreDto livre3 = new LivreDto().builder().id(3).ouvrage(new OuvrageDto().builder().id(1).build()).reserve(true).empruntEnCours(new EmpruntDto().builder().id(2).dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(1))).emprunteur(new UtilisateurDto().builder().id(5).build()).build()).build();
        ReservationDto reservation1 = new ReservationDto().builder().id(1).livre(livre1).ouvrage(new OuvrageDto().builder().id(1).build()).emprunteur(new UtilisateurDto().builder().id(1).build()).build();
        ReservationDto reservation2 = new ReservationDto().builder().id(2).livre(livre2).ouvrage(new OuvrageDto().builder().id(2).build()).dateReservation(Date.valueOf(LocalDate.now().minusDays(3))).emprunteur(new UtilisateurDto().builder().id(1).build()).build();
        ReservationDto reservation3 = new ReservationDto().builder().id(3).livre(livre3).ouvrage(new OuvrageDto().builder().id(2).build()).dateReservation(Date.valueOf(LocalDate.now().minusWeeks(3))).emprunteur(new UtilisateurDto().builder().id(3).build()).build();
        List<ReservationDto> reservationsUtilisateur1 = new ArrayList<>();
        reservationsUtilisateur1.add(reservation1);
        reservationsUtilisateur1.add(reservation2);
        Mockito.when(reservationClient.getReservationsByUtilisateur(1)).thenReturn(reservationsUtilisateur1);
        List<ReservationDto> reservationsOuvrage1 = new ArrayList<>();
        reservationsOuvrage1.add(reservation1);
        Mockito.when(reservationClient.getReservationsByOuvrage(1)).thenReturn(reservationsOuvrage1);
        List<ReservationDto> reservationsOuvrage2 = new ArrayList<>();
        reservationsOuvrage2.add(reservation3);
        reservationsOuvrage2.add(reservation2);
        Mockito.when(reservationClient.getReservationsByOuvrage(2)).thenReturn(reservationsOuvrage2);
        List<LivreDto> livresOuvrage1 = new ArrayList<>();
        livresOuvrage1.add(livre1);
        livresOuvrage1.add(livre3);
        List<LivreDto> livresOuvrage2 = new ArrayList<>();
        livresOuvrage2.add(livre2);
        Mockito.when(livreService.getAllLivresByOuvrage(1)).thenReturn(livresOuvrage1);
        Mockito.when(livreService.getAllLivresByOuvrage(2)).thenReturn(livresOuvrage2);

        Mockito.when(empruntClient.findEmpruntEnCoursByLivre(any())).thenReturn(null);

        List<ReservationDto> reservations = reservationService.displayReservationsByUtilisateur(1);

        Assert.assertEquals(java.util.Optional.of(1).get(), reservations.get(0).getEmprunteur().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), reservations.get(1).getEmprunteur().getId());

        Assert.assertEquals(java.util.Optional.of(1).get(), reservations.get(0).getOuvrage().getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), reservations.get(1).getOuvrage().getId());

        Assert.assertEquals(java.util.Optional.of(1).get(), reservations.get(0).getPosition());
        Assert.assertEquals(java.util.Optional.of(2).get(), reservations.get(1).getPosition());

        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(1)), reservations.get(0).getOuvrage().getNextRetourPrevu());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(4)), reservations.get(1).getOuvrage().getNextRetourPrevu());
    }

    @Test
    public void testShouldCancelReservation() {
        ReservationDto reservationToCancel = new ReservationDto().builder().id(1).build();
        reservationService.cancelReservation(1);
        Mockito.verify(reservationClient).cancelReservation(1);
    }

    @Test
    public void testShouldCreateReservation() {
        Mockito.when(utilisateurClient.getUtilisateurById(1)).thenReturn(new UtilisateurDto().builder().id(1).build());
        Mockito.when(ouvrageClient.getOuvrageById(1)).thenReturn(new OuvrageDto().builder().id(1).build());

        reservationService.createReservation(1, 1);
        Mockito.verify(reservationClient).createReservation(any());
    }

    @Test
    public void testShouldGetReservationsByOuvrage() {
        List<ReservationDto> expectedReservationsOuvrage1 = new ArrayList<>();
        expectedReservationsOuvrage1.add(new ReservationDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).emprunteur(new UtilisateurDto().builder().id(1).build()).build());
        expectedReservationsOuvrage1.add(new ReservationDto().builder().id(2).ouvrage(new OuvrageDto().builder().id(1).build()).dateReservation(Date.valueOf(LocalDate.now().minusDays(3))).emprunteur(new UtilisateurDto().builder().id(1).build()).build());
        expectedReservationsOuvrage1.add(new ReservationDto().builder().id(3).ouvrage(new OuvrageDto().builder().id(1).build()).dateReservation(Date.valueOf(LocalDate.now().minusWeeks(3))).emprunteur(new UtilisateurDto().builder().id(3).build()).build());
        Mockito.when(reservationClient.getReservationsByOuvrage(1)).thenReturn(expectedReservationsOuvrage1);

        List<ReservationDto> reservations = reservationService.getReservationsByOuvrage(1);

        Assert.assertEquals(java.util.Optional.of(1).get(), reservations.get(0).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), reservations.get(1).getId());
        Assert.assertEquals(java.util.Optional.of(3).get(), reservations.get(2).getId());
    }
}