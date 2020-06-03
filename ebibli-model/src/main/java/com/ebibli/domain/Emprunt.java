package com.ebibli.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Emprunt {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Integer id;

    @ManyToOne
    private Livre livre;

    @ManyToOne
    private Utilisateur emprunteur;

    @Basic
    private Date dateEmprunt;

    @Basic
    private Date dateRetourPrevu;

    @Basic
    private Date dateRetour;

    @Basic
    private Boolean prolonge;

    @Basic
    private Boolean encours;

    @Basic
    private Boolean enRetard;

}
