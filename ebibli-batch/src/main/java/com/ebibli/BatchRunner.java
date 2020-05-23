package com.ebibli;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, XADataSourceAutoConfiguration.class})
public class BatchRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchRunner.class);

    public static void main(String[] args) {
        runMyApp();
    }

    private static void runMyApp() {
        LOGGER.info("*********** Démarrage du batch ***********");
        SpringApplication.run(BatchRunner.class);
        LOGGER.info("*********** Batch terminé ***********");
    }
}
