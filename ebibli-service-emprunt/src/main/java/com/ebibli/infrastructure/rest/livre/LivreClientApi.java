package com.ebibli.infrastructure.rest.livre;

import com.ebibli.dto.LivreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Accès à l'API du microservice livre avec Feign
 */
@FeignClient(name = "microbiblio-service-livre",
        url = "${clients.com-ebibli-livre.endpoint}")
public interface LivreClientApi {

    @GetMapping(value = "/livre/{id}")
    LivreDto getLivreById(@PathVariable("id") Integer id);

    @PostMapping(value = "/livre/{livreId}/emprunt")
    LivreDto setIndisponible(@PathVariable("livreId") Integer id);

    @PostMapping(value = "/livre/{livreId}/retour")
    LivreDto setDisponible(@PathVariable ("livreId") Integer livreId);
}
