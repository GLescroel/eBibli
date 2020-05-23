package com.ebibli.infrastructure.rest.emprunt;

import com.ebibli.dto.EmpruntDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Accès à l'API du microservice emprunt avec Feign
 */
@FeignClient(name = "microbiblio-service-emprunt",
        url = "${clients.com-ebibli-emprunt.endpoint}")
public interface EmpruntClientApi {

    @GetMapping(value = "emprunts/encours/abonne/{userId}")
    List<EmpruntDto> findEmpruntsEnCoursByUtilisateur(@PathVariable("userId") Integer userId);

    @GetMapping(value = "emprunts/termine/abonne/{userId}")
    List<EmpruntDto> findEmpruntsTermineByUtilisateur(@PathVariable("userId") Integer userId);

    @PostMapping(value = "emprunt/suppression_emprunteur/{empruntId}")
    EmpruntDto suppressionEmprunteur(@PathVariable("empruntId") Integer empruntId);
}
