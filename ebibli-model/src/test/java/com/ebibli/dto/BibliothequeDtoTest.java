package com.ebibli.dto;

import org.junit.Assert;
import org.junit.Test;

public class BibliothequeDtoTest {

    @Test
    public void testGettersAndSetters() {
        BibliothequeDto bibliothequeDto = new BibliothequeDto();
        bibliothequeDto.setId(1);
        bibliothequeDto.setNom("Bibli test");

        Assert.assertEquals(java.util.Optional.of(1).get(), bibliothequeDto.getId());
        Assert.assertEquals("Bibli test", bibliothequeDto.getNom());
    }

    @Test
    public void testBuilder() {
        BibliothequeDto bibliothequeDto = new BibliothequeDto()
                .builder()
                .id(1)
                .nom("Bibli test")
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), bibliothequeDto.getId());
        Assert.assertEquals("Bibli test", bibliothequeDto.getNom());
    }

}