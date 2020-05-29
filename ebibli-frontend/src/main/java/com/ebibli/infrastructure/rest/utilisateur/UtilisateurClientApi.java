package com.ebibli.infrastructure.rest.utilisateur;

import com.ebibli.dto.UtilisateurDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Accès à l'API du microservice utilisateur avec Feign
 */
@FeignClient(name = "microbiblio-service-utilisateur",
        url = "${clients.com-ebibli-utilisateur.endpoint}")
public interface UtilisateurClientApi {

    @GetMapping(value = "/utilisateur/email/{email}")
    UtilisateurDto getUtilisateurByEmail(@PathVariable("email") String email);

    @PostMapping(value = "/utilisateur/creation")
    UtilisateurDto save(@RequestBody UtilisateurDto utilisateur);

    @PostMapping(value = "/utilisateur/suppression")
    Boolean delete(@RequestBody UtilisateurDto utilisateur);

    @GetMapping(value = "/utilisateur/id/{utilisateurId}")
    UtilisateurDto getUtilisateurById(@PathVariable ("utilisateurId") Integer utilisateurId);
}
