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
public class EmpruntDto {

    private Integer id;
    private LivreDto livre;
    private UtilisateurDto emprunteur;
    private Date dateRetourPrevu;
    private Date dateRetour;
    private Boolean enRetard;

}
