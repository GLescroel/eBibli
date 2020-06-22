package com.ebibli.dto;

import org.junit.Assert;
import org.junit.Test;

public class OuvrageDtoTest {

    @Test
    public void testGettersAndSetters() {
        OuvrageDto ouvrage = new OuvrageDto();
        ouvrage.setId(1);
        ouvrage.setTitre("ouvrage de test");

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getId());
        Assert.assertEquals("ouvrage de test", ouvrage.getTitre());
    }

    @Test
    public void testBuilder() {
        OuvrageDto ouvrage = new OuvrageDto().builder()
                .id(1)
                .titre("ouvrage de test")
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getId());
        Assert.assertEquals("ouvrage de test", ouvrage.getTitre());
    }
}