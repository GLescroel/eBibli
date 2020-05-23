package com.ebibli.infrastructure.rest.bibliotheque;

import com.ebibli.dto.BibliothequeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Accès à l'API du microservice bibliotheque avec Feign
 */
@FeignClient(name = "microbiblio-service-bibliotheque",
        url = "${clients.com-ebibli-bibliotheque.endpoint}")
public interface BibliothequeClientApi {

    @GetMapping(value = "/bibliotheque/{id}")
    BibliothequeDto getBibliothequeById(@PathVariable("id") Integer id);

    @GetMapping(value = "/bibliotheques")
    List<BibliothequeDto> getAllBibliotheques();
}
