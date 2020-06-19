package com.ebibli.domain;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

public class EmpruntTest {

    @Test
    public void testGettersAndSetters() {
        Emprunt emprunt = new Emprunt();
        emprunt.setId(1);
        emprunt.setLivre(new Livre().builder().id(1).build());
        emprunt.setEmprunteur(new Utilisateur().builder().id(1).build());
        emprunt.setEnRetard(true);
        emprunt.setProlonge(false);
        emprunt.setEncours(true);
        emprunt.setDateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(3)));
        emprunt.setDateRetourPrevu(Date.valueOf(LocalDate.now().plusDays(3)));
        emprunt.setDateRetour(Date.valueOf(LocalDate.now()));

        Assert.assertEquals(java.util.Optional.of(1).get(), emprunt.getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunt.getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunt.getEmprunteur().getId());
        Assert.assertTrue(emprunt.getEnRetard());
        Assert.assertTrue(emprunt.getEncours());
        Assert.assertFalse(emprunt.getProlonge());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), emprunt.getDateRetour());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(3)), emprunt.getDateRetourPrevu());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusWeeks(3)), emprunt.getDateEmprunt());
    }

    @Test
    public void testBuilder() {
        Emprunt emprunt = new Emprunt().builder()
                .id(1)
                .livre(new Livre().builder().id(1).build())
                .emprunteur(new Utilisateur().builder().id(1).build())
                .enRetard(true)
                .encours(true)
                .prolonge(false)
                .dateRetourPrevu(Date.valueOf(LocalDate.now().plusDays(3)))
                .dateRetour(Date.valueOf(LocalDate.now()))
                .dateEmprunt(Date.valueOf(LocalDate.now().minusWeeks(3)))
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), emprunt.getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunt.getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunt.getEmprunteur().getId());
        Assert.assertTrue(emprunt.getEnRetard());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), emprunt.getDateRetour());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(3)), emprunt.getDateRetourPrevu());
        Assert.assertTrue(emprunt.getEncours());
        Assert.assertFalse(emprunt.getProlonge());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusWeeks(3)), emprunt.getDateEmprunt());
    }

}