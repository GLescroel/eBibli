package com.ebibli.dto;

import org.junit.Assert;
import org.junit.Test;

public class ReservationDtoTest {

    @Test
    public void testReservationDtoGettersAndSetters() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(999);
        reservationDto.setEmprunteur(new UtilisateurDto.UtilisateurDtoBuilder().email("utilisateur@oc.com").build());
        reservationDto.setOuvrage(new OuvrageDto.OuvrageDtoBuilder().titre("ouvrage de test").build());

        Assert.assertEquals(java.util.Optional.of(999).get(), reservationDto.getId());
        Assert.assertEquals("utilisateur@oc.com", reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals("ouvrage de test", reservationDto.getOuvrage().getTitre());
    }

    @Test
    public void testReservationDtoBuilder() {
        ReservationDto reservationDto = new ReservationDto()
                .builder()
                .id(999)
                .emprunteur(new UtilisateurDto.UtilisateurDtoBuilder().email("utilisateur@oc.com").build())
                .ouvrage(new OuvrageDto.OuvrageDtoBuilder().titre("ouvrage de test").build())
                .build();

        Assert.assertEquals(java.util.Optional.of(999).get(), reservationDto.getId());
        Assert.assertEquals("utilisateur@oc.com", reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals("ouvrage de test", reservationDto.getOuvrage().getTitre());
    }
}