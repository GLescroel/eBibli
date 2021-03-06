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
        utilisateurDto.setPassword("123456");
        utilisateurDto.setRole(new RoleDto().builder().id(999).role("role test").build());

        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateurDto.getId());
        Assert.assertEquals("utilisateur@oc.com", utilisateurDto.getEmail());
        Assert.assertEquals("Nom utilisateur", utilisateurDto.getNom());
        Assert.assertEquals("Prenom utilisateur", utilisateurDto.getPrenom());
        Assert.assertEquals("123456", utilisateurDto.getPassword());
        Assert.assertEquals("role test", utilisateurDto.getRole().getRole());
        Assert.assertEquals(java.util.Optional.of(999).get(), utilisateurDto.getRole().getId());
    }

    @Test
    public void testBuilder() {
        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .builder()
                .id(1)
                .email("utilisateur@oc.com")
                .nom("Nom utilisateur")
                .prenom("Prenom utilisateur")
                .password("123456")
                .role(new RoleDto().builder().id(999).role("role test").build())
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateurDto.getId());
        Assert.assertEquals("utilisateur@oc.com", utilisateurDto.getEmail());
        Assert.assertEquals("Nom utilisateur", utilisateurDto.getNom());
        Assert.assertEquals("Prenom utilisateur", utilisateurDto.getPrenom());
        Assert.assertEquals("123456", utilisateurDto.getPassword());
        Assert.assertEquals("role test", utilisateurDto.getRole().getRole());
        Assert.assertEquals(java.util.Optional.of(999).get(), utilisateurDto.getRole().getId());
    }

    @Test
    public void testMethods() {
        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .builder()
                .id(1)
                .email("utilisateur@oc.com")
                .nom("Nom utilisateur")
                .prenom("Prenom utilisateur")
                .password("123456")
                .role(new RoleDto().builder().id(999).role("role test").build())
                .build();
        Assert.assertEquals("Nom utilisateur", utilisateurDto.getUsername());
        Assert.assertEquals("[role test]", utilisateurDto.getAuthorities().toString());
        Assert.assertFalse(utilisateurDto.isCredentialsNonExpired());
        Assert.assertFalse(utilisateurDto.isEnabled());
        Assert.assertFalse(utilisateurDto.isAccountNonLocked());
        Assert.assertFalse(utilisateurDto.isAccountNonExpired());
    }
}