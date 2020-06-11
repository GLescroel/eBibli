package com.ebibli.service;

import com.ebibli.domain.Bibliotheque;
import com.ebibli.domain.Livre;
import com.ebibli.domain.Ouvrage;
import com.ebibli.domain.Utilisateur;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.repository.LivreRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class LivreServiceTest {

    LivreRepository livreRepository = Mockito.mock(LivreRepository.class);
    UtilisateurClient utilisateurClient = Mockito.mock(UtilisateurClient.class);
    private LivreService livreService = new LivreService(livreRepository, utilisateurClient);
    private Livre livre1 = new Livre();
    private Livre livre2 = new Livre();
    private Livre livre3 = new Livre();
    private Livre livre4 = new Livre();
    private List<Livre> livres = new ArrayList<>();
    private List<Livre> livresDispo = new ArrayList<>();
    private List<Livre> livresBibliotheque2 = new ArrayList<>();
    private List<Livre> livresDispoOuvrage = new ArrayList<>();
    private List<Livre> livresOuvrage2 = new ArrayList<>();

    @Before
    public void setup() {
        livre1 = new Livre()
                .builder()
                .id(1)
                .ouvrage(new Ouvrage().builder().id(1).build())
                .bibliotheque(new Bibliotheque().builder().id(1).build())
                .reserve(false)
                .disponible(true)
                .nextEmprunteur(null)
                .build();
        livre2 = new Livre()
                .builder()
                .id(2)
                .ouvrage(new Ouvrage().builder().id(2).build())
                .bibliotheque(new Bibliotheque().builder().id(2).build())
                .reserve(false)
                .disponible(false)
                .nextEmprunteur(null)
                .build();
        livre3 = new Livre()
                .builder()
                .id(3)
                .ouvrage(new Ouvrage().builder().id(3).build())
                .bibliotheque(new Bibliotheque().builder().id(2).build())
                .reserve(true)
                .disponible(true)
                .nextEmprunteur(new Utilisateur().builder().id(3).build())
                .build();
        livre4 = new Livre()
                .builder()
                .id(4)
                .ouvrage(new Ouvrage().builder().id(2).build())
                .bibliotheque(new Bibliotheque().builder().id(1).build())
                .reserve(false)
                .disponible(true)
                .nextEmprunteur(null)
                .build();
        livres.add(livre1);
        livres.add(livre2);
        livresDispo.add(livre1);
        livresDispo.add(livre3);
        livresBibliotheque2.add(livre2);
        livresBibliotheque2.add(livre3);
        livresDispoOuvrage.add(livre4);
        livresOuvrage2.add(livre2);
        livresOuvrage2.add(livre4);
    }

    @Test
    public void testShouldGetLivre() {
        Mockito.when(livreRepository.getOne(any())).thenReturn(livre2);
        LivreDto livreTrouve = livreService.getLivre(2);
        Assert.assertEquals(livre2.getId(), livreTrouve.getId());
        Assert.assertEquals(livre2.getOuvrage().getId(), livreTrouve.getOuvrage().getId());
        Assert.assertEquals(livre2.getBibliotheque().getId(), livreTrouve.getBibliotheque().getId());
        Assert.assertEquals(livre2.getReserve(), livreTrouve.getReserve());
        Assert.assertEquals(livre2.getDisponible(), livreTrouve.getDisponible());
        Assert.assertEquals(livre2.getNextEmprunteur(), livreTrouve.getNextEmprunteur());
    }

    @Test
    public void testShouldGetAllLivres() {
        Mockito.when(livreRepository.findAll()).thenReturn(livres);
        List<LivreDto> livresTrouves = livreService.getAllLivres();
        Assert.assertEquals(livre1.getId(), livresTrouves.get(0).getId());
        Assert.assertEquals(livre1.getOuvrage().getId(), livresTrouves.get(0).getOuvrage().getId());
        Assert.assertEquals(livre1.getBibliotheque().getId(), livresTrouves.get(0).getBibliotheque().getId());
        Assert.assertEquals(livre1.getReserve(), livresTrouves.get(0).getReserve());
        Assert.assertEquals(livre1.getDisponible(), livresTrouves.get(0).getDisponible());
        Assert.assertEquals(livre1.getNextEmprunteur(), livresTrouves.get(0).getNextEmprunteur());

        Assert.assertEquals(livre2.getId(), livresTrouves.get(1).getId());
        Assert.assertEquals(livre2.getOuvrage().getId(), livresTrouves.get(1).getOuvrage().getId());
        Assert.assertEquals(livre2.getBibliotheque().getId(), livresTrouves.get(1).getBibliotheque().getId());
        Assert.assertEquals(livre2.getReserve(), livresTrouves.get(1).getReserve());
        Assert.assertEquals(livre2.getDisponible(), livresTrouves.get(1).getDisponible());
        Assert.assertEquals(livre2.getNextEmprunteur(), livresTrouves.get(1).getNextEmprunteur());
    }

    @Test
    public void testShouldGetAllLivresDispo() {
        Mockito.when(livreRepository.findAllByDisponibleTrue()).thenReturn(livresDispo);
        List<LivreDto> livresTrouves = livreService.getAllLivresDispo();
        Assert.assertEquals(livre1.getId(), livresTrouves.get(0).getId());
        Assert.assertEquals(livre1.getOuvrage().getId(), livresTrouves.get(0).getOuvrage().getId());
        Assert.assertEquals(livre1.getBibliotheque().getId(), livresTrouves.get(0).getBibliotheque().getId());
        Assert.assertEquals(livre1.getReserve(), livresTrouves.get(0).getReserve());
        Assert.assertEquals(livre1.getDisponible(), livresTrouves.get(0).getDisponible());
        Assert.assertEquals(livre1.getNextEmprunteur(), livresTrouves.get(0).getNextEmprunteur());

        Assert.assertEquals(livre3.getId(), livresTrouves.get(1).getId());
        Assert.assertEquals(livre3.getOuvrage().getId(), livresTrouves.get(1).getOuvrage().getId());
        Assert.assertEquals(livre3.getBibliotheque().getId(), livresTrouves.get(1).getBibliotheque().getId());
        Assert.assertEquals(livre3.getReserve(), livresTrouves.get(1).getReserve());
        Assert.assertEquals(livre3.getDisponible(), livresTrouves.get(1).getDisponible());
        Assert.assertEquals(livre3.getNextEmprunteur().getId(), livresTrouves.get(1).getNextEmprunteur().getId());
    }

    @Test
    public void testShouldGetAllLivresByBibliotheque() {
        Mockito.when(livreRepository.findAllByBibliotheque_IdOrderByOuvrageAsc(2)).thenReturn(livresBibliotheque2);
        List<LivreDto> livresTrouves = livreService.getLivresByBibliotheque(2);
        Assert.assertEquals(livre2.getId(), livresTrouves.get(0).getId());
        Assert.assertEquals(livre2.getOuvrage().getId(), livresTrouves.get(0).getOuvrage().getId());
        Assert.assertEquals(livre2.getBibliotheque().getId(), livresTrouves.get(0).getBibliotheque().getId());
        Assert.assertEquals(livre2.getReserve(), livresTrouves.get(0).getReserve());
        Assert.assertEquals(livre2.getDisponible(), livresTrouves.get(0).getDisponible());
        Assert.assertEquals(livre2.getNextEmprunteur(), livresTrouves.get(0).getNextEmprunteur());

        Assert.assertEquals(livre3.getId(), livresTrouves.get(1).getId());
        Assert.assertEquals(livre3.getOuvrage().getId(), livresTrouves.get(1).getOuvrage().getId());
        Assert.assertEquals(livre3.getBibliotheque().getId(), livresTrouves.get(1).getBibliotheque().getId());
        Assert.assertEquals(livre3.getReserve(), livresTrouves.get(1).getReserve());
        Assert.assertEquals(livre3.getDisponible(), livresTrouves.get(1).getDisponible());
        Assert.assertEquals(livre3.getNextEmprunteur().getId(), livresTrouves.get(1).getNextEmprunteur().getId());
    }

    @Test
    public void testShouldGetAllLivresDispoByOuvrage() {
        Mockito.when(livreRepository.findLivresByOuvrage_IdAndDisponibleIsTrueAndReserveIsFalseOrderByBibliotheque(any())).thenReturn(livresDispoOuvrage);
        List<LivreDto> livresTrouves = livreService.getAllLivresDispoByOuvrage(2);
        Assert.assertEquals(livre4.getId(), livresTrouves.get(0).getId());
        Assert.assertEquals(livre4.getOuvrage().getId(), livresTrouves.get(0).getOuvrage().getId());
        Assert.assertEquals(livre4.getBibliotheque().getId(), livresTrouves.get(0).getBibliotheque().getId());
        Assert.assertEquals(livre4.getReserve(), livresTrouves.get(0).getReserve());
        Assert.assertEquals(livre4.getDisponible(), livresTrouves.get(0).getDisponible());
        Assert.assertEquals(livre4.getNextEmprunteur(), livresTrouves.get(0).getNextEmprunteur());
    }

    @Test
    public void testShouldGetAllLivresByOuvrage() {
        Mockito.when(livreRepository.findLivresByOuvrage_IdOrderByBibliotheque(2)).thenReturn(livresOuvrage2);
        List<LivreDto> livresTrouves = livreService.getAllLivresByOuvrage(2);
        Assert.assertEquals(livre2.getId(), livresTrouves.get(0).getId());
        Assert.assertEquals(livre2.getOuvrage().getId(), livresTrouves.get(0).getOuvrage().getId());
        Assert.assertEquals(livre2.getBibliotheque().getId(), livresTrouves.get(0).getBibliotheque().getId());
        Assert.assertEquals(livre2.getReserve(), livresTrouves.get(0).getReserve());
        Assert.assertEquals(livre2.getDisponible(), livresTrouves.get(0).getDisponible());
        Assert.assertEquals(livre2.getNextEmprunteur(), livresTrouves.get(0).getNextEmprunteur());

        Assert.assertEquals(livre4.getId(), livresTrouves.get(1).getId());
        Assert.assertEquals(livre4.getOuvrage().getId(), livresTrouves.get(1).getOuvrage().getId());
        Assert.assertEquals(livre4.getBibliotheque().getId(), livresTrouves.get(1).getBibliotheque().getId());
        Assert.assertEquals(livre4.getReserve(), livresTrouves.get(1).getReserve());
        Assert.assertEquals(livre4.getDisponible(), livresTrouves.get(1).getDisponible());
        Assert.assertEquals(livre4.getNextEmprunteur(), livresTrouves.get(1).getNextEmprunteur());
    }

    @Test
    public void testShouldSetPret() {
        Mockito.when(livreRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(livre1));
        Mockito.when(livreRepository.save(Mockito.any(Livre.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        LivreDto livrePrete = livreService.setPret(1);
        Assert.assertFalse(livrePrete.getDisponible());
    }

    @Test
    public void testShouldSetRetour() {
        Mockito.when(livreRepository.findById(2)).thenReturn(java.util.Optional.ofNullable(livre2));
        Mockito.when(livreRepository.save(Mockito.any(Livre.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        LivreDto livrePrete = livreService.setRetour(2);
        Assert.assertTrue(livrePrete.getDisponible());
    }

    @Test
    public void testShouldSetReserve() {
        Mockito.when(livreRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(livre1));
        Mockito.when(livreRepository.save(Mockito.any(Livre.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(utilisateurClient.getUtilisateurById(1)).thenReturn(new UtilisateurDto().builder().id(1).build());
        LivreDto livreReserve = livreService.setReserve(1, 1);
        Assert.assertTrue(livreReserve.getReserve());
        Assert.assertEquals(java.util.Optional.of(1).get(), livreReserve.getNextEmprunteur().getId());
    }

    @Test
    public void testShouldSetNotReserve() {
        Mockito.when(livreRepository.findById(3)).thenReturn(java.util.Optional.ofNullable(livre3));
        Mockito.when(livreRepository.save(Mockito.any(Livre.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(utilisateurClient.getUtilisateurById(0)).thenReturn(null);
        LivreDto livreReserve = livreService.setReserve(3, 0);
        Assert.assertFalse(livreReserve.getReserve());
        Assert.assertNull(livreReserve.getNextEmprunteur());
    }

    @Test
    public void testShouldCancelReservation() {
        Mockito.when(livreRepository.getOne(3)).thenReturn(livre3);
        Mockito.when(livreRepository.save(Mockito.any(Livre.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        LivreDto livreReserve = livreService.cancelReservation(3);
        Assert.assertFalse(livreReserve.getReserve());
        Assert.assertNull(livreReserve.getNextEmprunteur());
    }
}