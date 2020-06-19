package com.ebibli.batch.processor;

import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.emailconfiguration.EmailConfiguration;
import com.ebibli.service.ReservationService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

public class ReservationJobProcessorTest {

    private ReservationService reservationService = Mockito.mock(ReservationService.class);
    private EmailConfiguration emailConfiguration = Mockito.mock(EmailConfiguration.class);

    @Test
    public void testShouldProcessReservation() throws Exception {
        Mockito.when(emailConfiguration.getHost()).thenReturn("localhost");
        Mockito.when(emailConfiguration.getPort()).thenReturn(25);
        Mockito.when(emailConfiguration.getProtocol()).thenReturn("smtp");
        Mockito.when(emailConfiguration.getStarttls()).thenReturn(false);
        Mockito.when(emailConfiguration.getAuth()).thenReturn(false);
        Mockito.when(emailConfiguration.getUsername()).thenReturn("");
        Mockito.when(emailConfiguration.getPassword()).thenReturn("");

        ReservationJobProcessor processor = new ReservationJobProcessor(reservationService, emailConfiguration);

        ReservationDto reservation = new ReservationDto()
                .builder()
                .emprunteur(new UtilisateurDto().builder().email("user@oc.com").build())
                .ouvrage(new OuvrageDto().builder().id(1).titre("ouvrage de test").build())
                .build();
        MimeMessage message = processor.process(reservation);

        Assert.assertEquals(String.format("eBibli : annulation de votre réservation pour %s", reservation.getOuvrage().getTitre()), message.getSubject());
        Assert.assertEquals("user@oc.com", Arrays.stream(message.getRecipients(Message.RecipientType.TO)).findFirst().get().toString());
        Assert.assertEquals("eBibli@oc.com", Arrays.stream(message.getFrom()).findFirst().get().toString());
        Assert.assertTrue(message.getContent().toString().contains(reservation.getOuvrage().getTitre()));
    }
}