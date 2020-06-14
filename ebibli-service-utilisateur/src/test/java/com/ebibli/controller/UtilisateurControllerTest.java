package com.ebibli.controller;

import com.ebibli.domain.Utilisateur;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.service.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class UtilisateurControllerTest {

    protected MockMvc mockMvc;
    @LocalServerPort
    protected int serverPort;
    protected String apiURL = "http://localhost:" + serverPort;
    protected ObjectMapper mapper = new ObjectMapper();

    private UtilisateurService utilisateurService = Mockito.mock(UtilisateurService.class);
    private UtilisateurController utilisateurController = new UtilisateurController(utilisateurService);

    @Test
    public void testShouldGetAllUtilisateurs() throws Exception {
        List<Utilisateur> utilisateursList = new ArrayList<>();
        utilisateursList.add(new Utilisateur());
        utilisateursList.add(new Utilisateur());
        Mockito.when(utilisateurService.getAllUsers()).thenReturn(utilisateursList);

        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();

        mockMvc.perform(get(apiURL + "/utilisateurs"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(utilisateursList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetUtilisateurById() throws Exception {
        UtilisateurDto utilisateur = new UtilisateurDto().builder().id(1).build();
        Mockito.when(utilisateurService.getUtlisateurById(any())).thenReturn(utilisateur);

        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();

        mockMvc.perform(get(apiURL + "/utilisateur/id/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(utilisateur), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetNullUtilisateurById() throws Exception {
        Mockito.when(utilisateurService.getUtlisateurById(any())).thenReturn(null);

        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();

        mockMvc.perform(get(apiURL + "/utilisateur/id/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldGetUtilisateurByEmail() throws Exception {
        UtilisateurDto utilisateur = new UtilisateurDto().builder().id(1).build();
        Mockito.when(utilisateurService.getUserByEmail(any())).thenReturn(utilisateur);

        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();

        String email = "user@oc.fr";
        mockMvc.perform(get(apiURL + "/utilisateur/email/" + email))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(utilisateur), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldCreateUtilisateur() throws Exception {
        UtilisateurDto utilisateur = new UtilisateurDto().builder().id(1).build();
        Mockito.when(utilisateurService.save(any())).thenReturn(utilisateur);

        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();

        mockMvc.perform(post(apiURL + "/utilisateur/creation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new UtilisateurDto())))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldReturn400CreateUtilisateurFailed() throws Exception {
        Mockito.when(utilisateurService.save(any())).thenReturn(null);

        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();

        mockMvc.perform(post(apiURL + "/utilisateur/creation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new UtilisateurDto())))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldDeleteUtilisateur() throws Exception {
        Mockito.when(utilisateurService.delete(any())).thenReturn(true);

        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();

        mockMvc.perform(post(apiURL + "/utilisateur/suppression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new UtilisateurDto())))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(Boolean.toString(true), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldSendOkWhenDeleteUtilisateurFailed() throws Exception {
        Mockito.when(utilisateurService.delete(any())).thenReturn(false);

        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();

        mockMvc.perform(post(apiURL + "/utilisateur/suppression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new UtilisateurDto())))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(Boolean.toString(false), mvcResult.getResponse().getContentAsString());
                });
    }

}