package com.ebibli.controller;


import com.ebibli.dto.BibliothequeDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.service.LivreService;
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

public class LivreControllerTest {

    protected MockMvc mockMvc;
    @LocalServerPort
    protected int serverPort;
    protected String apiURL = "http://localhost:" + serverPort;
    protected ObjectMapper mapper = new ObjectMapper();

    private LivreService livreService = Mockito.mock(LivreService.class);
    private LivreController livreController = new LivreController(livreService);

    @Test
    public void testShouldGetAllLivres() throws Exception {
        List<LivreDto> livresList = new ArrayList<>();
        livresList.add(new LivreDto());
        livresList.add(new LivreDto());
        Mockito.when(livreService.getAllLivres()).thenReturn(livresList);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(get(apiURL + "/livres"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livresList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetLivreById() throws Exception {
        LivreDto livre = new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).bibliotheque(new BibliothequeDto().builder().id(1).build()).disponible(true).reserve(false).nextEmprunteur(null).build();
        Mockito.when(livreService.getLivre(anyInt())).thenReturn(livre);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(get(apiURL + "/livre/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livre), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetNullLivreById() throws Exception {
        Mockito.when(livreService.getLivre(anyInt())).thenReturn(null);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(get(apiURL + "/livre/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldGetAllLivresDispo() throws Exception {
        List<LivreDto> livresList = new ArrayList<>();
        livresList.add(new LivreDto());
        livresList.add(new LivreDto());
        Mockito.when(livreService.getAllLivresDispo()).thenReturn(livresList);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(get(apiURL + "/livresDispo"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livresList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetAllLivresDispoByOuvrage() throws Exception {
        List<LivreDto> livresList = new ArrayList<>();
        livresList.add(new LivreDto());
        livresList.add(new LivreDto());
        Mockito.when(livreService.getAllLivresDispoByOuvrage(anyInt())).thenReturn(livresList);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(get(apiURL + "/livresDispo/ouvrage/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livresList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetAllLivresByOuvrage() throws Exception {
        List<LivreDto> livresList = new ArrayList<>();
        livresList.add(new LivreDto());
        livresList.add(new LivreDto());
        Mockito.when(livreService.getAllLivresByOuvrage(anyInt())).thenReturn(livresList);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(get(apiURL + "/livres/ouvrage/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livresList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetAllLivresByBibliotheque() throws Exception {
        List<LivreDto> livresList = new ArrayList<>();
        livresList.add(new LivreDto());
        livresList.add(new LivreDto());
        Mockito.when(livreService.getLivresByBibliotheque(anyInt())).thenReturn(livresList);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(get(apiURL + "/livres/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livresList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldSetPret() throws Exception {
        LivreDto livre = new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).bibliotheque(new BibliothequeDto().builder().id(1).build()).disponible(true).reserve(false).nextEmprunteur(null).build();
        Mockito.when(livreService.setPret(anyInt())).thenReturn(livre);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(post(apiURL + "/livre/1/emprunt"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livre), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldSetRetour() throws Exception {
        LivreDto livre = new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).bibliotheque(new BibliothequeDto().builder().id(1).build()).disponible(true).reserve(false).nextEmprunteur(null).build();
        Mockito.when(livreService.setRetour(anyInt())).thenReturn(livre);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(post(apiURL + "/livre/1/retour"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livre), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldSetReserve() throws Exception {
        LivreDto livre = new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).bibliotheque(new BibliothequeDto().builder().id(1).build()).disponible(true).reserve(false).nextEmprunteur(null).build();
        Mockito.when(livreService.setReserve(anyInt(), anyInt())).thenReturn(livre);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(post(apiURL + "/livre/1/reserve/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livre), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldCancelReservation() throws Exception {
        LivreDto livre = new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).bibliotheque(new BibliothequeDto().builder().id(1).build()).disponible(true).reserve(false).nextEmprunteur(null).build();
        Mockito.when(livreService.cancelReservation(anyInt())).thenReturn(livre);

        mockMvc = MockMvcBuilders.standaloneSetup(livreController).build();

        mockMvc.perform(post(apiURL + "/livre/1/cancelReservation"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(livre), mvcResult.getResponse().getContentAsString());
                });
    }
}