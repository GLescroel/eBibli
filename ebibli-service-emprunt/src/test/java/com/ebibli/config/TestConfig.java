package com.ebibli.config;


import com.ebibli.service.EmpruntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
public class TestConfig {

    private final EntityManager entityManager;
    private final EmpruntService empruntService;

    @Autowired
    public TestConfig(EntityManager entityManager,
                      EmpruntService empruntService) {
        this.entityManager = entityManager;
        this.empruntService = empruntService;
    }
}
