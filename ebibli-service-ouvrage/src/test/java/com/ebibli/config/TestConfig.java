package com.ebibli.config;


import com.ebibli.service.OuvrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
public class TestConfig {

    private final EntityManager entityManager;
    private final OuvrageService ouvrageService;

    @Autowired
    public TestConfig(EntityManager entityManager,
                      OuvrageService ouvrageService) {
        this.entityManager = entityManager;
        this.ouvrageService = ouvrageService;
    }
}
