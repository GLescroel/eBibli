package com.ebibli.dto;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OuvrageDtoTest {

    @Test
    public void testGettersAndSetters() {
        List<Disponibilite> disponibilites = new ArrayList<>();
        disponibilites.add(new Disponibilite().builder().dispo(2).bibliotheque(new BibliothequeDto().builder().id(1).build()).build());
        List<ReservationDto> reservations = new ArrayList<>();
        reservations.add(new ReservationDto().builder().id(1).build());
        OuvrageDto ouvrage = new OuvrageDto();
        ouvrage.setId(1);
        ouvrage.setTitre("ouvrage de test");
        ouvrage.setResume("resume de test");
        ouvrage.setImage("image.jpg");
        ouvrage.setReservationListSizeMax(4);
        ouvrage.setReservationEnCours(true);
        ouvrage.setEmpruntEnCours(true);
        ouvrage.setReservationAvailable(false);
        ouvrage.setNextRetourPrevu(Date.valueOf(LocalDate.now().plusDays(5)));
        ouvrage.setDisponibilite(disponibilites);
        ouvrage.setReservations(reservations);

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getId());
        Assert.assertEquals("ouvrage de test", ouvrage.getTitre());
        Assert.assertEquals("resume de test", ouvrage.getResume());
        Assert.assertEquals("image.jpg", ouvrage.getImage());
        Assert.assertEquals(java.util.Optional.of(4).get(), ouvrage.getReservationListSizeMax());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(5)), ouvrage.getNextRetourPrevu());
        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getReservations().get(0).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), ouvrage.getDisponibilite().get(0).getDispo());
        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getDisponibilite().get(0).getBibliotheque().getId());
        Assert.assertTrue(ouvrage.getReservationEnCours());
        Assert.assertTrue(ouvrage.getEmpruntEnCours());
        Assert.assertFalse(ouvrage.getReservationAvailable());
    }

    @Test
    public void testBuilder() {
        List<Disponibilite> disponibilites = new ArrayList<>();
        disponibilites.add(new Disponibilite().builder().dispo(2).bibliotheque(new BibliothequeDto().builder().id(1).build()).build());
        List<ReservationDto> reservations = new ArrayList<>();
        reservations.add(new ReservationDto().builder().id(1).build());
        OuvrageDto ouvrage = new OuvrageDto().builder()
                .id(1)
                .titre("ouvrage de test")
                .resume("resume de test")
                .image("image.jpg")
                .reservationListSizeMax(4)
                .reservationEnCours(true)
                .empruntEnCours(true)
                .reservationAvailable(false)
                .nextRetourPrevu(Date.valueOf(LocalDate.now().plusDays(5)))
                .disponibilite(disponibilites)
                .reservations(reservations)
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getId());
        Assert.assertEquals("ouvrage de test", ouvrage.getTitre());
        Assert.assertEquals("resume de test", ouvrage.getResume());
        Assert.assertEquals("image.jpg", ouvrage.getImage());
        Assert.assertEquals(java.util.Optional.of(4).get(), ouvrage.getReservationListSizeMax());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(5)), ouvrage.getNextRetourPrevu());
        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getReservations().get(0).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), ouvrage.getDisponibilite().get(0).getDispo());
        Assert.assertEquals(java.util.Optional.of(1).get(), ouvrage.getDisponibilite().get(0).getBibliotheque().getId());
        Assert.assertTrue(ouvrage.getReservationEnCours());
        Assert.assertTrue(ouvrage.getEmpruntEnCours());
        Assert.assertFalse(ouvrage.getReservationAvailable());
    }
}