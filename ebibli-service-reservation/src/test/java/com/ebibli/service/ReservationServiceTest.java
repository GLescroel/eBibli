package com.ebibli.service;

import com.ebibli.configuration.EmailConfiguration;
import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.LivreClient;
import com.ebibli.domain.Ouvrage;
import com.ebibli.domain.Reservation;
import com.ebibli.domain.Utilisateur;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.exception.FunctionalException;
import com.ebibli.repository.ReservationRepository;
import com.ebibli.transport.MyTransport;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.mail.MessagingException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

public class ReservationServiceTest {

    private ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
    private LivreClient livreClient = Mockito.mock(LivreClient.class);
    private EmpruntClient empruntClient = Mockito.mock(EmpruntClient.class);
    private EmailConfiguration emailConfiguration = Mockito.mock(EmailConfiguration.class);
    private MyTransport myTransport = Mockito.mock(MyTransport.class);
    private ReservationService reservationService = new ReservationService(reservationRepository, livreClient, empruntClient, emailConfiguration, myTransport);

    @Test
    public void testShouldSaveReservation() {
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ReservationDto reservation = new ReservationDto()
                .builder()
                .ouvrage(new OuvrageDto().builder().id(1).build())
                .emprunteur(new UtilisateurDto().builder().id(1).build())
                .build();

        ReservationDto reservationSaved = reservationService.saveReservation(reservation);

        Assert.assertEquals(java.util.Optional.of(1).get(), reservationSaved.getOuvrage().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), reservationSaved.getEmprunteur().getId());
    }

    @Test
    public void testShouldCreateReservation() {
        List<LivreDto> livres = new ArrayList<>();
        livres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        livres.add(new LivreDto().builder().id(2).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        livres.add(new LivreDto().builder().id(3).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        Mockito.when(livreClient.getLivresByOuvrage(any())).thenReturn(livres);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation().builder().emprunteur(new Utilisateur().builder().id(2).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        reservations.add(new Reservation().builder().emprunteur(new Utilisateur().builder().id(3).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        List<EmpruntDto> emprunts = new ArrayList<>();
        emprunts.add(new EmpruntDto().builder().emprunteur(new UtilisateurDto().builder().id(1).build()).livre(new LivreDto().builder().ouvrage(new OuvrageDto().builder().id(2).build()).build()).build());
        emprunts.add(new EmpruntDto().builder().emprunteur(new UtilisateurDto().builder().id(1).build()).livre(new LivreDto().builder().ouvrage(new OuvrageDto().builder().id(3).build()).build()).build());
        Mockito.when(empruntClient.findEmpruntsEnCoursByUtilisateur(any())).thenReturn(emprunts);
        Mockito.when(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(any())).thenReturn(reservations);
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ReservationDto reservation = new ReservationDto()
                .builder()
                .ouvrage(new OuvrageDto().builder().id(1).build())
                .emprunteur(new UtilisateurDto().builder().id(1).build())
                .build();

        ReservationDto reservationSaved = reservationService.createReservation(reservation);

        Assert.assertEquals(java.util.Optional.of(1).get(), reservationSaved.getOuvrage().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), reservationSaved.getEmprunteur().getId());
        Assert.assertEquals(Date.valueOf(LocalDate.now()), reservationSaved.getDateReservation());
        Assert.assertNull(reservationSaved.getDateAlerte());
        Assert.assertNull(reservationSaved.getDateRetraitMax());
        Assert.assertFalse(reservationSaved.getAlerte());
    }

    @Test(expected = FunctionalException.class)
    public void testShouldThrowExceptionNoUtilisateur() {
        ReservationDto reservation = new ReservationDto()
                .builder()
                .ouvrage(new OuvrageDto().builder().id(1).build())
                .build();

        reservationService.createReservation(reservation);
    }

    @Test(expected = FunctionalException.class)
    public void testShouldThrowExceptionNoOuvrage() {
        ReservationDto reservation = new ReservationDto()
                .builder()
                .emprunteur(new UtilisateurDto().builder().id(1).build())
                .build();

        reservationService.createReservation(reservation);
    }

    @Test(expected = FunctionalException.class)
    public void testShouldThrowExceptionSameReservation() {
        List<LivreDto> livres = new ArrayList<>();
        livres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        livres.add(new LivreDto().builder().id(2).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        livres.add(new LivreDto().builder().id(3).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        Mockito.when(livreClient.getLivresByOuvrage(any())).thenReturn(livres);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation().builder().emprunteur(new Utilisateur().builder().id(1).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        Mockito.when(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(any())).thenReturn(reservations);
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ReservationDto reservation = new ReservationDto()
                .builder()
                .ouvrage(new OuvrageDto().builder().id(1).build())
                .emprunteur(new UtilisateurDto().builder().id(1).build())
                .build();

        reservationService.createReservation(reservation);
    }

    @Test(expected = FunctionalException.class)
    public void testShouldThrowExceptionAlreadyEmprunt() {
        List<LivreDto> livres = new ArrayList<>();
        livres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        livres.add(new LivreDto().builder().id(2).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        livres.add(new LivreDto().builder().id(3).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        Mockito.when(livreClient.getLivresByOuvrage(any())).thenReturn(livres);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation().builder().emprunteur(new Utilisateur().builder().id(2).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        Mockito.when(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(any())).thenReturn(reservations);
        List<EmpruntDto> emprunts = new ArrayList<>();
        emprunts.add(new EmpruntDto().builder().emprunteur(new UtilisateurDto().builder().id(1).build()).livre(new LivreDto().builder().ouvrage(new OuvrageDto().builder().id(1).build()).build()).build());
        Mockito.when(empruntClient.findEmpruntsEnCoursByUtilisateur(any())).thenReturn(emprunts);
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ReservationDto reservation = new ReservationDto()
                .builder()
                .ouvrage(new OuvrageDto().builder().id(1).build())
                .emprunteur(new UtilisateurDto().builder().id(1).build())
                .build();

        reservationService.createReservation(reservation);
    }

    @Test(expected = FunctionalException.class)
    public void testShouldThrowExceptionMaxList() {
        List<LivreDto> livres = new ArrayList<>();
        livres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        livres.add(new LivreDto().builder().id(2).ouvrage(new OuvrageDto().builder().id(1).build()).build());
        Mockito.when(livreClient.getLivresByOuvrage(any())).thenReturn(livres);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation().builder().emprunteur(new Utilisateur().builder().id(2).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        reservations.add(new Reservation().builder().emprunteur(new Utilisateur().builder().id(3).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        reservations.add(new Reservation().builder().emprunteur(new Utilisateur().builder().id(4).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        reservations.add(new Reservation().builder().emprunteur(new Utilisateur().builder().id(5).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        Mockito.when(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(any())).thenReturn(reservations);
        List<EmpruntDto> emprunts = new ArrayList<>();
        emprunts.add(new EmpruntDto().builder().emprunteur(new UtilisateurDto().builder().id(7).build()).livre(new LivreDto().builder().ouvrage(new OuvrageDto().builder().id(1).build()).build()).build());
        Mockito.when(empruntClient.findEmpruntsEnCoursByUtilisateur(any())).thenReturn(emprunts);
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ReservationDto reservation = new ReservationDto()
                .builder()
                .ouvrage(new OuvrageDto().builder().id(1).build())
                .emprunteur(new UtilisateurDto().builder().id(1).build())
                .build();

        reservationService.createReservation(reservation);
    }

    @Test
    public void testShouldGetReservationsByEmprunteur() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation().builder().id(1).emprunteur(new Utilisateur().builder().id(1).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        reservations.add(new Reservation().builder().id(2).emprunteur(new Utilisateur().builder().id(1).build()).ouvrage(new Ouvrage().builder().id(2).build()).build());
        reservations.add(new Reservation().builder().id(3).emprunteur(new Utilisateur().builder().id(1).build()).ouvrage(new Ouvrage().builder().id(3).build()).build());
        reservations.add(new Reservation().builder().id(4).emprunteur(new Utilisateur().builder().id(1).build()).ouvrage(new Ouvrage().builder().id(4).build()).build());
        Mockito.when(reservationRepository.findAllByEmprunteur_IdOrderByDateReservation(1)).thenReturn(reservations);

        List<ReservationDto> reservationsUtilisateur = reservationService.getAllReservationsByEmprunteur(1);

        for (int i = 0; i < reservations.size(); i++) {
            Assert.assertEquals(reservations.get(i).getId(), reservationsUtilisateur.get(i).getId());
            Assert.assertEquals(reservations.get(i).getOuvrage().getId(), reservationsUtilisateur.get(i).getOuvrage().getId());
        }

    }

    @Test
    public void testShouldGetReservationsByOuvrage() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation().builder().id(1).emprunteur(new Utilisateur().builder().id(1).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        reservations.add(new Reservation().builder().id(2).emprunteur(new Utilisateur().builder().id(2).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        reservations.add(new Reservation().builder().id(3).emprunteur(new Utilisateur().builder().id(3).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        reservations.add(new Reservation().builder().id(4).emprunteur(new Utilisateur().builder().id(4).build()).ouvrage(new Ouvrage().builder().id(1).build()).build());
        Mockito.when(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(1)).thenReturn(reservations);

        List<ReservationDto> reservationsUtilisateur = reservationService.getAllReservationsByOuvrage(1);

        for (int i = 0; i < reservations.size(); i++) {
            Assert.assertEquals(reservations.get(i).getId(), reservationsUtilisateur.get(i).getId());
            Assert.assertEquals(reservations.get(i).getOuvrage().getId(), reservationsUtilisateur.get(i).getOuvrage().getId());
        }
    }

    @Test
    public void testShouldCancelReservationById() throws MessagingException {
        Reservation reservation = new Reservation()
                .builder()
                .id(1)
                .ouvrage(new Ouvrage().builder().id(2).build())
                .emprunteur(new Utilisateur().builder().id(4).build())
                .alerte(true)
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(5)))
                .dateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)))
                .build();
        Mockito.when(reservationRepository.getOne(any())).thenReturn(reservation);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        Mockito.when(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(any())).thenReturn(reservations);
        List<LivreDto> livres = new ArrayList<>();
        livres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(2).build()).nextEmprunteur(new UtilisateurDto().builder().id(4).build()).build());
        Mockito.when(livreClient.getLivresByOuvrage(any())).thenReturn(livres);
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        doNothing().when(reservationRepository).deleteById(any());

        reservationService.cancelReservation(1);
        Mockito.verify(reservationRepository).deleteById(any());
    }

    @Test
    public void testShouldCancelReservation() throws MessagingException {
        Reservation reservation = new Reservation()
                .builder()
                .id(1)
                .ouvrage(new Ouvrage().builder().id(2).build())
                .emprunteur(new Utilisateur().builder().id(4).build())
                .alerte(true)
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(5)))
                .dateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)))
                .build();
        Mockito.when(reservationRepository.getOne(any())).thenReturn(reservation);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        Mockito.when(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(any())).thenReturn(reservations);
        List<LivreDto> livres = new ArrayList<>();
        livres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(2).build()).nextEmprunteur(new UtilisateurDto().builder().id(4).build()).build());
        Mockito.when(livreClient.getLivresByOuvrage(any())).thenReturn(livres);
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        doNothing().when(reservationRepository).deleteById(any());

        reservationService.cancelReservation(2, 4);
        Mockito.verify(reservationRepository).deleteById(any());
    }

    @Test
    public void testShouldCheckNextReservation() throws MessagingException {
        LivreDto livre = new LivreDto()
                .builder()
                .id(1)
                .ouvrage(new OuvrageDto().builder().id(2).build())
                .build();
        Reservation reservation = new Reservation()
                .builder()
                .id(1)
                .ouvrage(new Ouvrage().builder().id(2).build())
                .emprunteur(new Utilisateur().builder().id(4).build())
                .alerte(true)
                .dateAlerte(Date.valueOf(LocalDate.now()))
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(5)))
                .dateRetraitMax(Date.valueOf(LocalDate.now().plusDays(2)))
                .build();
        Reservation reservation2 = new Reservation()
                .builder()
                .id(2)
                .ouvrage(new Ouvrage().builder().id(2).build())
                .emprunteur(new Utilisateur().builder().id(1).email("user@oc.com").build())
                .alerte(false)
                .dateAlerte(null)
                .dateReservation(Date.valueOf(LocalDate.now().minusDays(4)))
                .dateRetraitMax(null)
                .build();
        Mockito.when(reservationRepository.getOne(any())).thenReturn(reservation);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        reservations.add(reservation2);
        Mockito.when(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(any())).thenReturn(reservations);
        List<LivreDto> livres = new ArrayList<>();
        livres.add(new LivreDto().builder().id(1).ouvrage(new OuvrageDto().builder().id(2).build()).nextEmprunteur(new UtilisateurDto().builder().id(4).build()).build());
        Mockito.when(livreClient.getLivresByOuvrage(any())).thenReturn(livres);
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        doNothing().when(livreClient).setLivreReserve(any(), any());

        Mockito.when(emailConfiguration.getProtocol()).thenReturn("smtp");
        Mockito.when(emailConfiguration.getAuth()).thenReturn(false);
        Mockito.when(emailConfiguration.getStarttls()).thenReturn(false);
        Mockito.when(emailConfiguration.getHost()).thenReturn("localhost");
        Mockito.when(emailConfiguration.getPort()).thenReturn(25);

        ArgumentCaptor<Reservation> argument = ArgumentCaptor.forClass(Reservation.class);

        reservationService.checkNextReservation(livre);

        Mockito.verify(reservationRepository).save(argument.capture());
        Assert.assertEquals(reservation2.getId(), argument.getValue().getId());
        Assert.assertTrue(argument.getValue().getAlerte());
        Assert.assertNotNull(argument.getValue().getDateAlerte());
    }

    @Test
    public void testShouldGetAllReservationsToCancel() {
        Reservation reservation = new Reservation()
                .builder()
                .id(1)
                .ouvrage(new Ouvrage().builder().id(2).build())
                .emprunteur(new Utilisateur().builder().id(4).build())
                .alerte(true)
                .dateAlerte(Date.valueOf(LocalDate.now().minusDays(3)))
                .dateReservation(Date.valueOf(LocalDate.now().minusWeeks(5)))
                .dateRetraitMax(Date.valueOf(LocalDate.now().minusWeeks(1)))
                .build();
        Reservation reservation2 = new Reservation()
                .builder()
                .id(2)
                .ouvrage(new Ouvrage().builder().id(2).build())
                .emprunteur(new Utilisateur().builder().id(1).email("user@oc.com").build())
                .alerte(true)
                .dateAlerte(Date.valueOf(LocalDate.now().minusDays(3)))
                .dateReservation(Date.valueOf(LocalDate.now().minusWeeks(5)))
                .dateRetraitMax(Date.valueOf(LocalDate.now().minusWeeks(1)))
                .build();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        reservations.add(reservation2);
        Mockito.when(reservationRepository.findAllByDateRetraitMaxBeforeOrderById(any())).thenReturn(reservations);

        List<ReservationDto> reservationsToCancel = reservationService.getAllReservationsToCancel();

        Assert.assertEquals(java.util.Optional.of(1).get(), reservationsToCancel.get(0).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), reservationsToCancel.get(1).getId());
    }
}