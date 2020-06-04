package com.ebibli.infrastructure.rest;


import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.ReservationClient;
import com.ebibli.infrastructure.rest.emprunt.EmpruntClientApi;
import com.ebibli.infrastructure.rest.emprunt.RestEmpruntClient;
import com.ebibli.infrastructure.rest.reservation.ReservationClientApi;
import com.ebibli.infrastructure.rest.reservation.RestReservationClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class RestConfig {

    @Bean
    public EmpruntClient restBiblios(EmpruntClientApi empruntClientApi) {
        return new RestEmpruntClient(empruntClientApi);
    }

    @Bean
    public ReservationClient restReservation(ReservationClientApi reservationClientApi) {
        return new RestReservationClient(reservationClientApi);
    }

    @Bean
    public CustomErrorDecoder customErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
