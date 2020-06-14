package com.ebibli.controller;

import com.ebibli.dto.OuvrageDto;
import com.ebibli.service.OuvrageService;
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

public class OuvrageControllerTest {

    protected MockMvc mockMvc;
    @LocalServerPort
    protected int serverPort;
    protected String apiURL = "http://localhost:" + serverPort;
    protected ObjectMapper mapper = new ObjectMapper();

    private OuvrageService ouvrageService = Mockito.mock(OuvrageService.class);
    private OuvrageController ouvrageController = new OuvrageController(ouvrageService);

    @Test
    public void testShouldGetAllOuvrages() throws Exception {
        List<OuvrageDto> ouvragesList = new ArrayList<>();
        ouvragesList.add(new OuvrageDto());
        ouvragesList.add(new OuvrageDto());
        Mockito.when(ouvrageService.getAllTitles()).thenReturn(ouvragesList);

        mockMvc = MockMvcBuilders.standaloneSetup(ouvrageController).build();

        mockMvc.perform(get(apiURL + "/ouvrages"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(ouvragesList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetOuvrageById() throws Exception {
        OuvrageDto ouvrage = new OuvrageDto().builder().id(1).build();
        Mockito.when(ouvrageService.getOuvrageById(any())).thenReturn(ouvrage);

        mockMvc = MockMvcBuilders.standaloneSetup(ouvrageController).build();

        mockMvc.perform(get(apiURL + "/ouvrage/id/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(ouvrage), mvcResult.getResponse().getContentAsString());
                });
    }
}