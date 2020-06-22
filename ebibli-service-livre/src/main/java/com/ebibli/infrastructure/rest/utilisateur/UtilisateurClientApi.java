package com.ebibli.infrastructure.rest.utilisateur;

import com.ebibli.dto.UtilisateurDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Accès à l'API du microservice utilisateur avec Feign
 */
@FeignClient(name = "microbiblio-service-utilisateur",
        url = "${clients.com-ebibli-utilisateur.endpoint}")
public interface UtilisateurClientApi {

    @GetMapping(value = "/utilisateur/id/{id}")
    UtilisateurDto getUtilisateurById(@PathVariable("id") Integer id);
}
