package com.ebibli.repository;

import com.ebibli.domain.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    List<Livre> findAllByDisponibleTrue();

    Livre save(Livre livre);

    List<Livre> findAllByBibliotheque_IdOrderByOuvrageAsc(Integer id);

    List<Livre> findLivresByOuvrage_IdAndDisponibleIsTrueAndReserveIsFalseOrderByBibliotheque(Integer ouvrageId);

    List<Livre> findLivresByOuvrage_IdOrderByBibliotheque(Integer ouvrageId);
}
