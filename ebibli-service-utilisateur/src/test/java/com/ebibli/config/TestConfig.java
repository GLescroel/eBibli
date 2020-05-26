package com.ebibli.config;


import com.ebibli.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
public class TestConfig {

    private final EntityManager entityManager;
    private final UtilisateurService utilisateurService;

    @Autowired
    public TestConfig(EntityManager entityManager,
                      UtilisateurService utilisateurService) {
        this.entityManager = entityManager;
        this.utilisateurService = utilisateurService;
    }
}
