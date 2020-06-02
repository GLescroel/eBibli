package com.ebibli.infrastructure.rest.reservation;

import com.ebibli.dto.LivreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Accès à l'API du microservice utilisateur avec Feign
 */
@FeignClient(name = "microbiblio-service-reservation",
        url = "${clients.com-ebibli-reservation.endpoint}")
public interface ReservationClientApi {

    @PostMapping(value = "/reservation/ notification")
    void notificationLivreEnRetour(@RequestBody LivreDto livre);
}
