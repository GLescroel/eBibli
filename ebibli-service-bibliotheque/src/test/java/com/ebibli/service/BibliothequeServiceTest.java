package com.ebibli.service;

import com.ebibli.domain.Bibliotheque;
import com.ebibli.dto.BibliothequeDto;
import com.ebibli.repository.BibliothequeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class BibliothequeServiceTest {

    BibliothequeRepository bibliothequeRepository = Mockito.mock(BibliothequeRepository.class);
    private BibliothequeService bibliothequeService = new BibliothequeService(bibliothequeRepository);

    @Test
    public void testShouldGetBibliotheque() {
        Mockito.when(bibliothequeRepository.getOne(any())).thenReturn(new Bibliotheque().builder().id(1).nom("bibliotheque test").build());

        BibliothequeDto bibliotheque = bibliothequeService.getBibliotheque(1);

        Assert.assertEquals(java.util.Optional.of(1).get(), bibliotheque.getId());
        Assert.assertEquals("bibliotheque test", bibliotheque.getNom());
    }

    @Test
    public void testShouldGetAllBibliotheques() {
        List<Bibliotheque> bibliotheques = new ArrayList<>();
        bibliotheques.add(new Bibliotheque().builder().id(1).nom("bibliotheque test un").build());
        bibliotheques.add(new Bibliotheque().builder().id(2).nom("bibliotheque test deux").build());
        Mockito.when(bibliothequeRepository.findAll()).thenReturn(bibliotheques);

        List<BibliothequeDto> bibliothequeDtos = bibliothequeService.getAllBibliotheques();

        Assert.assertEquals(java.util.Optional.of(1).get(), bibliothequeDtos.get(0).getId());
        Assert.assertEquals("bibliotheque test un", bibliothequeDtos.get(0).getNom());
        Assert.assertEquals(java.util.Optional.of(2).get(), bibliothequeDtos.get(1).getId());
        Assert.assertEquals("bibliotheque test deux", bibliothequeDtos.get(1).getNom());
    }
}