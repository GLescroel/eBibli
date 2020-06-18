package com.ebibli.batch.processor;

import com.ebibli.batch.config.EmailConfiguration;
import com.ebibli.dto.BibliothequeDto;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.service.EmpruntService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReminderJobProcessorTest {

    private EmpruntService empruntService = Mockito.mock(EmpruntService.class);
    private EmailConfiguration emailConfiguration = Mockito.mock(EmailConfiguration.class);

    @Test
    public void testShouldProcessEmprunt() throws Exception {
        Mockito.when(emailConfiguration.getHost()).thenReturn("localhost");
        Mockito.when(emailConfiguration.getPort()).thenReturn(25);
        Mockito.when(emailConfiguration.getProtocol()).thenReturn("smtp");
        Mockito.when(emailConfiguration.getStarttls()).thenReturn(false);
        Mockito.when(emailConfiguration.getAuth()).thenReturn(false);
        Mockito.when(emailConfiguration.getUsername()).thenReturn("");
        Mockito.when(emailConfiguration.getPassword()).thenReturn("");

        List<EmpruntDto> empruntsEnCours = new ArrayList<>();
        EmpruntDto empruntEnCours = new EmpruntDto().builder()
                .enRetard(false)
                .emprunteur(new UtilisateurDto().builder().id(1).email("user1@oc.com").build())
                .dateRetourPrevu(Date.valueOf(LocalDate.now()))
                .livre(new LivreDto().builder()
                        .ouvrage(new OuvrageDto().builder().titre("ouvrage test").build())
                        .bibliotheque(new BibliothequeDto().builder().nom("bibli test").build()).build())
                .build();
        EmpruntDto empruntEnRetard = new EmpruntDto().builder()
                .enRetard(true)
                .emprunteur(new UtilisateurDto().builder().id(2).email("user2@oc.com").build())
                .dateRetourPrevu(Date.valueOf(LocalDate.now()))
                .livre(new LivreDto().builder()
                        .ouvrage(new OuvrageDto().builder().titre("ouvrage deux test").build())
                        .bibliotheque(new BibliothequeDto().builder().nom("bibli deux test").build()).build())
                .build();
        EmpruntDto empruntEnRetard2 = new EmpruntDto().builder()
                .enRetard(true)
                .emprunteur(new UtilisateurDto().builder().id(1).email("user1@oc.com").build())
                .dateRetourPrevu(Date.valueOf(LocalDate.now()))
                .livre(new LivreDto().builder()
                        .ouvrage(new OuvrageDto().builder().titre("ouvrage bis test").build())
                        .bibliotheque(new BibliothequeDto().builder().nom("bibli test").build()).build())
                .build();
        empruntsEnCours.add(empruntEnCours);
        empruntsEnCours.add(empruntEnRetard);
        empruntsEnCours.add(empruntEnRetard2);
        Mockito.when(empruntService.getAllEmpruntsEnCoursByUtilisateur(Mockito.anyInt())).thenReturn(empruntsEnCours);

        ReminderJobProcessor processor = new ReminderJobProcessor(empruntService, emailConfiguration);

        MimeMessage message = processor.process(empruntEnRetard);

        Assert.assertEquals("eBibli : retard", message.getSubject());
        Assert.assertEquals("user2@oc.com", Arrays.stream(message.getRecipients(Message.RecipientType.TO)).findFirst().get().toString());
        Assert.assertEquals("eBibli@oc.com", Arrays.stream(message.getFrom()).findFirst().get().toString());
        Assert.assertTrue(message.getContent().toString().contains("ouvrage test"));
        Assert.assertTrue(message.getContent().toString().contains("ouvrage bis test"));
        Assert.assertTrue(message.getContent().toString().contains("bibli test"));

        message = processor.process(empruntEnCours);

        Assert.assertEquals("eBibli : retard", message.getSubject());
        Assert.assertEquals("user1@oc.com", Arrays.stream(message.getRecipients(Message.RecipientType.TO)).findFirst().get().toString());
        Assert.assertEquals("eBibli@oc.com", Arrays.stream(message.getFrom()).findFirst().get().toString());
        Assert.assertTrue(message.getContent().toString().contains("ouvrage deux test"));
        Assert.assertTrue(message.getContent().toString().contains("bibli deux test"));

        message = processor.process(empruntEnRetard2);

        Assert.assertNull(message);
    }
}