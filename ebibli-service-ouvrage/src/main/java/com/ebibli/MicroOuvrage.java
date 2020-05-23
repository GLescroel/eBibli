package com.ebibli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroOuvrage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroOuvrage.class);

    public static void main(String[] args) {
        SpringApplication.run(MicroOuvrage.class, args);
        LOGGER.info("L'application a démarré...");
    }
}
