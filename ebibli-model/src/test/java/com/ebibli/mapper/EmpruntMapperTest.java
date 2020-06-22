package com.ebibli.mapper;


import com.ebibli.domain.Emprunt;
import com.ebibli.domain.Livre;
import com.ebibli.domain.Utilisateur;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.UtilisateurDto;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntMapperTest {

    @Test
    public void testShouldMapEmpruntToDto() {
        Emprunt emprunt = new Emprunt()
                .builder()
                .id(1)
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(2)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(2)))
                .dateRetour(Date.valueOf(LocalDate.now()))
                .encours(false)
                .enRetard(false)
                .prolonge(false)
                .livre(new Livre().builder().id(1).build())
                .emprunteur(new Utilisateur().builder().id(1).build())
                .build();

        EmpruntDto empruntDto = Mappers.getMapper(EmpruntMapper.class).map(emprunt);

        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getId());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusWeeks(2)), empruntDto.getDateEmprunt());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(2)), empruntDto.getDateRetourPrevu());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), empruntDto.getDateRetour());
        Assert.assertFalse(empruntDto.getEncours());
        Assert.assertFalse(empruntDto.getEnRetard());
        Assert.assertFalse(empruntDto.getProlonge());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getEmprunteur().getId());
    }

    @Test
    public void testShouldMapDtoToEmprunt() {
        EmpruntDto empruntDto = new EmpruntDto()
                .builder()
                .id(1)
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(2)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(2)))
                .dateRetour(Date.valueOf(LocalDate.now()))
                .encours(false)
                .enRetard(false)
                .prolonge(false)
                .livre(new LivreDto().builder().id(1).build())
                .emprunteur(new UtilisateurDto().builder().id(1).build())
                .build();

        Emprunt emprunt = Mappers.getMapper(EmpruntMapper.class).map(empruntDto);

        Assert.assertEquals(java.util.Optional.of(1).get(), emprunt.getId());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusWeeks(2)), emprunt.getDateEmprunt());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(2)), emprunt.getDateRetourPrevu());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), emprunt.getDateRetour());
        Assert.assertFalse(emprunt.getEncours());
        Assert.assertFalse(emprunt.getEnRetard());
        Assert.assertFalse(emprunt.getProlonge());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunt.getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunt.getEmprunteur().getId());
    }

    @Test
    public void testShouldMapEmpruntsToDtos() {
        Emprunt emprunt1 = new Emprunt()
                .builder()
                .id(1)
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(2)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusWeeks(2)))
                .dateRetour(Date.valueOf(LocalDate.now()))
                .encours(false)
                .enRetard(false)
                .prolonge(false)
                .livre(new Livre().builder().id(1).build())
                .emprunteur(new Utilisateur().builder().id(1).build())
                .build();
        Emprunt emprunt2 = new Emprunt()
                .builder()
                .id(2)
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(6)))
                .dateRetourPrevu(Date.valueOf(LocalDate.now().minusWeeks(2)))
                .dateRetour(null)
                .encours(true)
                .enRetard(true)
                .prolonge(true)
                .livre(new Livre().builder().id(2).build())
                .emprunteur(new Utilisateur().builder().id(2).build())
                .build();
        List<Emprunt> emprunts = new ArrayList<>();
        emprunts.add(emprunt1);
        emprunts.add(emprunt2);

        List<EmpruntDto> empruntDtos = Mappers.getMapper(EmpruntMapper.class).empruntsToDtos(emprunts);

        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDtos.get(0).getId());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusWeeks(2)), empruntDtos.get(0).getDateEmprunt());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(2)), empruntDtos.get(0).getDateRetourPrevu());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), empruntDtos.get(0).getDateRetour());
        Assert.assertFalse(empruntDtos.get(0).getEncours());
        Assert.assertFalse(empruntDtos.get(0).getEnRetard());
        Assert.assertFalse(empruntDtos.get(0).getProlonge());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDtos.get(0).getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDtos.get(0).getEmprunteur().getId());

        Assert.assertEquals(java.util.Optional.of(2).get(), empruntDtos.get(1).getId());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusWeeks(6)), empruntDtos.get(1).getDateEmprunt());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusWeeks(2)), empruntDtos.get(1).getDateRetourPrevu());
        Assert.assertNull(empruntDtos.get(1).getDateRetour());
        Assert.assertTrue(empruntDtos.get(1).getEncours());
        Assert.assertTrue(empruntDtos.get(1).getEnRetard());
        Assert.assertTrue(empruntDtos.get(1).getProlonge());
        Assert.assertEquals(java.util.Optional.of(2).get(), empruntDtos.get(1).getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), empruntDtos.get(1).getEmprunteur().getId());
    }

    @Test
    public void testShouldMapNull() {
        Assert.assertNull(Mappers.getMapper(EmpruntMapper.class).map((Emprunt) null));
        Assert.assertNull(Mappers.getMapper(EmpruntMapper.class).map((EmpruntDto) null));
    }
}