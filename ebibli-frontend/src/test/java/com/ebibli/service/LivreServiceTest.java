package com.ebibli.service;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.LivreClient;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.UtilisateurDto;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivreServiceTest {

    private final LivreClient livreClient = Mockito.mock(LivreClient.class);
    private final EmpruntClient empruntClient = Mockito.mock(EmpruntClient.class);

    private LivreService livreService = new LivreService(livreClient, empruntClient);

    @Test
    public void testShouldGetLivreByOuvrage() {
        EmpruntDto emprunt = new EmpruntDto().builder().livre(new LivreDto().builder().id(2).build()).emprunteur(new UtilisateurDto().builder().id(2).build()).encours(true).dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(2))).dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(2))).build();
        List<LivreDto> expectedLivres = new ArrayList<>();
        expectedLivres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).disponible(true).build());
        expectedLivres.add(new LivreDto().builder().id(2).ouvrage(new OuvrageDto().builder().id(1).build()).disponible(false).empruntEnCours(emprunt).build());
        Mockito.when(livreClient.getAllLivresByOuvrage(1)).thenReturn(expectedLivres);

        List<LivreDto> livres = livreService.getAllLivresByOuvrage(1);

        Assert.assertEquals(java.util.Optional.of(1).get(), livres.get(0).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), livres.get(1).getId());
    }

    @Test
    public void testShouldGetLivreByBibliotheque() {
        EmpruntDto emprunt = new EmpruntDto().builder().livre(new LivreDto().builder().id(2).build()).emprunteur(new UtilisateurDto().builder().id(2).build()).encours(true).dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(2))).dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(2))).build();
        List<LivreDto> expectedLivres = new ArrayList<>();
        expectedLivres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).disponible(true).build());
        expectedLivres.add(new LivreDto().builder().id(2).ouvrage(new OuvrageDto().builder().id(1).build()).disponible(false).empruntEnCours(emprunt).build());
        Mockito.when(livreClient.getAllLivresByBibliotheque(1)).thenReturn(expectedLivres);

        List<LivreDto> livres = livreService.getAllLivresByBibliotheque(1);

        Assert.assertEquals(java.util.Optional.of(1).get(), livres.get(0).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), livres.get(1).getId());
    }

    @Test
    public void testShouldSetEmpruntEnCours() {
        LivreDto livre = new LivreDto().builder().id(1).build();
        Mockito.when(empruntClient.findEmpruntEnCoursByLivre(livre.getId()))
                .thenReturn(new EmpruntDto().builder()
                        .id(1)
                        .emprunteur(new UtilisateurDto().builder().id(1).build())
                        .livre(new LivreDto().builder().id(1).build())
                        .build());

        livreService.setEmpruntEncours(livre);

        Assert.assertEquals(java.util.Optional.of(1).get(), livre.getEmpruntEnCours().getEmprunteur().getId());
    }

    @Test
    public void testShouldGetDispoByOuvrage() {
        List<LivreDto> expectedLivres = new ArrayList<>();
        expectedLivres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).disponible(true).build());
        expectedLivres.add(new LivreDto().builder().id(2).ouvrage(new OuvrageDto().builder().id(1).build()).disponible(true).build());
        Mockito.when(livreClient.getDispoByOuvrage(1)).thenReturn(expectedLivres);

        List<LivreDto> livres = livreService.getDispoByOuvrage(1);

        Assert.assertEquals(java.util.Optional.of(1).get(), livres.get(0).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), livres.get(1).getId());
    }
}