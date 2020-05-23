package com.ebibli.repository;

import com.ebibli.domain.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OuvrageRepository extends JpaRepository<Ouvrage, Integer> {
    List<Ouvrage> findOuvragesByTitreContainsOrderByTitre(String recherche);
}
