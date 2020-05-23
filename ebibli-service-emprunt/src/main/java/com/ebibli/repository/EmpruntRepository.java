package com.ebibli.repository;

import com.ebibli.domain.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Integer> {


    Emprunt findEmpruntByLivre_IdAndEncoursIsTrue(Integer livreId);

    List<Emprunt> findEmpruntsByEmprunteur_IdOrderByDateEmpruntAsc(Integer emprunteurId);

    List<Emprunt> findEmpruntsByEmprunteur_IdAndEncoursIsTrueOrderByDateEmpruntAsc(Integer emprunteurId);

    List<Emprunt> findEmpruntsByEmprunteur_IdAndEncoursIsFalseOrderByDateEmpruntAsc(Integer emprunteurId);

    List<Emprunt> findEmpruntsByEncoursIsTrueAndEnRetardIsTrueOrderByEmprunteurIdAsc();

    List<Emprunt> findEmpruntsByEncoursIsTrueAndDateRetourPrevuBeforeOrderByEmprunteur(Date valueOf);
}
