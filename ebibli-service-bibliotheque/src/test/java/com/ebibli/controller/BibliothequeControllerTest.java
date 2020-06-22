package com.ebibli.controller;

import com.ebibli.dto.BibliothequeDto;
import com.ebibli.service.BibliothequeService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class BibliothequeControllerTest {

    protected MockMvc mockMvc;
    @LocalServerPort
    protected int serverPort;
    protected String apiURL = "http://localhost:" + serverPort;
    protected ObjectMapper mapper = new ObjectMapper();

    private BibliothequeService bibliothequeService = Mockito.mock(BibliothequeService.class);
    private BibliothequeController bibliothequeController = new BibliothequeController(bibliothequeService);

    @Test
    public void testShouldGetAllBibliotheques() throws Exception {
        List<BibliothequeDto> bibliothequessList = new ArrayList<>();
        bibliothequessList.add(new BibliothequeDto());
        bibliothequessList.add(new BibliothequeDto());
        Mockito.when(bibliothequeService.getAllBibliotheques()).thenReturn(bibliothequessList);

        mockMvc = MockMvcBuilders.standaloneSetup(bibliothequeController).build();

        mockMvc.perform(get(apiURL + "/bibliotheques"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(bibliothequessList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetBibliothequeById() throws Exception {
        BibliothequeDto bibliotheque = new BibliothequeDto().builder().id(1).build();
        Mockito.when(bibliothequeService.getBibliotheque(any())).thenReturn(bibliotheque);

        mockMvc = MockMvcBuilders.standaloneSetup(bibliothequeController).build();

        mockMvc.perform(get(apiURL + "/bibliotheque/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(bibliotheque), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetNullBibliothequeById() throws Exception {
        Mockito.when(bibliothequeService.getBibliotheque(any())).thenReturn(null);

        mockMvc = MockMvcBuilders.standaloneSetup(bibliothequeController).build();

        mockMvc.perform(get(apiURL + "/bibliotheque/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus());
                });
    }
}