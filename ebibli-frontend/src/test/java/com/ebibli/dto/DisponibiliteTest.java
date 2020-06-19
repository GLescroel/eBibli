package com.ebibli.dto;

import org.junit.Assert;
import org.junit.Test;

public class DisponibiliteTest {

    @Test
    public void testGettersAndSetters() {
        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setBibliotheque(new BibliothequeDto().builder().id(1).build());
        disponibilite.setDispo(10);

        Assert.assertEquals(java.util.Optional.of(1).get(), disponibilite.getBibliotheque().getId());
        Assert.assertEquals(java.util.Optional.of(10).get(), disponibilite.getDispo());
    }

    @Test
    public void testBuilder() {
        Disponibilite disponibilite = new Disponibilite()
                .builder()
                .bibliotheque(new BibliothequeDto().builder().id(1).build())
                .dispo(10)
                .build();
        Assert.assertEquals(java.util.Optional.of(1).get(), disponibilite.getBibliotheque().getId());
        Assert.assertEquals(java.util.Optional.of(10).get(), disponibilite.getDispo());
    }
}