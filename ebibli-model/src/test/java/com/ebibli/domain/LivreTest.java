package com.ebibli.domain;

import org.junit.Assert;
import org.junit.Test;

public class LivreTest {

    @Test
    public void testGettersAndSetters() {
        Livre livre = new Livre();
        livre.setId(1);
        livre.setBibliotheque(new Bibliotheque().builder().nom("bibli test").build());
        livre.setOuvrage(new Ouvrage().builder().titre("ouvrage test").build());
        livre.setDisponible(true);
        livre.setReserve(true);
        livre.setNextEmprunteur(new Utilisateur().builder().nom("emprunteur test").build());

        Assert.assertEquals(java.util.Optional.of(1).get(), livre.getId());
        Assert.assertEquals("bibli test", livre.getBibliotheque().getNom());
        Assert.assertEquals("ouvrage test", livre.getOuvrage().getTitre());
        Assert.assertEquals("emprunteur test", livre.getNextEmprunteur().getNom());
        Assert.assertTrue(livre.getDisponible());
        Assert.assertTrue(livre.getReserve());
    }

    @Test
    public void testBuilder() {
        Livre livre = new Livre().builder()
                .id(1)
                .bibliotheque(new Bibliotheque().builder().nom("bibli test").build())
                .ouvrage(new Ouvrage().builder().titre("ouvrage test").build())
                .disponible(true)
                .reserve(true)
                .nextEmprunteur(new Utilisateur().builder().nom("emprunteur test").build())
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), livre.getId());
        Assert.assertEquals("bibli test", livre.getBibliotheque().getNom());
        Assert.assertEquals("ouvrage test", livre.getOuvrage().getTitre());
        Assert.assertEquals("emprunteur test", livre.getNextEmprunteur().getNom());
        Assert.assertTrue(livre.getDisponible());
        Assert.assertTrue(livre.getReserve());
    }

}