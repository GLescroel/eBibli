package com.ebibli.dto;


import org.junit.Assert;
import org.junit.Test;

public class LivreDtoTest {

    @Test
    public void testGettersAndSetters() {
        LivreDto livre = new LivreDto();
        livre.setId(1);
        livre.setBibliotheque(new BibliothequeDto.BibliothequeDtoBuilder().nom("bibli test").build());
        livre.setOuvrage(new OuvrageDto.OuvrageDtoBuilder().titre("ouvrage test").build());
        livre.setDisponible(true);
        livre.setReserve(true);
        livre.setNextEmprunteur(UtilisateurDto.builder().nom("emprunteur test").build());

        Assert.assertEquals(java.util.Optional.of(1).get(), livre.getId());
        Assert.assertEquals("bibli test", livre.getBibliotheque().getNom());
        Assert.assertEquals("ouvrage test", livre.getOuvrage().getTitre());
        Assert.assertEquals("emprunteur test", livre.getNextEmprunteur().getNom());
        Assert.assertTrue(livre.getDisponible());
        Assert.assertTrue(livre.getReserve());
    }

    @Test
    public void testBuilder() {
        LivreDto livre = new LivreDto().builder()
                .id(1)
                .bibliotheque(new BibliothequeDto.BibliothequeDtoBuilder().nom("bibli test").build())
                .ouvrage(new OuvrageDto.OuvrageDtoBuilder().titre("ouvrage test").build())
                .disponible(true)
                .reserve(true)
                .nextEmprunteur(UtilisateurDto.builder().nom("emprunteur test").build())
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), livre.getId());
        Assert.assertEquals("bibli test", livre.getBibliotheque().getNom());
        Assert.assertEquals("ouvrage test", livre.getOuvrage().getTitre());
        Assert.assertEquals("emprunteur test", livre.getNextEmprunteur().getNom());
        Assert.assertTrue(livre.getDisponible());
        Assert.assertTrue(livre.getReserve());
    }
}