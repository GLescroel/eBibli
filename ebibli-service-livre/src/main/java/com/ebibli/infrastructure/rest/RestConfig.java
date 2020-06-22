package com.ebibli.infrastructure.rest;


import com.ebibli.domain.UtilisateurClient;
import com.ebibli.infrastructure.rest.utilisateur.RestUtilisateurClient;
import com.ebibli.infrastructure.rest.utilisateur.UtilisateurClientApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class RestConfig {

    @Bean
    public UtilisateurClient restUtilisateur(UtilisateurClientApi utilisateurClientApi) {
        return new RestUtilisateurClient(utilisateurClientApi);
    }

    @Bean
    public CustomErrorDecoder customErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
