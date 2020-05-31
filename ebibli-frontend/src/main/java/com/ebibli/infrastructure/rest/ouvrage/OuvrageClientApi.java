package com.ebibli.infrastructure.rest.ouvrage;

import com.ebibli.dto.OuvrageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Accès à l'API du microservice ouvrage avec Feign
 */
@FeignClient(name = "microbiblio-service-ouvrage",
        url = "${clients.com-ebibli-ouvrage.endpoint}")
public interface OuvrageClientApi {

    @GetMapping(value = "/ouvrages")
    List<OuvrageDto> getAllOuvrages();

    @GetMapping(value = "/ouvrage/id/{ouvrageId}")
    OuvrageDto getOuvrageById(@PathVariable ("ouvrageId") Integer ouvrageId);
}
