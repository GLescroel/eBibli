package com.ebibli.domain;


import org.junit.Assert;
import org.junit.Test;

public class BibliothequeTest {

    @Test
    public void testGettersAndSetters() {
        Bibliotheque bibliotheque = new Bibliotheque();
        bibliotheque.setId(1);
        bibliotheque.setNom("Bibli test");

        Assert.assertEquals(java.util.Optional.of(1).get(), bibliotheque.getId());
        Assert.assertEquals("Bibli test", bibliotheque.getNom());
    }

    @Test
    public void testBuilder() {
        Bibliotheque bibliotheque = new Bibliotheque()
                .builder()
                .id(1)
                .nom("Bibli test")
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), bibliotheque.getId());
        Assert.assertEquals("Bibli test", bibliotheque.getNom());
    }

}