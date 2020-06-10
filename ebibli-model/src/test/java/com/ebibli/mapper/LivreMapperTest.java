package com.ebibli.mapper;

import com.ebibli.domain.Bibliotheque;
import com.ebibli.domain.Livre;
import com.ebibli.domain.Ouvrage;
import com.ebibli.domain.Utilisateur;
import com.ebibli.dto.BibliothequeDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.UtilisateurDto;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class LivreMapperTest {

    @Test
    public void testShouldMapLivreToDto() {
        Livre livre = new Livre()
                .builder()
                .id(1)
                .disponible(true)
                .reserve(true)
                .bibliotheque(new Bibliotheque().builder().id(1).build())
                .nextEmprunteur(new Utilisateur().builder().id(1).build())
                .ouvrage(new Ouvrage().builder().id(1).build())
                .build();

        LivreDto livreDto = Mappers.getMapper(LivreMapper.class).map(livre);

        Assert.assertEquals(java.util.Optional.of(1).get(), livreDto.getId());
        Assert.assertTrue(livreDto.getDisponible());
        Assert.assertTrue(livreDto.getReserve());
        Assert.assertEquals(java.util.Optional.of(1).get(), livreDto.getOuvrage().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), livreDto.getNextEmprunteur().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), livreDto.getBibliotheque().getId());
    }

    @Test
    public void testShouldMapDtoToLivre() {
        LivreDto livreDto = new LivreDto()
                .builder()
                .id(1)
                .disponible(true)
                .reserve(true)
                .bibliotheque(new BibliothequeDto().builder().id(1).build())
                .nextEmprunteur(new UtilisateurDto().builder().id(1).build())
                .ouvrage(new OuvrageDto().builder().id(1).build())
                .build();

        Livre livre = Mappers.getMapper(LivreMapper.class).map(livreDto);

        Assert.assertEquals(java.util.Optional.of(1).get(), livre.getId());
        Assert.assertTrue(livre.getDisponible());
        Assert.assertTrue(livre.getReserve());
        Assert.assertEquals(java.util.Optional.of(1).get(), livre.getOuvrage().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), livre.getNextEmprunteur().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), livre.getBibliotheque().getId());
    }

    @Test
    public void testShouldMapLivresToDtos() {
        Livre livre1 = new Livre()
                .builder()
                .id(1)
                .disponible(true)
                .reserve(true)
                .bibliotheque(new Bibliotheque().builder().id(1).build())
                .nextEmprunteur(new Utilisateur().builder().id(1).build())
                .ouvrage(new Ouvrage().builder().id(1).build())
                .build();
        Livre livre2 = new Livre()
                .builder()
                .id(2)
                .disponible(false)
                .reserve(false)
                .bibliotheque(new Bibliotheque().builder().id(2).build())
                .nextEmprunteur(new Utilisateur().builder().id(2).build())
                .ouvrage(new Ouvrage().builder().id(2).build())
                .build();
        List<Livre> livres = new ArrayList<>();
        livres.add(livre1);
        livres.add(livre2);

        List<LivreDto> livreDtos = Mappers.getMapper(LivreMapper.class).livresToLivreDtos(livres);

        Assert.assertEquals(java.util.Optional.of(1).get(), livreDtos.get(0).getId());
        Assert.assertTrue(livreDtos.get(0).getDisponible());
        Assert.assertTrue(livreDtos.get(0).getReserve());
        Assert.assertEquals(java.util.Optional.of(1).get(), livreDtos.get(0).getOuvrage().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), livreDtos.get(0).getNextEmprunteur().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), livreDtos.get(0).getBibliotheque().getId());

        Assert.assertEquals(java.util.Optional.of(2).get(), livreDtos.get(1).getId());
        Assert.assertFalse(livreDtos.get(1).getDisponible());
        Assert.assertFalse(livreDtos.get(1).getReserve());
        Assert.assertEquals(java.util.Optional.of(2).get(), livreDtos.get(1).getOuvrage().getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), livreDtos.get(1).getNextEmprunteur().getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), livreDtos.get(1).getBibliotheque().getId());
    }

    @Test
    public void testShouldMapNull() {
        Assert.assertNull(Mappers.getMapper(LivreMapper.class).map((Livre) null));
        Assert.assertNull(Mappers.getMapper(LivreMapper.class).map((LivreDto) null));
    }
}