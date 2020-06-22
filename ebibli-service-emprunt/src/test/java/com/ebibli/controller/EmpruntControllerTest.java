package com.ebibli.controller;

import com.ebibli.dto.EmpruntDto;
import com.ebibli.service.EmpruntService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class EmpruntControllerTest {

    private MockMvc mockMvc;
    @LocalServerPort
    protected int serverPort;
    private String apiURL = "http://localhost:" + serverPort;
    private ObjectMapper mapper = new ObjectMapper();

    private EmpruntService empruntService = Mockito.mock(EmpruntService.class);
    private EmpruntController empruntController = new EmpruntController(empruntService);

    @Test
    public void testShouldGetEmpruntsEnCoursByLivre() throws Exception {
        EmpruntDto emprunt = new EmpruntDto().builder().id(1).build();
        Mockito.when(empruntService.getEmpruntEnCoursByLivre(anyInt())).thenReturn(emprunt);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(get(apiURL + "/emprunt/encours/livre/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(emprunt), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetAllEmpruntsByUtiliateur() throws Exception {
        List<EmpruntDto> empruntsList = new ArrayList<>();
        empruntsList.add(new EmpruntDto().builder().id(1).build());
        empruntsList.add(new EmpruntDto().builder().id(2).build());
        Mockito.when(empruntService.getEmpruntsByUtilisateur(anyInt())).thenReturn(empruntsList);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(get(apiURL + "/emprunts/abonne/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(empruntsList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetAllEmpruntsEnCoursByUtiliateur() throws Exception {
        List<EmpruntDto> empruntsList = new ArrayList<>();
        empruntsList.add(new EmpruntDto().builder().id(1).build());
        empruntsList.add(new EmpruntDto().builder().id(2).build());
        Mockito.when(empruntService.getEmpruntsEnCoursByUtilisateur(anyInt())).thenReturn(empruntsList);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(get(apiURL + "/emprunts/encours/abonne/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(empruntsList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetAllEmpruntsTermineByUtiliateur() throws Exception {
        List<EmpruntDto> empruntsList = new ArrayList<>();
        empruntsList.add(new EmpruntDto().builder().id(1).build());
        empruntsList.add(new EmpruntDto().builder().id(2).build());
        Mockito.when(empruntService.getEmpruntsTermineByUtilisateur(anyInt())).thenReturn(empruntsList);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(get(apiURL + "/emprunts/termine/abonne/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(empruntsList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetAllEmpruntsEnCoursEnRetard() throws Exception {
        List<EmpruntDto> empruntsList = new ArrayList<>();
        empruntsList.add(new EmpruntDto().builder().id(1).build());
        empruntsList.add(new EmpruntDto().builder().id(2).build());
        Mockito.when(empruntService.getEmpruntsEnCoursRetard()).thenReturn(empruntsList);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(get(apiURL + "/emprunts/retard"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(empruntsList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldCreateNewPret() throws Exception {
        EmpruntDto emprunt = new EmpruntDto().builder().id(1).build();
        Mockito.when(empruntService.newPret(anyInt(), anyInt())).thenReturn(emprunt);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(post(apiURL + "/emprunt/1/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(emprunt), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldCreateNewPretReturnsNull() throws Exception {
        Mockito.when(empruntService.newPret(anyInt(), anyInt())).thenReturn(null);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(post(apiURL + "/emprunt/1/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldClosePret() throws Exception {
        EmpruntDto emprunt = new EmpruntDto().builder().id(1).build();
        Mockito.when(empruntService.closePret(anyInt(), anyInt())).thenReturn(emprunt);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(post(apiURL + "/retour/1/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(emprunt), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldUpgradePret() throws Exception {
        EmpruntDto emprunt = new EmpruntDto().builder().id(1).build();
        Mockito.when(empruntService.upgradePret(anyInt())).thenReturn(emprunt);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(post(apiURL + "/prolongation/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(emprunt), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldRemoveEmprunteur() throws Exception {
        EmpruntDto emprunt = new EmpruntDto().builder().id(1).build();
        Mockito.when(empruntService.suppressionEmprunteur(anyInt())).thenReturn(emprunt);

        mockMvc = MockMvcBuilders.standaloneSetup(empruntController).build();

        mockMvc.perform(post(apiURL + "/emprunt/suppression_emprunteur/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(emprunt), mvcResult.getResponse().getContentAsString());
                });
    }

}