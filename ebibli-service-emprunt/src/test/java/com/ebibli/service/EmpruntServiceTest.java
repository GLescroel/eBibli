package com.ebibli.service;

import com.ebibli.domain.Bibliotheque;
import com.ebibli.domain.Emprunt;
import com.ebibli.domain.Livre;
import com.ebibli.domain.LivreClient;
import com.ebibli.domain.Ouvrage;
import com.ebibli.domain.ReservationClient;
import com.ebibli.domain.Utilisateur;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.BibliothequeDto;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.exception.FunctionalException;
import com.ebibli.repository.EmpruntRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class EmpruntServiceTest {

    private UtilisateurClient utilisateurClient = Mockito.mock(UtilisateurClient.class);
    private LivreClient livreClient = Mockito.mock(LivreClient.class);
    private EmpruntRepository empruntRepository = Mockito.mock(EmpruntRepository.class);
    private ReservationClient reservationClient = Mockito.mock(ReservationClient.class);
    @InjectMocks
    EmpruntService empruntService = new EmpruntService(empruntRepository, utilisateurClient, livreClient, reservationClient);

    private EmpruntDto expectedEmpruntDto1;
    private EmpruntDto expectedEmpruntDto2;
    private EmpruntDto expectedEmpruntDto3;
    private Emprunt emprunt1;
    private Emprunt emprunt2;
    private Emprunt emprunt3;
    private List<Emprunt> emprunts = new ArrayList<>();
    private List<EmpruntDto> expectedEmpruntsDto = new ArrayList<>();

    @Before
    public void setUp() {
        emprunt1 = new Emprunt()
                .builder()
                .id(1)
                .livre(new Livre().builder().id(1).build())
                .emprunteur(new Utilisateur().builder().id(1).build())
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(5)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().minusWeeks(1)))
                .prolonge(false)
                .enRetard(false)
                .dateRetour(null)
                .encours(true)
                .build();
        emprunt2 = new Emprunt()
                .builder()
                .id(2)
                .livre(new Livre().builder().id(2).build())
                .emprunteur(new Utilisateur().builder().id(2).build())
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(4)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().minusDays(1)))
                .prolonge(false)
                .enRetard(true)
                .dateRetour(null)
                .encours(true)
                .build();
        emprunt3 = new Emprunt()
                .builder()
                .id(3)
                .livre(new Livre().builder().id(3).build())
                .emprunteur(new Utilisateur().builder().id(3).build())
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(5)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().minusWeeks(1)))
                .prolonge(false)
                .enRetard(false)
                .dateRetour(null)
                .encours(true)
                .build();

        expectedEmpruntDto1 = new EmpruntDto()
                .builder()
                .id(1)
                .livre(new LivreDto().builder().id(1).build())
                .emprunteur(new UtilisateurDto().builder().id(1).build())
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(5)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().minusWeeks(1)))
                .prolonge(false)
                .enRetard(true)
                .dateRetour(null)
                .encours(true)
                .build();
        expectedEmpruntDto2 = new EmpruntDto()
                .builder()
                .id(2)
                .livre(new LivreDto().builder().id(2).build())
                .emprunteur(new UtilisateurDto().builder().id(2).build())
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(4)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().minusDays(1)))
                .prolonge(false)
                .enRetard(true)
                .dateRetour(null)
                .encours(true)
                .build();
        expectedEmpruntDto3 = new EmpruntDto()
                .builder()
                .id(3)
                .livre(new LivreDto().builder().id(3).build())
                .emprunteur(new UtilisateurDto().builder().id(3).build())
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(5)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().minusWeeks(1)))
                .prolonge(false)
                .enRetard(false)
                .dateRetour(null)
                .encours(true)
                .build();
    }

    @Test
    public void testShouldUpgradePret() {
        Emprunt emprunt = new Emprunt().builder()
                .id(1)
                .livre(new Livre().builder().id(1).build())
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(1)))
                .emprunteur(new Utilisateur().builder().id(1).build())
                .prolonge(false)
                .enRetard(false)
                .encours(true)
                .build();
        Mockito.when(empruntRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(emprunt));
        Mockito.when(empruntRepository.save(Mockito.any(Emprunt.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        EmpruntDto empruntUpgrade = empruntService.upgradePret(1);

        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(1).plusWeeks(4)), empruntUpgrade.getDateRetourPrevu());
        Assert.assertTrue(empruntUpgrade.getProlonge());
    }

    @Test
    public void testShouldNotUpgradeNullPret() {
        Mockito.when(empruntRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(null));
        Assert.assertNull(empruntService.upgradePret(1));
    }

    @Test
    public void testShouldNotUpgradePretDejaProlonge() {
        Emprunt emprunt = new Emprunt().builder()
                .id(1)
                .livre(new Livre().builder().id(1).build())
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(1)))
                .emprunteur(new Utilisateur().builder().id(1).build())
                .prolonge(true)
                .enRetard(false)
                .encours(true)
                .build();
        Mockito.when(empruntRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(emprunt));

        EmpruntDto empruntUpgrade = empruntService.upgradePret(1);

        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(1)), empruntUpgrade.getDateRetourPrevu());
    }

    @Test
    public void testShouldNotUpgradePretEnRetard() {
        Emprunt emprunt = new Emprunt().builder()
                .id(1)
                .livre(new Livre().builder().id(1).build())
                .dateRetourPrevu(Date.valueOf(LocalDate.now().minusDays(1)))
                .emprunteur(new Utilisateur().builder().id(1).build())
                .prolonge(false)
                .enRetard(true)
                .encours(true)
                .build();
        Mockito.when(empruntRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(emprunt));

        EmpruntDto empruntUpgrade = empruntService.upgradePret(1);

        Assert.assertEquals(Date.valueOf(LocalDate.now().minusDays(1)), empruntUpgrade.getDateRetourPrevu());
    }

    @Test
    public void testShouldNotUpgradePretAfterDateRetourPrevue() {
        Emprunt emprunt = new Emprunt().builder()
                .id(1)
                .livre(new Livre().builder().id(1).build())
                .dateRetourPrevu(Date.valueOf(LocalDate.now().minusDays(1)))
                .emprunteur(new Utilisateur().builder().id(1).build())
                .prolonge(false)
                .enRetard(false)
                .encours(true)
                .build();
        Mockito.when(empruntRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(emprunt));

        EmpruntDto empruntUpgrade = empruntService.upgradePret(1);

        Assert.assertEquals(Date.valueOf(LocalDate.now().minusDays(1)), empruntUpgrade.getDateRetourPrevu());
    }

    @Test
    public void testShouldNotUpgradePretTermine() {
        Emprunt emprunt = new Emprunt().builder()
                .id(1)
                .livre(new Livre().builder().id(1).build())
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(1)))
                .emprunteur(new Utilisateur().builder().id(1).build())
                .prolonge(false)
                .enRetard(true)
                .encours(false)
                .build();
        Mockito.when(empruntRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(emprunt));

        EmpruntDto empruntUpgrade = empruntService.upgradePret(1);

        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(1)), empruntUpgrade.getDateRetourPrevu());
    }

    @Test(expected = FunctionalException.class)
    public void testShouldNotCreatePretWithNullLivre() {
        UtilisateurDto utilisateur = new UtilisateurDto().builder().id(1).email("user@oc.com").build();
        Mockito.when(utilisateurClient.getUtilisateurById(1)).thenReturn(utilisateur);
        Mockito.when(livreClient.getLivreById(any())).thenReturn(null);
        empruntService.newPret(1, 999);
    }

    @Test(expected = FunctionalException.class)
    public void testShouldNotCreatePretWithNullEmprunteur() {
        LivreDto livre = new LivreDto().builder().id(1).build();
        Mockito.when(utilisateurClient.getUtilisateurById(any())).thenReturn(null);
        Mockito.when(livreClient.getLivreById(any())).thenReturn(livre);
        empruntService.newPret(1, 1);
    }

    @Test(expected = FunctionalException.class)
    public void testShouldNotCreatePretWithDifferentEmprunteur() {
        LivreDto livre = new LivreDto().builder().id(1).reserve(true).nextEmprunteur(new UtilisateurDto().builder().id(1).build()).build();
        UtilisateurDto utilisateur = new UtilisateurDto().builder().id(2).email("user@oc.com").build();
        Mockito.when(utilisateurClient.getUtilisateurById(any())).thenReturn(utilisateur);
        Mockito.when(livreClient.getLivreById(any())).thenReturn(livre);
        empruntService.newPret(2, 1);
    }

    @Test(expected = FunctionalException.class)
    public void testShouldNotCreatePretAlreadyPrete() {
        LivreDto livre = new LivreDto().builder().id(1).reserve(true).nextEmprunteur(new UtilisateurDto().builder().id(1).build()).build();
        UtilisateurDto utilisateur = new UtilisateurDto().builder().id(1).email("user@oc.com").build();
        Mockito.when(utilisateurClient.getUtilisateurById(any())).thenReturn(utilisateur);
        Mockito.when(livreClient.getLivreById(any())).thenReturn(livre);
        Emprunt emprunt = new Emprunt().builder().id(1).build();
        Mockito.when(empruntRepository.findEmpruntByLivre_IdAndEncoursIsTrue(any())).thenReturn(emprunt);
        empruntService.newPret(1, 1);
    }

    @Test
    public void testShouldCreatePret() {
        LivreDto livre = new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).reserve(true).nextEmprunteur(new UtilisateurDto().builder().id(1).build()).build();
        UtilisateurDto utilisateur = new UtilisateurDto().builder().id(1).email("user@oc.com").build();
        Mockito.when(utilisateurClient.getUtilisateurById(any())).thenReturn(utilisateur);
        Mockito.when(livreClient.getLivreById(any())).thenReturn(livre);
        livre.setNextEmprunteur(null);
        livre.setReserve(false);
        Mockito.when(livreClient.removeReservation(any())).thenReturn(livre);
        Mockito.when(empruntRepository.findEmpruntByLivre_IdAndEncoursIsTrue(any())).thenReturn(null);
        Mockito.when(empruntRepository.save(Mockito.any(Emprunt.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        EmpruntDto empruntCree = empruntService.newPret(1, 1);

        EmpruntDto expectedEmprunt = new EmpruntDto()
                .builder()
                .livre(livre)
                .emprunteur(utilisateur)
                .dateEmprunt(Date.valueOf(LocalDate.now()))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(4)))
                .prolonge(false)
                .enRetard(false)
                .dateRetour(null)
                .encours(true)
                .build();

        Assert.assertEquals(expectedEmprunt.getLivre().getId(), empruntCree.getLivre().getId());
        Assert.assertEquals(expectedEmprunt.getEmprunteur().getId(), empruntCree.getEmprunteur().getId());
        Assert.assertEquals(expectedEmprunt.getDateEmprunt(), empruntCree.getDateEmprunt());
        Assert.assertEquals(expectedEmprunt.getDateRetourPrevu(), empruntCree.getDateRetourPrevu());
        Assert.assertEquals(expectedEmprunt.getDateRetour(), empruntCree.getDateRetour());
        Assert.assertEquals(expectedEmprunt.getProlonge(), empruntCree.getProlonge());
        Assert.assertEquals(expectedEmprunt.getEnRetard(), empruntCree.getEnRetard());
        Assert.assertEquals(expectedEmprunt.getEncours(), empruntCree.getEncours());
    }

    @Test
    public void testShouldClosePret() {
        Mockito.when(empruntRepository.save(Mockito.any(Emprunt.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Livre livre = new Livre().builder().id(1).bibliotheque(new Bibliotheque().builder().id(1).build()).ouvrage(new Ouvrage().builder().id(1).build()).reserve(true).nextEmprunteur(new Utilisateur().builder().id(1).build()).build();
        LivreDto livreDto = new LivreDto().builder().id(1).bibliotheque(new BibliothequeDto().builder().id(1).build()).ouvrage(new OuvrageDto().builder().id(1).build()).reserve(true).nextEmprunteur(new UtilisateurDto().builder().id(1).build()).build();
        BibliothequeDto bibliotheque = new BibliothequeDto().builder().id(1).build();
        Utilisateur utilisateur = new Utilisateur().builder().id(1).email("user@oc.com").build();
        UtilisateurDto utilisateurDto = new UtilisateurDto().builder().id(1).email("user@oc.com").build();
        Mockito.when(livreClient.setDisponible(any())).thenReturn(livreDto);

        Emprunt empruntToClose = new Emprunt()
                .builder()
                .id(1)
                .livre(livre)
                .emprunteur(utilisateur)
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(1)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(3)))
                .prolonge(false)
                .enRetard(false)
                .dateRetour(null)
                .encours(true)
                .build();
        Mockito.when(empruntRepository.findEmpruntByLivre_IdAndEncoursIsTrue(any())).thenReturn(empruntToClose);

        EmpruntDto expectedClosedEmprunt = new EmpruntDto()
                .builder()
                .id(1)
                .livre(livreDto)
                .emprunteur(utilisateurDto)
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(1)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(3)))
                .prolonge(false)
                .enRetard(false)
                .dateRetour(Date.valueOf(LocalDate.now()))
                .encours(false)
                .build();

        EmpruntDto closedEmprunt = empruntService.closePret(bibliotheque.getId(), livre.getId());

        Assert.assertEquals(expectedClosedEmprunt.getId(), closedEmprunt.getId());
        Assert.assertEquals(expectedClosedEmprunt.getLivre().getId(), closedEmprunt.getLivre().getId());
        Assert.assertEquals(expectedClosedEmprunt.getEmprunteur().getId(), closedEmprunt.getEmprunteur().getId());
        Assert.assertEquals(expectedClosedEmprunt.getDateEmprunt(), closedEmprunt.getDateEmprunt());
        Assert.assertEquals(expectedClosedEmprunt.getDateRetourPrevu(), closedEmprunt.getDateRetourPrevu());
        Assert.assertEquals(expectedClosedEmprunt.getDateRetour(), closedEmprunt.getDateRetour());
        Assert.assertEquals(expectedClosedEmprunt.getProlonge(), closedEmprunt.getProlonge());
        Assert.assertEquals(expectedClosedEmprunt.getEnRetard(), closedEmprunt.getEnRetard());
        Assert.assertEquals(expectedClosedEmprunt.getEncours(), closedEmprunt.getEncours());
    }

    @Test
    public void testShouldNotCloseNullPret() {
        Mockito.when(empruntRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(null));
        Assert.assertNull(empruntService.closePret(1, 1));
    }

    @Test(expected = FunctionalException.class)
    public void testShouldNotClosePretWhenDifferentBibliotheque() {
        Livre livre = new Livre().builder().id(1).bibliotheque(new Bibliotheque().builder().id(1).build()).ouvrage(new Ouvrage().builder().id(1).build()).reserve(true).nextEmprunteur(new Utilisateur().builder().id(1).build()).build();
        Utilisateur utilisateur = new Utilisateur().builder().id(1).email("user@oc.com").build();
        BibliothequeDto bibliothequeRetour = new BibliothequeDto().builder().id(2).build();

        Emprunt empruntToClose = new Emprunt()
                .builder()
                .id(1)
                .livre(livre)
                .emprunteur(utilisateur)
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(1)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(3)))
                .prolonge(false)
                .enRetard(false)
                .dateRetour(null)
                .encours(true)
                .build();
        Mockito.when(empruntRepository.findEmpruntByLivre_IdAndEncoursIsTrue(any())).thenReturn(empruntToClose);

        empruntService.closePret(bibliothequeRetour.getId(), livre.getId());
    }

    @Test
    public void testShouldGetEmpruntsEnCoursEnRetard() {
        emprunts.add(emprunt1);
        emprunts.add(emprunt2);
        expectedEmpruntsDto.add(expectedEmpruntDto1);
        expectedEmpruntsDto.add(expectedEmpruntDto2);

        Mockito.when(empruntRepository.findEmpruntsByEncoursIsTrueAndDateRetourPrevuBeforeOrderByEmprunteur(any())).thenReturn(emprunts);
        List<EmpruntDto> empruntsDtoEnRetard = empruntService.getEmpruntsEnCoursRetard();

        Assert.assertEquals(expectedEmpruntsDto.get(0).getId(), empruntsDtoEnRetard.get(0).getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getLivre().getId(), empruntsDtoEnRetard.get(0).getLivre().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEmprunteur().getId(), empruntsDtoEnRetard.get(0).getEmprunteur().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateEmprunt(), empruntsDtoEnRetard.get(0).getDateEmprunt());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateRetourPrevu(), empruntsDtoEnRetard.get(0).getDateRetourPrevu());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateRetour(), empruntsDtoEnRetard.get(0).getDateRetour());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getProlonge(), empruntsDtoEnRetard.get(0).getProlonge());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEncours(), empruntsDtoEnRetard.get(0).getEncours());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEnRetard(), empruntsDtoEnRetard.get(0).getEnRetard());

        Assert.assertEquals(expectedEmpruntsDto.get(1).getId(), empruntsDtoEnRetard.get(1).getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getLivre().getId(), empruntsDtoEnRetard.get(1).getLivre().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEmprunteur().getId(), empruntsDtoEnRetard.get(1).getEmprunteur().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateEmprunt(), empruntsDtoEnRetard.get(1).getDateEmprunt());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateRetourPrevu(), empruntsDtoEnRetard.get(1).getDateRetourPrevu());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateRetour(), empruntsDtoEnRetard.get(1).getDateRetour());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getProlonge(), empruntsDtoEnRetard.get(1).getProlonge());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEncours(), empruntsDtoEnRetard.get(1).getEncours());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEnRetard(), empruntsDtoEnRetard.get(1).getEnRetard());
    }

    @Test
    public void testShouldGetEmpruntsByUtilisateur() {
        emprunts.add(emprunt2);
        emprunts.add(emprunt3);
        expectedEmpruntsDto.add(expectedEmpruntDto2);
        expectedEmpruntsDto.add(expectedEmpruntDto3);
        Mockito.when(empruntRepository.findEmpruntsByEmprunteur_IdOrderByDateEmpruntAsc(any())).thenReturn(emprunts);
        List<EmpruntDto> empruntsDtoUtilisateur = empruntService.getEmpruntsByUtilisateur(1);

        Assert.assertEquals(expectedEmpruntsDto.get(0).getId(), empruntsDtoUtilisateur.get(0).getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getLivre().getId(), empruntsDtoUtilisateur.get(0).getLivre().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEmprunteur().getId(), empruntsDtoUtilisateur.get(0).getEmprunteur().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateEmprunt(), empruntsDtoUtilisateur.get(0).getDateEmprunt());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateRetourPrevu(), empruntsDtoUtilisateur.get(0).getDateRetourPrevu());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateRetour(), empruntsDtoUtilisateur.get(0).getDateRetour());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getProlonge(), empruntsDtoUtilisateur.get(0).getProlonge());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEncours(), empruntsDtoUtilisateur.get(0).getEncours());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEnRetard(), empruntsDtoUtilisateur.get(0).getEnRetard());

        Assert.assertEquals(expectedEmpruntsDto.get(1).getId(), empruntsDtoUtilisateur.get(1).getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getLivre().getId(), empruntsDtoUtilisateur.get(1).getLivre().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEmprunteur().getId(), empruntsDtoUtilisateur.get(1).getEmprunteur().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateEmprunt(), empruntsDtoUtilisateur.get(1).getDateEmprunt());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateRetourPrevu(), empruntsDtoUtilisateur.get(1).getDateRetourPrevu());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateRetour(), empruntsDtoUtilisateur.get(1).getDateRetour());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getProlonge(), empruntsDtoUtilisateur.get(1).getProlonge());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEncours(), empruntsDtoUtilisateur.get(1).getEncours());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEnRetard(), empruntsDtoUtilisateur.get(1).getEnRetard());
    }

    @Test
    public void testShouldGetEmpruntsTermineByUilisateur() {
        emprunts.add(emprunt2);
        emprunts.add(emprunt3);
        expectedEmpruntsDto.add(expectedEmpruntDto2);
        expectedEmpruntsDto.add(expectedEmpruntDto3);
        Mockito.when(empruntRepository.findEmpruntsByEmprunteur_IdAndEncoursIsFalseOrderByDateEmpruntAsc(any())).thenReturn(emprunts);

        List<EmpruntDto> empruntsDtoTerminesUtilisateur = empruntService.getEmpruntsTermineByUtilisateur(1);
        Assert.assertEquals(expectedEmpruntsDto.get(0).getId(), empruntsDtoTerminesUtilisateur.get(0).getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getLivre().getId(), empruntsDtoTerminesUtilisateur.get(0).getLivre().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEmprunteur().getId(), empruntsDtoTerminesUtilisateur.get(0).getEmprunteur().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateEmprunt(), empruntsDtoTerminesUtilisateur.get(0).getDateEmprunt());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateRetourPrevu(), empruntsDtoTerminesUtilisateur.get(0).getDateRetourPrevu());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateRetour(), empruntsDtoTerminesUtilisateur.get(0).getDateRetour());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getProlonge(), empruntsDtoTerminesUtilisateur.get(0).getProlonge());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEncours(), empruntsDtoTerminesUtilisateur.get(0).getEncours());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEnRetard(), empruntsDtoTerminesUtilisateur.get(0).getEnRetard());

        Assert.assertEquals(expectedEmpruntsDto.get(1).getId(), empruntsDtoTerminesUtilisateur.get(1).getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getLivre().getId(), empruntsDtoTerminesUtilisateur.get(1).getLivre().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEmprunteur().getId(), empruntsDtoTerminesUtilisateur.get(1).getEmprunteur().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateEmprunt(), empruntsDtoTerminesUtilisateur.get(1).getDateEmprunt());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateRetourPrevu(), empruntsDtoTerminesUtilisateur.get(1).getDateRetourPrevu());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateRetour(), empruntsDtoTerminesUtilisateur.get(1).getDateRetour());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getProlonge(), empruntsDtoTerminesUtilisateur.get(1).getProlonge());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEncours(), empruntsDtoTerminesUtilisateur.get(1).getEncours());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEnRetard(), empruntsDtoTerminesUtilisateur.get(1).getEnRetard());
    }

    @Test
    public void testShouldGetEmpruntsEnCoursByUilisateur() {
        emprunts.add(emprunt2);
        emprunts.add(emprunt3);
        expectedEmpruntsDto.add(expectedEmpruntDto2);
        expectedEmpruntsDto.add(expectedEmpruntDto3);
        Mockito.when(empruntRepository.findEmpruntsByEmprunteur_IdAndEncoursIsTrueOrderByDateEmpruntAsc(any())).thenReturn(emprunts);

        List<EmpruntDto> empruntsDtoEnCoursUtilisateur = empruntService.getEmpruntsEnCoursByUtilisateur(1);
        Assert.assertEquals(expectedEmpruntsDto.get(0).getId(), empruntsDtoEnCoursUtilisateur.get(0).getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getLivre().getId(), empruntsDtoEnCoursUtilisateur.get(0).getLivre().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEmprunteur().getId(), empruntsDtoEnCoursUtilisateur.get(0).getEmprunteur().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateEmprunt(), empruntsDtoEnCoursUtilisateur.get(0).getDateEmprunt());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateRetourPrevu(), empruntsDtoEnCoursUtilisateur.get(0).getDateRetourPrevu());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getDateRetour(), empruntsDtoEnCoursUtilisateur.get(0).getDateRetour());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getProlonge(), empruntsDtoEnCoursUtilisateur.get(0).getProlonge());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEncours(), empruntsDtoEnCoursUtilisateur.get(0).getEncours());
        Assert.assertEquals(expectedEmpruntsDto.get(0).getEnRetard(), empruntsDtoEnCoursUtilisateur.get(0).getEnRetard());

        Assert.assertEquals(expectedEmpruntsDto.get(1).getId(), empruntsDtoEnCoursUtilisateur.get(1).getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getLivre().getId(), empruntsDtoEnCoursUtilisateur.get(1).getLivre().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEmprunteur().getId(), empruntsDtoEnCoursUtilisateur.get(1).getEmprunteur().getId());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateEmprunt(), empruntsDtoEnCoursUtilisateur.get(1).getDateEmprunt());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateRetourPrevu(), empruntsDtoEnCoursUtilisateur.get(1).getDateRetourPrevu());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getDateRetour(), empruntsDtoEnCoursUtilisateur.get(1).getDateRetour());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getProlonge(), empruntsDtoEnCoursUtilisateur.get(1).getProlonge());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEncours(), empruntsDtoEnCoursUtilisateur.get(1).getEncours());
        Assert.assertEquals(expectedEmpruntsDto.get(1).getEnRetard(), empruntsDtoEnCoursUtilisateur.get(1).getEnRetard());
    }

    @Test
    public void testShouldRemoveEmprunteur() {
        Mockito.when(empruntRepository.save(Mockito.any(Emprunt.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(empruntRepository.getOne(any())).thenReturn(emprunt1);
        Assert.assertNotNull(expectedEmpruntDto1.getEmprunteur());
        expectedEmpruntDto1 = empruntService.suppressionEmprunteur(expectedEmpruntDto1.getId());
        Assert.assertNull(expectedEmpruntDto1.getEmprunteur());
    }
}