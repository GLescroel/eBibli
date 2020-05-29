package com.ebibli.dto;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

class ReservationDtoTest {

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

        Assert.assertEquals(java.util.Optional.of(999).get(), reservationDto.getId());
        Assert.assertEquals("utilisateur@oc.com", reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals("ouvrage de test", reservationDto.getOuvrage().getTitre());
        Assert.assertEquals(true, reservationDto.getAlerte());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), reservationDto.getDateAlerte());
        Assert.assertEquals(java.util.Optional.of(1).get(), reservationDto.getPosition());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(2)), reservationDto.getDateRetraitMax());
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
                .build();

        Assert.assertEquals(java.util.Optional.of(999).get(), reservationDto.getId());
        Assert.assertEquals("utilisateur@oc.com", reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals("ouvrage de test", reservationDto.getOuvrage().getTitre());
        Assert.assertEquals(true, reservationDto.getAlerte());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), reservationDto.getDateAlerte());
        Assert.assertEquals(java.util.Optional.of(1).get(), reservationDto.getPosition());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusDays(2)), reservationDto.getDateRetraitMax());
    }

}