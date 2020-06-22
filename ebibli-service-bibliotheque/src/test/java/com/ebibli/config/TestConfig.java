package com.ebibli.config;


import com.ebibli.service.BibliothequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
public class TestConfig {

    private final EntityManager entityManager;
    private final BibliothequeService bibliothequeService;

    @Autowired
    public TestConfig(EntityManager entityManager,
                      BibliothequeService bibliothequeService) {
        this.entityManager = entityManager;
        this.bibliothequeService = bibliothequeService;
    }
}
