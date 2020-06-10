package com.ebibli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroEmprunt {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroEmprunt.class);

    public static void main(String[] args) {
        SpringApplication.run(MicroEmprunt.class, args);
        LOGGER.info("Le service Emprunt a démarré...");
    }
}
