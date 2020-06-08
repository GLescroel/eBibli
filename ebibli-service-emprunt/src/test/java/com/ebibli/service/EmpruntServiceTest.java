package com.ebibli.service;

import com.ebibli.domain.Emprunt;
import com.ebibli.domain.Livre;
import com.ebibli.domain.LivreClient;
import com.ebibli.domain.ReservationClient;
import com.ebibli.domain.Utilisateur;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.repository.EmpruntRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

public class EmpruntServiceTest {

    private UtilisateurClient utilisateurClient = Mockito.mock(UtilisateurClient.class);
    private LivreClient livreClient = Mockito.mock(LivreClient.class);
    private EmpruntRepository empruntRepository = Mockito.mock(EmpruntRepository.class);
    private ReservationClient reservationClient = Mockito.mock(ReservationClient.class);
    @InjectMocks
    EmpruntService empruntService = new EmpruntService(empruntRepository, utilisateurClient, livreClient, reservationClient);

    @Test
    public void testShouldNotUpgradePretDejaProlonge() {
        Emprunt emprunt = new Emprunt().builder()
                .id(1)
                .livre(new Livre().builder().id(1).build())
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(1)))
                .emprunteur(new Utilisateur().builder().id(1).build())
                .prolonge(true)
                .enRetard(true)
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

}