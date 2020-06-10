package com.ebibli.domain;

import org.junit.Assert;
import org.junit.Test;

public class UtilisateurTest {

    @Test
    public void testGettersAndSetters() {
        Role role = new Role();
        role.setId(999);
        role.setRole("role test");
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1);
        utilisateur.setEmail("utilisateur@oc.com");
        utilisateur.setNom("Nom utilisateur");
        utilisateur.setPrenom("Prenom utilisateur");
        utilisateur.setPassword("123456");
        utilisateur.setRole(role);

        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateur.getId());
        Assert.assertEquals("utilisateur@oc.com", utilisateur.getEmail());
        Assert.assertEquals("Nom utilisateur", utilisateur.getNom());
        Assert.assertEquals("Prenom utilisateur", utilisateur.getPrenom());
        Assert.assertEquals("123456", utilisateur.getPassword());
        Assert.assertEquals("role test", utilisateur.getRole().getRole());
        Assert.assertEquals(java.util.Optional.of(999).get(), utilisateur.getRole().getId());
    }

    @Test
    public void testBuilder() {
        Role role = new Role();
        role.setId(999);
        role.setRole("role test");
        Utilisateur utilisateur = new Utilisateur()
                .builder()
                .id(1)
                .email("utilisateur@oc.com")
                .nom("Nom utilisateur")
                .prenom("Prenom utilisateur")
                .password("123456")
                .role(role)
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateur.getId());
        Assert.assertEquals("utilisateur@oc.com", utilisateur.getEmail());
        Assert.assertEquals("Nom utilisateur", utilisateur.getNom());
        Assert.assertEquals("Prenom utilisateur", utilisateur.getPrenom());
        Assert.assertEquals("123456", utilisateur.getPassword());
        Assert.assertEquals("role test", utilisateur.getRole().getRole());
        Assert.assertEquals(java.util.Optional.of(999).get(), utilisateur.getRole().getId());
    }
}