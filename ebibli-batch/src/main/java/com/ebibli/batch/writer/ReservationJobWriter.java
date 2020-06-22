package com.ebibli.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.mail.javamail.MimeMessageItemWriter;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.List;

public class ReservationJobWriter extends MimeMessageItemWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationJobWriter.class);

    private final JavaMailSender sender;

    public ReservationJobWriter(JavaMailSender sender) {
        this.sender = sender;
    }

    /**
     * Envoi des messages aux utilisateurs dont les réservations sont annulées
     * @param list
     */
    @Override
    public void write(List<? extends MimeMessage> list) {
        LOGGER.info("dans ReminderJobWriter");

        for (MimeMessage message : list) {
            sender.send(message);
        }
    }
}