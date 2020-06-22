package com.ebibli.service;

import com.ebibli.domain.OuvrageClient;
import com.ebibli.dto.BibliothequeDto;
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

public class OuvrageServiceTest {

    private final OuvrageClient ouvrageClient = Mockito.mock(OuvrageClient.class);
    private final LivreService livreService = Mockito.mock(LivreService.class);
    private final ReservationService reservationService =  Mockito.mock(ReservationService.class);
    private final EmpruntService empruntService = Mockito.mock(EmpruntService.class);

    private OuvrageService ouvrageService = new OuvrageService(ouvrageClient, livreService, reservationService, empruntService);

    @Test
    public void testShouldGetAllOuvrages() {
        List<OuvrageDto> ouvragesExpected = new ArrayList<>();
        ouvragesExpected.add(new OuvrageDto().builder().id(1).build());
        ouvragesExpected.add(new OuvrageDto().builder().id(2).build());
        ouvragesExpected.add(new OuvrageDto().builder().id(3).build());
        ouvragesExpected.add(new OuvrageDto().builder().id(4).build());
        ouvragesExpected.add(new OuvrageDto().builder().id(5).build());
        ouvragesExpected.add(new OuvrageDto().builder().id(6).build());
        Mockito.when(ouvrageClient.getAllOuvrages()).thenReturn(ouvragesExpected);
        List<LivreDto> expectedLivresOuvrage1 = new ArrayList<>();
        expectedLivresOuvrage1.add(new LivreDto().builder().id(1).bibliotheque(new BibliothequeDto().builder().id(1).build()).ouvrage(new OuvrageDto().builder().id(1).build()).disponible(true).build());
//        expectedLivresOuvrage1.add(new LivreDto().builder().id(2).bibliotheque(new BibliothequeDto().builder().id(2).build()).ouvrage(new OuvrageDto().builder().id(1).build()).disponible(true).build());
        List<LivreDto> expectedLivresOuvrage2 = new ArrayList<>();
        expectedLivresOuvrage2.add(new LivreDto().builder().id(3).bibliotheque(new BibliothequeDto().builder().id(1).build()).ouvrage(new OuvrageDto().builder().id(2).build()).build());
        expectedLivresOuvrage2.add(new LivreDto().builder().id(4).bibliotheque(new BibliothequeDto().builder().id(1).build()).ouvrage(new OuvrageDto().builder().id(2).build()).build());
        List<LivreDto> expectedLivresOuvrage3 = new ArrayList<>();
        expectedLivresOuvrage3.add(new LivreDto().builder().id(5).bibliotheque(new BibliothequeDto().builder().id(3).build()).ouvrage(new OuvrageDto().builder().id(3).build()).disponible(false).nextEmprunteur(new UtilisateurDto().builder().id(5).build()).reserve(true).build());
        expectedLivresOuvrage3.add(new LivreDto().builder().id(6).bibliotheque(new BibliothequeDto().builder().id(3).build()).ouvrage(new OuvrageDto().builder().id(3).build()).disponible(false).nextEmprunteur(new UtilisateurDto().builder().id(6).build()).reserve(true).build());
        List<LivreDto> expectedLivresOuvrage4 = new ArrayList<>();
        expectedLivresOuvrage4.add(new LivreDto().builder().id(7).bibliotheque(new BibliothequeDto().builder().id(3).build()).ouvrage(new OuvrageDto().builder().id(3).build()).disponible(false).nextEmprunteur(new UtilisateurDto().builder().id(7).build()).reserve(true).build());
        expectedLivresOuvrage4.add(new LivreDto().builder().id(8).bibliotheque(new BibliothequeDto().builder().id(3).build()).ouvrage(new OuvrageDto().builder().id(3).build()).disponible(false).nextEmprunteur(new UtilisateurDto().builder().id(8).build()).reserve(true).build());
        List<LivreDto> expectedLivresOuvrage5 = new ArrayList<>();
        expectedLivresOuvrage5.add(new LivreDto().builder().id(9).bibliotheque(new BibliothequeDto().builder().id(3).build()).ouvrage(new OuvrageDto().builder().id(5).build()).disponible(false).nextEmprunteur(new UtilisateurDto().builder().id(9).build()).reserve(true).build());
        expectedLivresOuvrage5.add(new LivreDto().builder().id(10).bibliotheque(new BibliothequeDto().builder().id(3).build()).ouvrage(new OuvrageDto().builder().id(5).build()).disponible(false).nextEmprunteur(new UtilisateurDto().builder().id(10).build()).reserve(true).empruntEnCours(new EmpruntDto().builder().dateRetourPrevu(Date.valueOf(LocalDate.now().minusDays(4))).build()).build());
        List<LivreDto> expectedLivresOuvrage6 = new ArrayList<>();
        expectedLivresOuvrage6.add(new LivreDto().builder().id(11).bibliotheque(new BibliothequeDto().builder().id(1).build()).ouvrage(new OuvrageDto().builder().id(6).build()).disponible(false).nextEmprunteur(new UtilisateurDto().builder().id(9).build()).reserve(true).empruntEnCours(new EmpruntDto().builder().dateRetourPrevu(Date.valueOf(LocalDate.now().minusDays(4))).build()).build());
        Mockito.when(livreService.getDispoByOuvrage(1)).thenReturn(expectedLivresOuvrage1);
        Mockito.when(livreService.getDispoByOuvrage(2)).thenReturn(expectedLivresOuvrage2);
        Mockito.when(livreService.getDispoByOuvrage(3)).thenReturn(new ArrayList<>());
        Mockito.when(livreService.getDispoByOuvrage(4)).thenReturn(new ArrayList<>());
        Mockito.when(livreService.getDispoByOuvrage(5)).thenReturn(new ArrayList<>());
        Mockito.when(livreService.getDispoByOuvrage(6)).thenReturn(new ArrayList<>());
        Mockito.when(livreService.getAllLivresByOuvrage(3)).thenReturn(expectedLivresOuvrage1);
        Mockito.when(livreService.getAllLivresByOuvrage(3)).thenReturn(expectedLivresOuvrage2);
        Mockito.when(livreService.getAllLivresByOuvrage(3)).thenReturn(expectedLivresOuvrage3);
        Mockito.when(livreService.getAllLivresByOuvrage(4)).thenReturn(expectedLivresOuvrage4);
        Mockito.when(livreService.getAllLivresByOuvrage(5)).thenReturn(expectedLivresOuvrage5);
        Mockito.when(livreService.getAllLivresByOuvrage(5)).thenReturn(expectedLivresOuvrage6);
        List<EmpruntDto> empruntDtosUser5 = new ArrayList<>();
        empruntDtosUser5.add(new EmpruntDto().builder().id(1).livre(new LivreDto().builder().id(5).ouvrage(new OuvrageDto().builder().id(3).build()).build()).encours(true).emprunteur(new UtilisateurDto().builder().id(5).build()).build());
        Mockito.when(empruntService.findEmpruntsEnCoursByUtilisateur(5)).thenReturn(empruntDtosUser5);
        List<ReservationDto> reservationDtosUser5 = new ArrayList<>();
        reservationDtosUser5.add(new ReservationDto().builder().id(1).livre(new LivreDto().builder().id(10).ouvrage(new OuvrageDto().builder().id(5).build()).build()).ouvrage(new OuvrageDto().builder().id(5).build()).emprunteur(new UtilisateurDto().builder().id(5).build()).build());
        Mockito.when(reservationService.displayReservationsByUtilisateur(5)).thenReturn(reservationDtosUser5);
        Mockito.when(reservationService.getReservationsByOuvrage(5)).thenReturn(reservationDtosUser5);
        List<ReservationDto> reservationDtosOuvrage6 = new ArrayList<>();
        reservationDtosUser5.add(new ReservationDto().builder().id(2).livre(new LivreDto().builder().id(11).ouvrage(new OuvrageDto().builder().id(6).build()).build()).ouvrage(new OuvrageDto().builder().id(6).build()).emprunteur(new UtilisateurDto().builder().id(9).build()).build());
        reservationDtosUser5.add(new ReservationDto().builder().id(4).livre(new LivreDto().builder().id(11).ouvrage(new OuvrageDto().builder().id(6).build()).build()).ouvrage(new OuvrageDto().builder().id(6).build()).emprunteur(new UtilisateurDto().builder().id(3).build()).build());
        Mockito.when(reservationService.getReservationsByOuvrage(6)).thenReturn(reservationDtosOuvrage6);

        List<OuvrageDto> ouvrages = ouvrageService.getAllOuvrages(5);
        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrages.get(0).getDisponibilite().get(0).getBibliotheque().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrages.get(0).getDisponibilite().get(0).getDispo());
//        Assert.assertEquals(java.util.Optional.of(2).get(), ouvrages.get(0).getDisponibilite().get(1).getBibliotheque().getId());
//        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrages.get(0).getDisponibilite().get(1).getDispo());
        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrages.get(1).getDisponibilite().get(0).getBibliotheque().getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), ouvrages.get(1).getDisponibilite().get(0).getDispo());
        Assert.assertEquals(0, ouvrages.get(2).getDisponibilite().size());
        Assert.assertNull(ouvrages.get(0).getReservationAvailable());
        Assert.assertNull(ouvrages.get(0).getEmpruntEnCours());
        Assert.assertNull(ouvrages.get(0).getReservationEnCours());
        Assert.assertNull(ouvrages.get(1).getReservationAvailable());
        Assert.assertNull(ouvrages.get(1).getEmpruntEnCours());
        Assert.assertNull(ouvrages.get(1).getReservationEnCours());
        Assert.assertEquals(0, ouvrages.get(2).getDisponibilite().size());
        Assert.assertFalse(ouvrages.get(2).getReservationAvailable());
        Assert.assertTrue(ouvrages.get(2).getEmpruntEnCours());
        Assert.assertFalse(ouvrages.get(2).getReservationEnCours());
        Assert.assertEquals(0, ouvrages.get(3).getDisponibilite().size());
        Assert.assertTrue(ouvrages.get(3).getReservationAvailable());
        Assert.assertFalse(ouvrages.get(3).getEmpruntEnCours());
        Assert.assertFalse(ouvrages.get(3).getReservationEnCours());
        Assert.assertEquals(0, ouvrages.get(4).getDisponibilite().size());
        Assert.assertFalse(ouvrages.get(4).getReservationAvailable());
        Assert.assertFalse(ouvrages.get(4).getEmpruntEnCours());
        Assert.assertTrue(ouvrages.get(4).getReservationEnCours());
        Assert.assertEquals(0, ouvrages.get(5).getDisponibilite().size());
        Assert.assertFalse(ouvrages.get(5).getReservationAvailable());
        Assert.assertFalse(ouvrages.get(5).getEmpruntEnCours());
        Assert.assertFalse(ouvrages.get(5).getReservationEnCours());

    }
}