package com.ebibli.service;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.Utilisateur;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.repository.UtilisateurRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class UtilisateurServiceTest {

    UtilisateurRepository utilisateurRepository = Mockito.mock(UtilisateurRepository.class);
    EmpruntClient empruntClient = Mockito.mock(EmpruntClient.class);
    UtilisateurService utilisateurService = new UtilisateurService(utilisateurRepository, empruntClient);

    @Test
    public void testShouldGetUtilisateurById() {
        Utilisateur utilisateur = new Utilisateur()
                .builder()
                .id(1)
                .email("user@oc.com")
                .build();
        Mockito.when(utilisateurRepository.findById(any())).thenReturn(java.util.Optional.of(utilisateur));

        UtilisateurDto utilisateurDto = utilisateurService.getUtlisateurById(1);
        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateurDto.getId());
        Assert.assertEquals("user@oc.com", utilisateurDto.getEmail());
    }

    @Test
    public void testShouldGetUtilisateurByEmail() {
        Utilisateur utilisateur = new Utilisateur()
                .builder()
                .id(1)
                .email("user@oc.com")
                .build();
        Mockito.when(utilisateurRepository.findUtilisateurByEmail(any())).thenReturn(utilisateur);

        UtilisateurDto utilisateurDto = utilisateurService.getUserByEmail("user@oc.com");
        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateurDto.getId());
        Assert.assertEquals("user@oc.com", utilisateurDto.getEmail());
    }

    @Test
    public void testShouldGetAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        utilisateurs.add(new Utilisateur().builder().id(1).email("user1@oc.com").build());
        utilisateurs.add(new Utilisateur().builder().id(2).email("user2@oc.com").build());
        Mockito.when(utilisateurRepository.findAll()).thenReturn(utilisateurs);

        List<Utilisateur> utilisateursDto = utilisateurService.getAllUsers();
        Assert.assertEquals(java.util.Optional.of(1).get(), utilisateursDto.get(0).getId());
        Assert.assertEquals("user1@oc.com", utilisateursDto.get(0).getEmail());
        Assert.assertEquals(java.util.Optional.of(2).get(), utilisateursDto.get(1).getId());
        Assert.assertEquals("user2@oc.com", utilisateursDto.get(1).getEmail());
    }

    @Test
    public void testShouldSaveUtilisateur() {
        Mockito.when(utilisateurRepository.save(Mockito.any(Utilisateur.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .builder()
                .id(1)
                .email("user@oc.com")
                .build();

        UtilisateurDto utilisateurSaved = utilisateurService.save(utilisateurDto);

        Assert.assertEquals(Optional.of(1).get(), utilisateurSaved.getId());
        Assert.assertEquals("user@oc.com", utilisateurSaved.getEmail());
    }

    @Test
    public void testShouldDeleteUtilisateur() {
        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .builder()
                .id(1)
                .email("user@oc.com")
                .build();
        List<EmpruntDto> emprunts = new ArrayList<>();
        emprunts.add(new EmpruntDto().builder().id(1).emprunteur(utilisateurDto).build());
        emprunts.add(new EmpruntDto().builder().id(2).emprunteur(utilisateurDto).build());
        Mockito.when(empruntClient.findEmpruntsEnCoursByUtilisateur(any())).thenReturn(new ArrayList<>());
        Mockito.when(empruntClient.findEmpruntsTermineByUtilisateur(any())).thenReturn(emprunts);

        utilisateurService.delete(utilisateurDto);
        verify(utilisateurRepository).delete(any());
    }

    @Test
    public void testShouldNotDeleteUtilisateurWithEmpruntEnCours() {

        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .builder()
                .id(1)
                .email("user@oc.com")
                .build();
        List<EmpruntDto> emprunts = new ArrayList<>();
        emprunts.add(new EmpruntDto().builder().id(1).emprunteur(utilisateurDto).build());
        emprunts.add(new EmpruntDto().builder().id(2).emprunteur(utilisateurDto).build());
        Mockito.when(empruntClient.findEmpruntsEnCoursByUtilisateur(any())).thenReturn(emprunts);

        Assert.assertFalse(utilisateurService.delete(utilisateurDto));
    }
}