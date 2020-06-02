package com.ebibli.infrastructure.rest.livre;

import com.ebibli.dto.LivreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Accès à l'API du microservice livre avec Feign
 */
@FeignClient(name = "microbiblio-service-livre",
        url = "${clients.com-ebibli-livre.endpoint}")
public interface LivreClientApi {

    @GetMapping(value = "/livres/ouvrage/{ouvrageId}")
    List<LivreDto> getLivresByOuvrage(@PathVariable("ouvrageId") Integer ouvrageId);

    @PostMapping(value = "/livre/{livreId}/reserve")
    void setLivreReserve(@PathVariable ("livreId") Integer livreId);
}
