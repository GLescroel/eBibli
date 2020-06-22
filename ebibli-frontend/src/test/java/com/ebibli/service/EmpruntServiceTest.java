package com.ebibli.service;

import com.ebibli.domain.EmpruntClient;
import com.ebibli.dto.EmpruntDto;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class EmpruntServiceTest {

    private final EmpruntClient empruntClient = Mockito.mock(EmpruntClient.class);

    private EmpruntService empruntService = new EmpruntService(empruntClient);

    @Test
    public void testShoudFindEmpruntsEnCoursByUtilisateur() {
        List<EmpruntDto> expectedEmprunts = new ArrayList<>();
        expectedEmprunts.add(new EmpruntDto().builder().id(1).build());
        expectedEmprunts.add(new EmpruntDto().builder().id(2).build());
        Mockito.when(empruntClient.findEmpruntsEnCoursByUtilisateur(any())).thenReturn(expectedEmprunts);

        List<EmpruntDto> emprunts = empruntService.findEmpruntsEnCoursByUtilisateur(1);

        Assert.assertEquals(java.util.Optional.of(1).get(), emprunts.get(0).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), emprunts.get(1).getId());
    }

    @Test
    public void testShoudFindEmpruntsTermineByUtilisateur() {
        List<EmpruntDto> expectedEmprunts = new ArrayList<>();
        expectedEmprunts.add(new EmpruntDto().builder().id(1).build());
        expectedEmprunts.add(new EmpruntDto().builder().id(2).build());
        Mockito.when(empruntClient.findEmpruntsTermineByUtilisateur(any())).thenReturn(expectedEmprunts);

        List<EmpruntDto> emprunts = empruntService.findEmpruntsTermineByUtilisateur(1);

        Assert.assertEquals(java.util.Optional.of(1).get(), emprunts.get(0).getId());
        Assert.assertEquals(java.util.Optional.of(2).get(), emprunts.get(1).getId());
    }

    @Test
    public void testShouldUpgradePret() {
        EmpruntDto emprunt = new EmpruntDto().builder().id(1).build();
        Mockito.when(empruntClient.upgradeEmprunt(emprunt.getId())).thenReturn(emprunt);

        EmpruntDto empruntUpgrade = empruntService.upgradePret(emprunt.getId());

        Assert.assertEquals(emprunt.getId(), empruntUpgrade.getId());
    }

}