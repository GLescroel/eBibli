package com.ebibli.domain;

import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EmprunteurTest {

    @Test
    public void testGettersAndSetters() {
        List<EmpruntDto> emprunts = new ArrayList<>();
        emprunts.add(new EmpruntDto().builder().livre(new LivreDto().builder().id(1).build()).build());
        Emprunteur emprunteur = new Emprunteur()
                .builder()
                .email("emprunteur@oc.com")
                .nom("Nom emprunteur")
                .prenom("Prenom emprunteur")
                .emprunts(emprunts)
                .empruntsRetard(emprunts)
                .build();

        Assert.assertEquals("Nom emprunteur", emprunteur.getNom());
        Assert.assertEquals("Prenom emprunteur", emprunteur.getPrenom());
        Assert.assertEquals("emprunteur@oc.com", emprunteur.getEmail());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunteur.getEmprunts().get(0).getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunteur.getEmpruntsRetard().get(0).getLivre().getId());
    }

    @Test
    public void testBuilderAndMethod() {
        List<EmpruntDto> emprunts = new ArrayList<>();
        emprunts.add(new EmpruntDto().builder().livre(new LivreDto().builder().id(1).build()).build());
        Emprunteur emprunteur = new Emprunteur();
        emprunteur.setNom("Nom emprunteur");
        emprunteur.setPrenom("Prenom emprunteur");
        emprunteur.setEmail("emprunteur@oc.com");
        emprunteur.setEmprunts(emprunts);
        emprunteur.setEmpruntsRetard(emprunts);
        emprunteur.addEmprunt(new EmpruntDto().builder().id(2).build());
        emprunteur.addEmpruntRetard(new EmpruntDto().builder().id(3).build());

        Assert.assertEquals("Nom emprunteur", emprunteur.getNom());
        Assert.assertEquals("Prenom emprunteur", emprunteur.getPrenom());
        Assert.assertEquals("emprunteur@oc.com", emprunteur.getEmail());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunteur.getEmprunts().get(0).getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(1).get(), emprunteur.getEmpruntsRetard().get(0).getLivre().getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), emprunteur.getEmprunts().get(1).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), emprunteur.getEmpruntsRetard().get(1).getId());
    }
}