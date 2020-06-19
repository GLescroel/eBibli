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
        empruntDto.setLivre(new LivreDto().builder().id(1).build());
        empruntDto.setEmprunteur(new UtilisateurDto().builder().id(1).build());
        empruntDto.setEnRetard(true);
        empruntDto.setProlonge(false);
        empruntDto.setEncours(true);
        empruntDto.setDateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(3)));
        empruntDto.setDateRetourPrevu(Date.valueOf(LocalDate.now().plusDays(3)));
        empruntDto.setDateRetour(Date.valueOf(LocalDate.now()));

        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getEmprunteur().getId());
        Assert.assertTrue(empruntDto.getEnRetard());
        Assert.assertTrue(empruntDto.getEncours());
        Assert.assertFalse(empruntDto.getProlonge());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), empruntDto.getDateRetour());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(3)), empruntDto.getDateRetourPrevu());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusWeeks(3)), empruntDto.getDateEmprunt());
    }

    @Test
    public void testBuilder() {
        EmpruntDto empruntDto = new EmpruntDto().builder()
                .id(1)
                .livre(new LivreDto.LivreDtoBuilder().id(1).build())
                .emprunteur(new UtilisateurDto.UtilisateurDtoBuilder().id(1).build())
                .enRetard(true)
                .encours(true)
                .prolonge(false)
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusDays(3)))
                .dateRetour(Date.valueOf(LocalDate.now()))
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(3)))
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), empruntDto.getEmprunteur().getId());
        Assert.assertTrue(empruntDto.getEnRetard());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), empruntDto.getDateRetour());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(3)), empruntDto.getDateRetourPrevu());
        Assert.assertTrue(empruntDto.getEncours());
        Assert.assertFalse(empruntDto.getProlonge());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusWeeks(3)), empruntDto.getDateEmprunt());
    }
}