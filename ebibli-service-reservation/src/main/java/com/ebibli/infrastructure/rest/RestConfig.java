package com.ebibli.infrastructure.rest;


import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.LivreClient;
import com.ebibli.infrastructure.rest.emprunt.EmpruntClientApi;
import com.ebibli.infrastructure.rest.emprunt.RestEmpruntClient;
import com.ebibli.infrastructure.rest.livre.LivreClientApi;
import com.ebibli.infrastructure.rest.livre.RestLivreClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class RestConfig {

    @Bean
    public LivreClient restLivre(LivreClientApi livreClientApi) {
        return new RestLivreClient(livreClientApi);
    }

    @Bean
    public EmpruntClient restEmprunt(EmpruntClientApi empruntClientApi) {
        return new RestEmpruntClient(empruntClientApi);
    }

    @Bean
    public CustomErrorDecoder customErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
