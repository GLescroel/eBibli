package com.ebibli.controller;

import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.service.ReservationService;
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
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ReservationControllerTest {

    protected MockMvc mockMvc;
    @LocalServerPort
    protected int serverPort;
    protected String apiURL = "http://localhost:" + serverPort;
    protected ObjectMapper mapper = new ObjectMapper();

    private ReservationService reservationService = Mockito.mock(ReservationService.class);
    private ReservationController reservationController = new ReservationController(reservationService);

    @Test
    public void testShouldGetAllReservationsByOuvrage() throws Exception {
        List<ReservationDto> reservationsList = new ArrayList<>();
        reservationsList.add(new ReservationDto());
        reservationsList.add(new ReservationDto());
        Mockito.when(reservationService.getAllReservationsByOuvrage(Mockito.anyInt())).thenReturn(reservationsList);

        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        mockMvc.perform(get(apiURL + "/reservations/ouvrage/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(reservationsList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldGetAllReservationsByEmprunteur() throws Exception {
        List<ReservationDto> reservationsList = new ArrayList<>();
        reservationsList.add(new ReservationDto());
        reservationsList.add(new ReservationDto());
        Mockito.when(reservationService.getAllReservationsByEmprunteur(Mockito.anyInt())).thenReturn(reservationsList);

        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        mockMvc.perform(get(apiURL + "/reservations/abonne/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(reservationsList), mvcResult.getResponse().getContentAsString());
                });
    }

    @Test
    public void testShouldCreateNewReservation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        ReservationDto reservation = new ReservationDto().builder().ouvrage(new OuvrageDto().builder().id(1).build()).build();
        Mockito.when(reservationService.createReservation(any())).thenReturn(reservation);

        mockMvc.perform(post(apiURL + "/reservation/creation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation)))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldNotCreateNewReservation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        Mockito.when(reservationService.createReservation(any())).thenReturn(null);

        mockMvc.perform(post(apiURL + "/reservation/creation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new ReservationDto())))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldCancelReservationById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        doNothing().when(reservationService).cancelReservation(any());

        mockMvc.perform(post(apiURL + "/reservation/suppression/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldCancelReservation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        doNothing().when(reservationService).cancelReservation(any(), any());

            mockMvc.perform(post(apiURL + "/reservation/annulation/1/1"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldCheckNextReservation() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        doNothing().when(reservationService).checkNextReservation(any());

        mockMvc.perform(post(apiURL + "/reservation/notification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new LivreDto())))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                });
    }

    @Test
    public void testShouldGetAllReservationsToCancel() throws Exception {
        List<ReservationDto> reservationsList = new ArrayList<>();
        reservationsList.add(new ReservationDto());
        reservationsList.add(new ReservationDto());
        Mockito.when(reservationService.getAllReservationsToCancel()).thenReturn(reservationsList);

        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();

        mockMvc.perform(get(apiURL + "/reservationsASupprimer"))
                .andExpect(mvcResult -> {
                    Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
                    Assert.assertEquals(mapper.writeValueAsString(reservationsList), mvcResult.getResponse().getContentAsString());
                });
    }

}