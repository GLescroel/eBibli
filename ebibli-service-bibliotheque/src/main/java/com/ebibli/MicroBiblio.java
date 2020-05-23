package com.ebibli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroBiblio {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroBiblio.class);

    public static void main(String[] args) {
        SpringApplication.run(MicroBiblio.class, args);
        LOGGER.info("L'application a démarré...");
    }
}
