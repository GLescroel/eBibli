package com.ebibli.mapper;

import com.ebibli.domain.Bibliotheque;
import com.ebibli.dto.BibliothequeDto;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class BibliothequeMapperTest {

    @Test
    public void testShouldMapBibliothequeToDto() {
        Bibliotheque bibliotheque = new Bibliotheque()
                .builder()
                .id(999)
                .nom("biblio test")
                .build();

        BibliothequeDto bibliothequeDto = Mappers.getMapper(BibliothequeMapper.class).map(bibliotheque);

        Assert.assertEquals(java.util.Optional.of(999).get(), bibliothequeDto.getId());
        Assert.assertEquals("biblio test", bibliothequeDto.getNom());
    }

    @Test
    public void testShouldMapDtoToBibliotheque() {
        BibliothequeDto bibliothequeDto = new BibliothequeDto()
                .builder()
                .id(999)
                .nom("biblio test")
                .build();

        Bibliotheque bibliotheque = Mappers.getMapper(BibliothequeMapper.class).map(bibliothequeDto);

        Assert.assertEquals(java.util.Optional.of(999).get(), bibliotheque.getId());
        Assert.assertEquals("biblio test", bibliotheque.getNom());
    }

    @Test
    public void testShouldMapNull() {
        Assert.assertNull(Mappers.getMapper(BibliothequeMapper.class).map((Bibliotheque) null));
        Assert.assertNull(Mappers.getMapper(BibliothequeMapper.class).map((BibliothequeDto) null));
    }

    @Test
    public void testShouldMapBibliothequesToDtos() {
        Bibliotheque bibliotheque1 = new Bibliotheque()
                .builder()
                .id(999)
                .nom("biblio test")
                .build();
        Bibliotheque bibliotheque2 = new Bibliotheque()
                .builder()
                .id(998)
                .nom("biblio test 2")
                .build();
        List<Bibliotheque> bibliotheques = new ArrayList<>();
        bibliotheques.add(bibliotheque1);
        bibliotheques.add(bibliotheque2);

        List<BibliothequeDto> bibliothequeDtos = Mappers.getMapper(BibliothequeMapper.class).bibliothequesToBibliothequeDtos(bibliotheques);

        Assert.assertEquals(java.util.Optional.of(999).get(), bibliothequeDtos.get(0).getId());
        Assert.assertEquals("biblio test", bibliothequeDtos.get(0).getNom());
        Assert.assertEquals(java.util.Optional.of(998).get(), bibliothequeDtos.get(1).getId());
        Assert.assertEquals("biblio test 2", bibliothequeDtos.get(1).getNom());
    }

}