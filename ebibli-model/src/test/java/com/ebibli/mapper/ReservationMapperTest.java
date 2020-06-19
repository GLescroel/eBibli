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
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationMapperTest {

    @Test
    public void testShouldMapReservationToDto() {
        Reservation reservation = new Reservation()
                .builder()
                .id(999)
                .emprunteur(new Utilisateur().builder().email("utilisateurTest@oc.com").role(new Role()).build())
                .ouvrage(new Ouvrage().builder().titre("ouvrage de test").build())
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(3)))
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .dateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)))
                .alerte(true)
                .build();

        ReservationDto reservationDto = Mappers.getMapper(ReservationMapper.class).map(reservation);

        Assert.assertEquals(reservation.getId(), reservationDto.getId());
        Assert.assertEquals(reservation.getEmprunteur().getEmail(), reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals(reservation.getOuvrage().getTitre(), reservationDto.getOuvrage().getTitre());
        Assert.assertEquals(reservation.getDateReservation(), reservationDto.getDateReservation());
        Assert.assertEquals(reservation.getAlerte(), reservationDto.getAlerte());
        Assert.assertEquals(reservation.getDateAlerte(), reservationDto.getDateAlerte());
        Assert.assertEquals(reservation.getDateRetraitMax(), reservationDto.getDateRetraitMax());
    }

    @Test
    public void testShouldMapDtoToReservation() {
        ReservationDto reservationDto = new ReservationDto()
                .builder()
                .id(999)
                .emprunteur(new UtilisateurDto().builder().email("utilisateurTest@oc.com").role(new RoleDto()).build())
                .ouvrage(new OuvrageDto().builder().titre("ouvrage de test").build())
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(3)))
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .dateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)))
                .alerte(true)
                .build();

        Reservation reservation = Mappers.getMapper(ReservationMapper.class).map(reservationDto);

        Assert.assertEquals(reservation.getId(), reservationDto.getId());
        Assert.assertEquals(reservation.getEmprunteur().getEmail(), reservationDto.getEmprunteur().getEmail());
        Assert.assertEquals(reservation.getOuvrage().getTitre(), reservationDto.getOuvrage().getTitre());
        Assert.assertEquals(reservation.getDateReservation(), reservationDto.getDateReservation());
        Assert.assertEquals(reservation.getAlerte(), reservationDto.getAlerte());
        Assert.assertEquals(reservation.getDateAlerte(), reservationDto.getDateAlerte());
        Assert.assertEquals(reservation.getDateRetraitMax(), reservationDto.getDateRetraitMax());
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
        Assert.assertEquals(null, reservationDto.getDateReservation());
        Assert.assertEquals(null, reservationDto.getAlerte());
        Assert.assertEquals(null, reservationDto.getDateAlerte());
        Assert.assertEquals(null, reservationDto.getDateRetraitMax());
    }

    @Test
    public void testShouldMapDtoToReservationWithNullData() {
        ReservationDto reservationDto = new ReservationDto();
        Reservation reservation = Mappers.getMapper(ReservationMapper.class).map(reservationDto);

        Assert.assertEquals(null, reservationDto.getId());
        Assert.assertEquals(null, reservationDto.getEmprunteur());
        Assert.assertEquals(null, reservationDto.getOuvrage());
        Assert.assertEquals(null, reservationDto.getDateReservation());
        Assert.assertEquals(null, reservationDto.getAlerte());
        Assert.assertEquals(null, reservationDto.getDateAlerte());
        Assert.assertEquals(null, reservationDto.getDateRetraitMax());
    }

    @Test
    public void testShouldMapReservationsToDtos() {
        Reservation reservation1 = new Reservation()
                .builder()
                .id(999)
                .emprunteur(new Utilisateur().builder().email("utilisateurTest@oc.com").role(new Role()).build())
                .ouvrage(new Ouvrage().builder().titre("ouvrage de test").build())
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(3)))
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .dateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)))
                .alerte(true)
                .build();
        Reservation reservation2 = new Reservation()
                .builder()
                .id(998)
                .emprunteur(new Utilisateur().builder().email("utilisateuréTest@oc.com").role(new Role()).build())
                .ouvrage(new Ouvrage().builder().titre("ouvrage de test deux").build())
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(3)))
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .dateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)))
                .alerte(true)
                .build();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);

        List<ReservationDto> reservationDtos = Mappers.getMapper(ReservationMapper.class).reservationsToDtos(reservations);

        Assert.assertEquals(reservation1.getId(), reservationDtos.get(0).getId());
        Assert.assertEquals(reservation1.getEmprunteur().getEmail(), reservationDtos.get(0).getEmprunteur().getEmail());
        Assert.assertEquals(reservation1.getOuvrage().getTitre(), reservationDtos.get(0).getOuvrage().getTitre());
        Assert.assertEquals(reservation1.getDateReservation(), reservationDtos.get(0).getDateReservation());
        Assert.assertEquals(reservation1.getAlerte(), reservationDtos.get(0).getAlerte());
        Assert.assertEquals(reservation1.getDateAlerte(), reservationDtos.get(0).getDateAlerte());
        Assert.assertEquals(reservation1.getDateRetraitMax(), reservationDtos.get(0).getDateRetraitMax());

        Assert.assertEquals(reservation2.getId(), reservationDtos.get(1).getId());
        Assert.assertEquals(reservation2.getEmprunteur().getEmail(), reservationDtos.get(1).getEmprunteur().getEmail());
        Assert.assertEquals(reservation2.getOuvrage().getTitre(), reservationDtos.get(1).getOuvrage().getTitre());
        Assert.assertEquals(reservation2.getDateReservation(), reservationDtos.get(1).getDateReservation());
        Assert.assertEquals(reservation2.getAlerte(), reservationDtos.get(1).getAlerte());
        Assert.assertEquals(reservation2.getDateAlerte(), reservationDtos.get(1).getDateAlerte());
        Assert.assertEquals(reservation2.getDateRetraitMax(), reservationDtos.get(1).getDateRetraitMax());
    }

}