package com.ebibli.config;


import com.ebibli.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
public class TestConfig {

    private final EntityManager entityManager;
    private final LivreService livreService;

    @Autowired
    public TestConfig(EntityManager entityManager,
                      LivreService livreService) {
        this.entityManager = entityManager;
        this.livreService = livreService;
    }
}
