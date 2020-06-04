package com.ebibli.domain;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

public class ReservationTest {

    @Test
    public void testReservationGettersAndSetters() {
        Reservation reservation = new Reservation();
        reservation.setId(999);
        reservation.setEmprunteur(new Utilisateur.UtilisateurBuilder().email("utilisateur@oc.com").build());
        reservation.setOuvrage(new Ouvrage.OuvrageBuilder().titre("ouvrage de test").build());
        reservation.setAlerte(true);
        reservation.setDateReservation(Date.valueOf(LocalDate.now().minusDays(3)));
        reservation.setDateAlerte(Date.valueOf(LocalDate.now()));
        reservation.setDateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)));

        Assert.assertEquals(java.util.Optional.of(999).get(), reservation.getId());
        Assert.assertEquals("utilisateur@oc.com", reservation.getEmprunteur().getEmail());
        Assert.assertEquals("ouvrage de test", reservation.getOuvrage().getTitre());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusDays(3)), reservation.getDateReservation());
        Assert.assertEquals(true, reservation.getAlerte());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), reservation.getDateAlerte());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(2)), reservation.getDateRetraitMax());
    }

    @Test
    public void testReservationBuilder() {
        Reservation reservation = new Reservation()
                .builder()
                .id(999)
                .emprunteur(new Utilisateur().builder().email("utilisateur@oc.com").build())
                .ouvrage(new Ouvrage.OuvrageBuilder().titre("ouvrage de test").build())
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(3)))
                .alerte(true)
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .dateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)))
                .build();

        Assert.assertEquals(java.util.Optional.of(999).get(), reservation.getId());
        Assert.assertEquals("utilisateur@oc.com", reservation.getEmprunteur().getEmail());
        Assert.assertEquals("ouvrage de test", reservation.getOuvrage().getTitre());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusDays(3)), reservation.getDateReservation());
        Assert.assertEquals(true, reservation.getAlerte());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), reservation.getDateAlerte());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(2)), reservation.getDateRetraitMax());
    }

}