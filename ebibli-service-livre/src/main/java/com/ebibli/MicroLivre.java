package com.ebibli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroLivre {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroLivre.class);

    public static void main(String[] args) {
        SpringApplication.run(MicroLivre.class, args);
        LOGGER.info("Le service Livre a démarré...");
    }
}
