package com.ebibli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private Integer id;
    private OuvrageDto ouvrage;
    private UtilisateurDto emprunteur;
    private Date dateReservation;
    private Boolean alerte;
    private Date dateAlerte;
    private Date dateRetraitMax;
    private Integer position;
    private LivreDto livre;
}
