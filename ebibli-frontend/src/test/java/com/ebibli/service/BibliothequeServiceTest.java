package com.ebibli.service;

import com.ebibli.domain.BibliothequeClient;
import com.ebibli.dto.BibliothequeDto;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class BibliothequeServiceTest {

    private final BibliothequeClient biblioClients = Mockito.mock(BibliothequeClient.class);

    public BibliothequeService bibliothequeService = new BibliothequeService(biblioClients);

    @Test
    public void testShouldGetBibliothequeById() {
        BibliothequeDto expectedBibliotheque = new BibliothequeDto()
                .builder()
                .id(1)
                .nom("bibli test")
                .build();
        Mockito.when(biblioClients.getBibliotheque(1)).thenReturn(expectedBibliotheque);

        BibliothequeDto bibliotheque = bibliothequeService.getBibliotheque(1);

        Assert.assertEquals(expectedBibliotheque.getId(), bibliotheque.getId());
        Assert.assertEquals(expectedBibliotheque.getNom(), bibliotheque.getNom());
    }

    @Test
    public void testShouldGetAllBibliotheque() {
        List<BibliothequeDto> expectedBibliotheques = new ArrayList<>();
        expectedBibliotheques.add(new BibliothequeDto().builder().id(1).nom("bibli un test").build());
        expectedBibliotheques.add(new BibliothequeDto().builder().id(2).nom("bibli deux test").build());
        Mockito.when(biblioClients.getAllBibliotheques()).thenReturn(expectedBibliotheques);

        List<BibliothequeDto> bibliotheques = bibliothequeService.getAllBibliotheques();

        Assert.assertEquals(expectedBibliotheques.get(0).getId(), bibliotheques.get(0).getId());
        Assert.assertEquals(expectedBibliotheques.get(0).getNom(), bibliotheques.get(0).getNom());
        Assert.assertEquals(expectedBibliotheques.get(1).getId(), bibliotheques.get(1).getId());
        Assert.assertEquals(expectedBibliotheques.get(1).getNom(), bibliotheques.get(1).getNom());

    }

}