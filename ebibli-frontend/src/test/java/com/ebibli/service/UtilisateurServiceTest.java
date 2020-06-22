package com.ebibli.service;

import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.RoleDto;
import com.ebibli.dto.UtilisateurDto;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.ArgumentMatchers.any;

public class UtilisateurServiceTest {

    private final UtilisateurClient utilisateurClient = Mockito.mock(UtilisateurClient.class);

    private UtilisateurService utilisateurService = new UtilisateurService(utilisateurClient);

    @Test
    public void testShouldFindUtilisateurByEmail() {
        UtilisateurDto expectedUtilisateur = new UtilisateurDto()
                .builder()
                .id(1)
                .email("user@oc.com")
                .build();
        Mockito.when(utilisateurClient.getUtilisateurByEmail(any())).thenReturn(expectedUtilisateur);

        UtilisateurDto utilisateur = utilisateurService.findUtilisateurByEmail("user@oc.com");
        Assert.assertEquals(expectedUtilisateur.getId(), utilisateur.getId());
        Assert.assertEquals(expectedUtilisateur.getEmail(), utilisateur.getEmail());
    }

    @Test
    public void testShouldFindUtilisateurByEmail2() {
        UtilisateurDto expectedUtilisateur = new UtilisateurDto()
                .builder()
                .id(1)
                .email("user@oc.com")
                .build();
        Mockito.when(utilisateurClient.getUtilisateurByEmail(any())).thenReturn(expectedUtilisateur);

        UtilisateurDto utilisateur = utilisateurService.getUtilisateurByEmail("user@oc.com").get();
        Assert.assertEquals(expectedUtilisateur.getId(), utilisateur.getId());
        Assert.assertEquals(expectedUtilisateur.getEmail(), utilisateur.getEmail());
    }

    @Test
    public void testShouldFindUtilisateurByEmail3() {
        UtilisateurDto expectedUtilisateur = new UtilisateurDto()
                .builder()
                .id(1)
                .email("user@oc.com")
                .password("123456")
                .nom("nom test")
                .prenom("prenom test")
                .role(new RoleDto().roleAbonne())
                .build();
        Mockito.when(utilisateurClient.getUtilisateurByEmail("user@oc.com")).thenReturn(expectedUtilisateur);

        UserDetails userDetails = utilisateurService.loadUserByUsername("user@oc.com");
        Assert.assertEquals(expectedUtilisateur.getNom(), userDetails.getUsername());
        Assert.assertEquals(expectedUtilisateur.getPassword(), userDetails.getPassword());
    }

    @Test
    public void testShouldSaveNewUser() {
        UtilisateurDto utilisateurToSave = new UtilisateurDto()
                .builder()
                .email("user@oc.com")
                .build();
        Mockito.when(utilisateurClient.save(utilisateurToSave)).thenReturn(utilisateurToSave);

        UtilisateurDto utilisateurSaved = utilisateurService.save(utilisateurToSave);

        Assert.assertEquals(utilisateurSaved.getEmail(), utilisateurToSave.getEmail());
    }

    @Test
    public void testShouldDeleteUser() {
        UtilisateurDto utilisateurToDelete = new UtilisateurDto()
                .builder()
                .id(1)
                .email("user@oc.com")
                .build();
        Mockito.when(utilisateurClient.delete(utilisateurToDelete)).thenReturn(true);

        Assert.assertTrue(utilisateurService.remove(utilisateurToDelete));
    }

}