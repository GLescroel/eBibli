package com.ebibli.infrastructure.rest.livre;

import com.ebibli.dto.LivreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Accès à l'API du microservice livre avec Feign
 */
@FeignClient(name = "microbiblio-service-livre",
        url = "${clients.com-ebibli-livre.endpoint}")
public interface LivreClientApi {

    @GetMapping(value = "/livres/{bibliothequeId}")
    List<LivreDto> getAllLivresByBibliotheque(@PathVariable("bibliothequeId") Integer bibliothequeId);

    @GetMapping(value = "/livresDispo/ouvrage/{ouvrageId}")
    List<LivreDto> getDispoByOuvrage(@PathVariable("ouvrageId") Integer ouvrageId);
}
