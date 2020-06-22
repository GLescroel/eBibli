package com.ebibli.dto;

import org.junit.Assert;
import org.junit.Test;

public class OuvrageDtoTest {

    @Test
    public void testGettersAndSetters() {
        OuvrageDto ouvrage = new OuvrageDto();
        ouvrage.setId(1);
        ouvrage.setTitre("ouvrage de test");
        ouvrage.setResume("resume de test");
        ouvrage.setImage("image.jpg");

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getId());
        Assert.assertEquals("ouvrage de test", ouvrage.getTitre());
        Assert.assertEquals("resume de test", ouvrage.getResume());
        Assert.assertEquals("image.jpg", ouvrage.getImage());
    }

    @Test
    public void testBuilder() {
        OuvrageDto ouvrage = new OuvrageDto().builder()
                .id(1)
                .titre("ouvrage de test")
                .resume("resume de test")
                .image("image.jpg")
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getId());
        Assert.assertEquals("ouvrage de test", ouvrage.getTitre());
        Assert.assertEquals("resume de test", ouvrage.getResume());
        Assert.assertEquals("image.jpg", ouvrage.getImage());
    }
}