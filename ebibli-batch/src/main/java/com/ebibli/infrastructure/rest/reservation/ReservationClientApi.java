package com.ebibli.infrastructure.rest.reservation;

import com.ebibli.dto.ReservationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Accès à l'API du microservice utilisateur avec Feign
 */
@FeignClient(name = "microbiblio-service-reservation",
        url = "${clients.com-ebibli-reservation.endpoint}")
public interface ReservationClientApi {

    @GetMapping(value = "/reservationsASupprimer")
    List<ReservationDto> getAllReservationToCancel();

    @PostMapping(value = "/reservation/suppression/{reservationId}")
    void cancelReservation(@PathVariable("reservationId") Integer reservationId);
}
