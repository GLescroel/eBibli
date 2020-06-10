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
public class Reservation {

        @Id
        @GeneratedValue(generator="increment")
        @GenericGenerator(name="increment", strategy = "increment")
        private Integer id;

        @ManyToOne
        private Ouvrage ouvrage;

        @ManyToOne
        private Utilisateur emprunteur;

        @Basic
        private Date dateReservation;

        @Basic
        private Boolean alerte;

        @Basic
        private Date dateAlerte;

        @Basic
        private Date dateRetraitMax;
}
