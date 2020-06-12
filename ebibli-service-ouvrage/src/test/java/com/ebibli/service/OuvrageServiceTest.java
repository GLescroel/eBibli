package com.ebibli.service;

import com.ebibli.domain.Ouvrage;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.repository.OuvrageRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class OuvrageServiceTest {

    private OuvrageRepository ouvrageRepository = Mockito.mock(OuvrageRepository.class);
    private OuvrageService ouvrageService = new OuvrageService(ouvrageRepository);

    private Ouvrage ouvrage1 = new Ouvrage();
    private Ouvrage ouvrage2 = new Ouvrage();
    private List<Ouvrage> ouvrages = new ArrayList<>();

    @Before
    public void setup() {
        ouvrage1 = new Ouvrage()
                .builder()
                .id(1)
                .titre("titre un")
                .resume("resume un")
                .image("image1.jpg")
                .build();
        ouvrage2 = new Ouvrage()
                .builder()
                .id(2)
                .titre("titre deux")
                .resume("resume deux")
                .image("image2.jpg")
                .build();
        ouvrages.add(ouvrage1);
        ouvrages.add(ouvrage2);
    }

    @Test
    public void testShouldGetAllOuvrages() {
        Mockito.when(ouvrageRepository.findAll()).thenReturn(ouvrages);
        List<OuvrageDto> ouvrageDtos = ouvrageService.getAllTitles();

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrageDtos.get(0).getId());
        Assert.assertEquals("titre un", ouvrageDtos.get(0).getTitre());
        Assert.assertEquals("resume un", ouvrageDtos.get(0).getResume());
        Assert.assertEquals("image1.jpg", ouvrageDtos.get(0).getImage());

        Assert.assertEquals(java.util.Optional.of(2).get(), ouvrageDtos.get(1).getId());
        Assert.assertEquals("titre deux", ouvrageDtos.get(1).getTitre());
        Assert.assertEquals("resume deux", ouvrageDtos.get(1).getResume());
        Assert.assertEquals("image2.jpg", ouvrageDtos.get(1).getImage());
    }

    @Test
    public void testShouldGetOuvrageById() {
        Mockito.when(ouvrageRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(ouvrage1));
        Mockito.when(ouvrageRepository.findById(2)).thenReturn(java.util.Optional.ofNullable(ouvrage2));
        OuvrageDto ouvrageDto = ouvrageService.getOuvrageById(2);

        Assert.assertEquals(java.util.Optional.of(2).get(), ouvrageDto.getId());
        Assert.assertEquals("titre deux", ouvrageDto.getTitre());
        Assert.assertEquals("resume deux", ouvrageDto.getResume());
        Assert.assertEquals("image2.jpg", ouvrageDto.getImage());
    }
}