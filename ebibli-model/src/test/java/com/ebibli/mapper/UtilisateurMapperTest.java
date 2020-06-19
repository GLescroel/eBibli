package com.ebibli.mapper;


import com.ebibli.domain.Role;
import com.ebibli.domain.Utilisateur;
import com.ebibli.dto.UtilisateurDto;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class UtilisateurMapperTest {

    @Test
    public void testShouldMapUtilisateurToDto() {
        Role role = new Role();
        role.setId(1);
        role.setRole("role test");
        Utilisateur utilisateur = new Utilisateur()
                .builder()
                .id(1)
                .email("email@oc.com")
                .nom("nom test")
                .prenom("prenom test")
                .role(role)
                .password("123456")
                .build();

        UtilisateurDto utilisateurDto = Mappers.getMapper(UtilisateurMapper.class).map(utilisateur);

        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateurDto.getId());
        Assert.assertEquals("email@oc.com", utilisateurDto.getEmail());
        Assert.assertEquals("nom test", utilisateurDto.getNom());
        Assert.assertEquals("prenom test", utilisateurDto.getPrenom());
        Assert.assertEquals("123456", utilisateurDto.getPassword());
        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateurDto.getRole().getId());
        Assert.assertEquals("role test", utilisateurDto.getRole().getRole());
    }

    @Test
    public void testShouldMapNull() {
        Assert.assertNull(Mappers.getMapper(UtilisateurMapper.class).map((Utilisateur) null));
        Assert.assertNull(Mappers.getMapper(UtilisateurMapper.class).map((UtilisateurDto) null));
    }
}