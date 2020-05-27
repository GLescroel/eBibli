package com.ebibli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private Integer id;
    private OuvrageDto ouvrage;
    private UtilisateurDto emprunteur;
    private Boolean alerte;
    private Date dateAlerte;

}
