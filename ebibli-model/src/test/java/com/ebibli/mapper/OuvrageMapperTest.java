package com.ebibli.mapper;

import com.ebibli.domain.Ouvrage;
import com.ebibli.dto.OuvrageDto;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class OuvrageMapperTest {

    @Test
    public void testShouldMapOuvrageToDto() {
        Ouvrage ouvrage = new Ouvrage()
                .builder()
                .id(1)
                .titre("titre test")
                .resume("resume test")
                .image("image.jpg")
                .build();
        OuvrageDto ouvrageDto = Mappers.getMapper(OuvrageMapper.class).map(ouvrage);

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrageDto.getId());
        Assert.assertEquals("titre test", ouvrageDto.getTitre());
        Assert.assertEquals("resume test", ouvrageDto.getResume());
        Assert.assertEquals("image.jpg", ouvrageDto.getImage());
    }

    @Test
    public void testShouldMapDtoToOuvrage() {
        OuvrageDto ouvrageDto = new OuvrageDto()
                .builder()
                .id(1)
                .titre("titre test")
                .resume("resume test")
                .image("image.jpg")
                .build();
        Ouvrage ouvrage = Mappers.getMapper(OuvrageMapper.class).map(ouvrageDto);

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getId());
        Assert.assertEquals("titre test", ouvrage.getTitre());
        Assert.assertEquals("resume test", ouvrage.getResume());
        Assert.assertEquals("image.jpg", ouvrage.getImage());
    }

    @Test
    public void testShouldMapOuvragesToDtos() {
        Ouvrage ouvrage1 = new Ouvrage()
                .builder()
                .id(1)
                .titre("titre test")
                .resume("resume test")
                .image("image.jpg")
                .build();
        Ouvrage ouvrage2 = new Ouvrage()
                .builder()
                .id(2)
                .titre("titre test deux")
                .resume("resume test deux")
                .image("image2.jpg")
                .build();
        List<Ouvrage> ouvrages = new ArrayList<>();
        ouvrages.add(ouvrage1);
        ouvrages.add(ouvrage2);
        List<OuvrageDto> ouvrageDtos = Mappers.getMapper(OuvrageMapper.class).ouvragesToouvrageDtos(ouvrages);

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrageDtos.get(0).getId());
        Assert.assertEquals("titre test", ouvrageDtos.get(0).getTitre());
        Assert.assertEquals("resume test", ouvrageDtos.get(0).getResume());
        Assert.assertEquals("image.jpg", ouvrageDtos.get(0).getImage());

        Assert.assertEquals(java.util.Optional.of(2).get(), ouvrageDtos.get(1).getId());
        Assert.assertEquals("titre test deux", ouvrageDtos.get(1).getTitre());
        Assert.assertEquals("resume test deux", ouvrageDtos.get(1).getResume());
        Assert.assertEquals("image2.jpg", ouvrageDtos.get(1).getImage());
    }

    @Test
    public void testShouldMapNull() {
        Assert.assertNull(Mappers.getMapper(OuvrageMapper.class).map((Ouvrage) null));
        Assert.assertNull(Mappers.getMapper(OuvrageMapper.class).map((OuvrageDto) null));
    }
}