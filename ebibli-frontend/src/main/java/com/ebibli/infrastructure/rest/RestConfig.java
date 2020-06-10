package com.ebibli.infrastructure.rest;


import com.ebibli.domain.BibliothequeClient;
import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.LivreClient;
import com.ebibli.domain.OuvrageClient;
import com.ebibli.domain.ReservationClient;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.infrastructure.rest.bibliotheque.BibliothequeClientApi;
import com.ebibli.infrastructure.rest.bibliotheque.RestBibliothequeClient;
import com.ebibli.infrastructure.rest.emprunt.EmpruntClientApi;
import com.ebibli.infrastructure.rest.emprunt.RestEmpruntClient;
import com.ebibli.infrastructure.rest.livre.LivreClientApi;
import com.ebibli.infrastructure.rest.livre.RestLivreClient;
import com.ebibli.infrastructure.rest.ouvrage.OuvrageClientApi;
import com.ebibli.infrastructure.rest.ouvrage.RestOuvrageClient;
import com.ebibli.infrastructure.rest.reservation.ReservationClientApi;
import com.ebibli.infrastructure.rest.reservation.RestReservationClient;
import com.ebibli.infrastructure.rest.utilisateur.RestUtilisateurClient;
import com.ebibli.infrastructure.rest.utilisateur.UtilisateurClientApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class RestConfig {

    @Bean
    public UtilisateurClient restUtilisateur(UtilisateurClientApi utilisateurClientApi) {
        return new RestUtilisateurClient(utilisateurClientApi);
    }

    @Bean
    public OuvrageClient restOuvrage(OuvrageClientApi ouvrageClientApi) {
        return new RestOuvrageClient(ouvrageClientApi);
    }

    @Bean
    public LivreClient restLivre(LivreClientApi livreClientApi) {
        return new RestLivreClient(livreClientApi);
    }


    @Bean
    public EmpruntClient restEmprunt(EmpruntClientApi empruntClientApi) {
        return new RestEmpruntClient(empruntClientApi);
    }

    @Bean
    public BibliothequeClient restBibliotheque(BibliothequeClientApi bibliothequeClientApi) {
        return new RestBibliothequeClient(bibliothequeClientApi);
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
