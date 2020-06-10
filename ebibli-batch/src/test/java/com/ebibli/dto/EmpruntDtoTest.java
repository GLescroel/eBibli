package com.ebibli.dto;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

public class EmpruntDtoTest {

    @Test
    public void testGettersAndSetters() {
        EmpruntDto empruntDto = new EmpruntDto();
        empruntDto.setId(1);
        empruntDto.setLivre(new LivreDto.LivreDtoBuilder().id(1).build());
        empruntDto.setEmprunteur(new UtilisateurDto.UtilisateurDtoBuilder().id(1).build());
        empruntDto.setEnRetard(true);
        empruntDto.setDateRetourPrevu(Date.valueOf(LocalDate.now().plusDays(3)));
        empruntDto.setDateRetour(Date.valueOf(LocalDate.now()));

        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getEmprunteur().getId());
        Assert.assertTrue(empruntDto.getEnRetard());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), empruntDto.getDateRetour());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(3)), empruntDto.getDateRetourPrevu());
    }

    @Test
    public void testBuilder() {
        EmpruntDto empruntDto = new EmpruntDto().builder()
                .id(1)
                .livre(new LivreDto.LivreDtoBuilder().id(1).build())
                .emprunteur(new UtilisateurDto.UtilisateurDtoBuilder().id(1).build())
                .enRetard(true)
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusDays(3)))
                .dateRetour(Date.valueOf(LocalDate.now()))
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getEmprunteur().getId());
        Assert.assertTrue(empruntDto.getEnRetard());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), empruntDto.getDateRetour());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(3)), empruntDto.getDateRetourPrevu());
    }
}