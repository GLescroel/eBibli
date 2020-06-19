package com.ebibli.config;


import com.ebibli.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
public class TestConfig {

    private final EntityManager entityManager;
    private final ReservationService reservationService;

    @Autowired
    public TestConfig(EntityManager entityManager,
                      ReservationService reservationService) {
        this.entityManager = entityManager;
        this.reservationService = reservationService;
    }
}
