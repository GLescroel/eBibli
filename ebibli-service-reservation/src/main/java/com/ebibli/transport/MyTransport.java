package com.ebibli.transport;

import org.springframework.context.annotation.Configuration;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Configuration
public class MyTransport{

    public void send(MimeMessage msg) throws MessagingException {
        javax.mail.Transport.send(msg);
    }
}