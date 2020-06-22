package com.ebibli.batch.processor;

import com.ebibli.dto.ReservationDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.emailconfiguration.EmailConfiguration;
import com.ebibli.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ReservationJobProcessor implements ItemProcessor<ReservationDto, MimeMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationJobProcessor.class);

    private final ReservationService reservationService;
    private final EmailConfiguration emailConfiguration;

    public ReservationJobProcessor(ReservationService reservationService, EmailConfiguration emailConfiguration) {
        this.reservationService = reservationService;
        this.emailConfiguration = emailConfiguration;
    }

    /**
     * Identification des utilisateurs à relancer et création du message
     *
     * @param reservationToCancel
     * @return le MimeMessage à envoyer à l'utilisateur ayant des prêts en retard, null sinon
     * @throws Exception
     */
    @Override
    public MimeMessage process(ReservationDto reservationToCancel) throws Exception {

        reservationService.cancelReservation(reservationToCancel.getId());

        UtilisateurDto emprunteur = reservationToCancel.getEmprunteur();
        if (emprunteur != null) {
            String body = writeMessage(emprunteur, reservationToCancel.getOuvrage().getTitre());

            Properties prop = new Properties();
            prop.put("mail.transport.protocol", emailConfiguration.getProtocol());
            prop.put("mail.smtp.auth", emailConfiguration.getAuth());
            prop.put("mail.smtp.starttls.enable", emailConfiguration.getStarttls());
            prop.put("mail.smtp.host", emailConfiguration.getHost());
            prop.put("mail.smtp.port", emailConfiguration.getPort());

            Session session = Session.getDefaultInstance(prop, null);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("eBibli@oc.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(emprunteur.getEmail()));
            message.setSubject(String.format("eBibli : annulation de votre réservation pour %s", reservationToCancel.getOuvrage().getTitre()));
            message.setText(body, "UTF-8", "html");

            LOGGER.info("message annulation pour {} pour {}", reservationToCancel.getEmprunteur().getEmail(), reservationToCancel.getOuvrage().getTitre());
            return message;
        }
        return null;
    }

    /**
     * Rédaction du corps du message
     *
     * @param emprunteur
     * @return le corps texte du message
     */
    private String writeMessage(UtilisateurDto emprunteur, String titre) {
        String text = String.format("Bonjour %s %s,%n%n", emprunteur.getPrenom(), emprunteur.getNom());
        text += String.format("La date de retrait de votre réservation de l'ouvrage %s est dépassé. :%n", titre);
        text += "Votre réservation est donc annulée. :%n";
        return text;
    }
}