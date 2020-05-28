package com.ebibli.repository;

import com.ebibli.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByOuvrage_IdOrderByDateReservation(Integer ouvrageId);

    List<Reservation> findAllByEmprunteur_IdOrderByDateReservation(Integer emprunteurId);
}
