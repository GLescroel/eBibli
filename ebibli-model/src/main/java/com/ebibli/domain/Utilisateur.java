package com.ebibli.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Integer id;

    @Basic
    @ColumnTransformer(write = "UPPER(?)")
    private String nom;

    @Basic
    @ColumnTransformer(write = "UPPER(?)")
    private String prenom;

    @Basic
    @ColumnTransformer(write = "UPPER(?)")
    private String email;

    @Basic
    private String password;

    @ManyToOne
    @Valid private Role role;

    @Override
    public String toString() {
        return super.toString();
    }
}
