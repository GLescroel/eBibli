package com.ebibli.dto;

import org.junit.Assert;
import org.junit.Test;

public class UtilisateurDtoTest {

    @Test
    public void testGettersAndSetters() {
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setId(1);
        utilisateurDto.setEmail("utilisateur@oc.com");
        utilisateurDto.setNom("Nom utilisateur");
        utilisateurDto.setPrenom("Prenom utilisateur");

        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateurDto.getId());
        Assert.assertEquals("utilisateur@oc.com", utilisateurDto.getEmail());
        Assert.assertEquals("Nom utilisateur", utilisateurDto.getNom());
        Assert.assertEquals("Prenom utilisateur", utilisateurDto.getPrenom());
    }

    @Test
    public void testBuilder() {
        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .builder()
                .id(1)
                .email("utilisateur@oc.com")
                .nom("Nom utilisateur")
                .prenom("Prenom utilisateur")
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateurDto.getId());
        Assert.assertEquals("utilisateur@oc.com", utilisateurDto.getEmail());
        Assert.assertEquals("Nom utilisateur", utilisateurDto.getNom());
        Assert.assertEquals("Prenom utilisateur", utilisateurDto.getPrenom());
    }
}