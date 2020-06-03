package com.ebibli.dto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

public class ReservationDtoTest {

    @Test
    public void testReservationDtoGettersAndSetters() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(999);
        reservationDto.setEmprunteur(new UtilisateurDto.UtilisateurDtoBuilder().email("utilisateur@oc.com").build());
        reservationDto.setOuvrage(new OuvrageDto.OuvrageDtoBuilder().titre("ouvrage de test").build());
        reservationDto.setAlerte(true);
        reservationDto.setDateAlerte(Date.valueOf(LocalDate.now()));
        reservationDto.setDateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)));
        reservationDto.setPosition(1);
        reservationDto.setDateReservation(Date.valueOf(LocalDate.now().minusDays(10)));
        reservationDto.setLivre(LivreDto.builder().id(10).build());

        Assert.assertEquals(java.util.Optional.of(999).get(), reservationDto.getId());
        Assert.assertEquals("utilisateur@oc.com", reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals("ouvrage de test", reservationDto.getOuvrage().getTitre());
        Assert.assertEquals(true, reservationDto.getAlerte());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), reservationDto.getDateAlerte());
        Assert.assertEquals(java.util.Optional.of(1).get(), reservationDto.getPosition());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(2)), reservationDto.getDateRetraitMax());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusDays(10)), reservationDto.getDateReservation());
        Assert.assertEquals(java.util.Optional.of(10).get(), reservationDto.getLivre().getId());
    }

    @Test
    public void testReservationDtoBuilder() {
        ReservationDto reservationDto = new ReservationDto()
                .builder()
                .id(999)
                .emprunteur(new UtilisateurDto.UtilisateurDtoBuilder().email("utilisateur@oc.com").build())
                .ouvrage(new OuvrageDto.OuvrageDtoBuilder().titre("ouvrage de test").build())
                .alerte(true)
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .dateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)))
                .position(1)
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(10)))
                .livre(LivreDto.builder().id(10).build())
                .build();

        Assert.assertEquals(java.util.Optional.of(999).get(), reservationDto.getId());
        Assert.assertEquals("utilisateur@oc.com", reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals("ouvrage de test", reservationDto.getOuvrage().getTitre());
        Assert.assertEquals(true, reservationDto.getAlerte());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), reservationDto.getDateAlerte());
        Assert.assertEquals(java.util.Optional.of(1).get(), reservationDto.getPosition());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(2)), reservationDto.getDateRetraitMax());
        Assert.assertEquals(Date.valueOf(LocalDate.now().minusDays(10)), reservationDto.getDateReservation());
        Assert.assertEquals(java.util.Optional.of(10).get(), reservationDto.getLivre().getId());
    }

}