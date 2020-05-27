package com.ebibli.mapper;

import com.ebibli.domain.Ouvrage;
import com.ebibli.domain.Reservation;
import com.ebibli.domain.Role;
import com.ebibli.domain.Utilisateur;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.dto.RoleDto;
import com.ebibli.dto.UtilisateurDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationMapperTest {

    @Test
    public void testShouldMapReservationToDto() {
        Reservation reservation = new Reservation()
                .builder()
                .id(999)
                .emprunteur(new Utilisateur().builder().email("utilisateurTest@oc.com").role(new Role()).build())
                .ouvrage(new Ouvrage().builder().titre("ouvrage de test").build())
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .alerte(true)
                .build();

        ReservationDto reservationDto = Mappers.getMapper(ReservationMapper.class).map(reservation);

        Assert.assertEquals(reservation.getId(), reservationDto.getId());
        Assert.assertEquals(reservation.getEmprunteur().getEmail(), reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals(reservation.getOuvrage().getTitre(), reservationDto.getOuvrage().getTitre());
        Assert.assertEquals(reservation.getAlerte(), reservationDto.getAlerte());
        Assert.assertEquals(reservation.getDateAlerte(), reservationDto.getDateAlerte());
    }

    @Test
    public void testShouldMapDtoToReservation() {
        ReservationDto reservationDto = new ReservationDto()
                .builder()
                .id(999)
                .emprunteur(new UtilisateurDto().builder().email("utilisateurTest@oc.com").role(new RoleDto()).build())
                .ouvrage(new OuvrageDto().builder().titre("ouvrage de test").build())
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .alerte(true)
                .build();

        Reservation reservation = Mappers.getMapper(ReservationMapper.class).map(reservationDto);

        Assert.assertEquals(reservation.getId(), reservationDto.getId());
        Assert.assertEquals(reservation.getEmprunteur().getEmail(), reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals(reservation.getOuvrage().getTitre(), reservationDto.getOuvrage().getTitre());
        Assert.assertEquals(reservation.getAlerte(), reservationDto.getAlerte());
        Assert.assertEquals(reservation.getDateAlerte(), reservationDto.getDateAlerte());
    }

    @Test
    public void testShouldMapNullReservation() {
        Reservation reservation = null;
        Assert.assertEquals(null, Mappers.getMapper(ReservationMapper.class).map(reservation));
    }

    @Test
    public void testShouldMapNullReservationDto() {
        ReservationDto reservation = null;
        Assert.assertEquals(null, Mappers.getMapper(ReservationMapper.class).map(reservation));
    }

    @Test
    public void testShouldMapReservationToDtoWithNullData() {
        Reservation reservation = new Reservation();
        ReservationDto reservationDto = Mappers.getMapper(ReservationMapper.class).map(reservation);

        Assert.assertEquals(null, reservationDto.getId());
        Assert.assertEquals(null, reservationDto.getEmprunteur());
        Assert.assertEquals(null, reservationDto.getOuvrage());
        Assert.assertEquals(null, reservationDto.getAlerte());
        Assert.assertEquals(null, reservationDto.getDateAlerte());
    }

    @Test
    public void testShouldMapDtoToReservationWithNullData() {
        ReservationDto reservationDto = new ReservationDto();
        Reservation reservation = Mappers.getMapper(ReservationMapper.class).map(reservationDto);

        Assert.assertEquals(null, reservationDto.getId());
        Assert.assertEquals(null, reservationDto.getEmprunteur());
        Assert.assertEquals(null, reservationDto.getOuvrage());
        Assert.assertEquals(null, reservationDto.getAlerte());
        Assert.assertEquals(null, reservationDto.getDateAlerte());
    }

}