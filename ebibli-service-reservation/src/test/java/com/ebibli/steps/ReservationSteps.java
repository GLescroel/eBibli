package com.ebibli.steps;

import com.ebibli.config.StepDefs;
import com.ebibli.domain.EmpruntClient;
import com.ebibli.domain.LivreClient;
import com.ebibli.dto.BibliothequeDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.ReservationDto;
import com.ebibli.dto.UtilisateurDto;
import com.ebibli.transport.MyTransport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReservationSteps extends StepDefs {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate();
    private String apiUrl;
    @LocalServerPort
    private int port;
    private Integer ouvrageId;
    private ReservationDto reservation;
    private ReservationDto[] reservations;

    @MockBean
    @Autowired
    private MyTransport myTransport;

    @MockBean
    @Autowired
    private LivreClient livreClient;

    @MockBean
    @Autowired
    private EmpruntClient empruntclient;

    @Given("^je recherche toutes les réservations pour l'ouvrage d'\"([^\"]*)\"$")
    public void jeRechercheReservationsOuvrage(String ouvrageId) {
        apiUrl = "http://localhost:" + port + "/reservations/ouvrage/" + ouvrageId;
    }

    @Given("^je recherche toutes les réservations pour l'abonné d'\"([^\"]*)\"$")
    public void jeRechercheToutesLesRéservationsPourLAbonnéD(String abonneId) throws Throwable {
        apiUrl = "http://localhost:" + port + "/reservations/abonne/" + abonneId;
    }

    @Given("^je recherche toutes les réservations à supprimer$")
    public void jeRechercheToutesLesRéservationsÀSupprimer() {
        apiUrl = "http://localhost:" + port + "/reservationsASupprimer";
    }

    @When("^j'interroge le microservice reservation pour récupérer la liste$")
    public void jInterrogeLeMicroserviceReservationPourRecupererLaListe() {
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<ReservationDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, ReservationDto[].class);
        reservations = response.getBody();
    }

    @Then("Les infos (.*) de toutes les réservations du fichier json sont retournées")
    public void lesInfosRechercheOuvragesResultatDeToutesLesReservationsDuFichierJsonSontRetournées(String resultat) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String nomFichier = resultat + ".json";
        String path = "bdd/" + nomFichier;
        URL url = classLoader.getResource(path);
        File file;
        byte[] jsonData = null;
        if (url != null) {
            file = new File(url.getFile());
            jsonData = Files.readAllBytes(file.toPath());
        }
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode jsonNode = rootNode.get(resultat);
        String cras = jsonNode.toString();
        jsonData = cras.getBytes("utf-8");
        List<ReservationDto> expectedReservations = objectMapper.readValue(jsonData, new TypeReference<List<ReservationDto>>() {
        });

        for (int i = 0; i < expectedReservations.size(); i++) {
            Assert.assertEquals(expectedReservations.get(i).getId(), reservations[i].getId());
            Assert.assertEquals(expectedReservations.get(i).getDateReservation(), reservations[i].getDateReservation());
            Assert.assertEquals(expectedReservations.get(i).getDateRetraitMax(), reservations[i].getDateRetraitMax());
            Assert.assertEquals(expectedReservations.get(i).getDateAlerte(), reservations[i].getDateAlerte());
            Assert.assertEquals(expectedReservations.get(i).getOuvrage().getId(), reservations[i].getOuvrage().getId());
            Assert.assertEquals(expectedReservations.get(i).getEmprunteur().getId(), reservations[i].getEmprunteur().getId());
        }
    }

    @When("^Le systeme annule ces réservations$")
    public void leSystemeAnnuleCesRéservations() throws MessagingException {
        initMocks(this);
        List<LivreDto> livresOuvrage8 = new ArrayList<>();
        livresOuvrage8.add(new LivreDto()
                .builder()
                .ouvrage(new OuvrageDto().builder().id(8).build())
                .bibliotheque(new BibliothequeDto().builder().id(3).build())
                .nextEmprunteur(new UtilisateurDto().builder().id(4).build())
                .reserve(true)
                .disponible(true)
                .build());
        Mockito.when(livreClient.getLivresByOuvrage(any())).thenReturn(livresOuvrage8);
        doNothing().when(myTransport).send(any());
        for (ReservationDto reservationToCancel : reservations) {
            apiUrl = "http://localhost:" + port + "/reservation/suppression/" + reservationToCancel.getId();
            HttpEntity<String> entity = new HttpEntity<>(null, null);
            ResponseEntity<ReservationDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, ReservationDto[].class);
            Mockito.verify(myTransport).send(any());
        }
    }

    @Then("^Les réservations sont supprimées$")
    public void lesRéservationsSontSupprimées() {
        apiUrl = "http://localhost:" + port + "/reservationsASupprimer";
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<ReservationDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, ReservationDto[].class);
        reservations = response.getBody();
        Assert.assertEquals(0, reservations.length);
    }

    @Given("^L'abonné d'id \"([^\"]*)\" réserve l'ouvrage d'id \"([^\"]*)\"$")
    public void lAbonnéDIdRéserveLOuvrageDId(String abonneId, String ouvrageId) {
        ReservationDto reservationToCreate = new ReservationDto()
                .builder()
                .emprunteur(new UtilisateurDto().builder().id(Integer.valueOf(abonneId)).build())
                .ouvrage(new OuvrageDto().builder().id(Integer.valueOf(ouvrageId)).build())
                .build();
        reservation = reservationToCreate;
    }

    @When("^le microservice reservation enregistre la demande$")
    public void leMicroserviceReservationEnregistreLaDemande() {
        Mockito.when(empruntclient.findEmpruntsEnCoursByUtilisateur(any())).thenReturn(new ArrayList<>());
        apiUrl = "http://localhost:" + port + "/reservation/creation";
        HttpEntity<ReservationDto> entity = new HttpEntity<>(reservation, null);
        ResponseEntity<ReservationDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, ReservationDto.class);
        reservation = response.getBody();
    }

    @Then("^la réservation de l'ouvrage d'id \"([^\"]*)\" pour l'abonné d'id \"([^\"]*)\" est créée$")
    public void laRéservationDeLOuvrageDIdPourLAbonnéDIdEstCréée(String ouvrageId, String abonneId)  {
        apiUrl = "http://localhost:" + port + "/reservations/ouvrage/" + ouvrageId;
        HttpEntity<ReservationDto> entity = new HttpEntity<>(null, null);
        ResponseEntity<ReservationDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, ReservationDto[].class);
        reservations = response.getBody();

        boolean reservationExiste = false;
        for (ReservationDto reservationOuvrage: reservations) {
            if (reservationOuvrage.getEmprunteur().getId().equals(Integer.valueOf(abonneId))) {
                reservationExiste = true;
                reservation = reservationOuvrage;
            }
        }
        Assert.assertTrue(reservationExiste);
        Assert.assertEquals(Date.valueOf(LocalDate.now()).toString(), reservation.getDateReservation().toString());
    }

    @When("^L'abonné d'id \"([^\"]*)\" annule sa réservation de l'ouvrage d'id \"([^\"]*)\"$")
    public void lAbonnéDIdAnnuleSaRéservationDeLOuvrageDId(String abonneId, String ouvrageId) {
        apiUrl = "http://localhost:" + port + "/reservation/annulation/" + ouvrageId + "/" + abonneId;
        HttpEntity<ReservationDto> entity = new HttpEntity<>(null, null);
        restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Void.class);
    }

    @Then("^la réservation de l'ouvrage d'id \"([^\"]*)\" pour l'abonné d'id \"([^\"]*)\" est supprimé$")
    public void laRéservationDeLOuvrageDIdPourLAbonnéDIdEstSupprimé(String ouvrageId, String abonneId) throws Throwable {
        apiUrl = "http://localhost:" + port + "/reservations/ouvrage/" + ouvrageId;
        HttpEntity<ReservationDto> entity = new HttpEntity<>(null, null);
        ResponseEntity<ReservationDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, ReservationDto[].class);
        reservations = response.getBody();

        boolean reservationExiste = false;
        for (ReservationDto reservationOuvrage: reservations) {
            if (reservationOuvrage.getEmprunteur().getId().equals(Integer.valueOf(abonneId))) {
                reservationExiste = true;
                reservation = reservationOuvrage;
            }
        }
        Assert.assertFalse(reservationExiste);
    }

    @When("^Je recherche la prochaine réservation puor l'ouvrage \"([^\"]*)\" à affecter au livre d'id \"([^\"]*)\"$")
    public void jeRechercheLaProchaineRéservationPuorLOuvrageÀAffecterAuLivreDId(String ouvrageId, String livreId) {
        this.ouvrageId = Integer.valueOf(ouvrageId);
        LivreDto livreDispo = new LivreDto()
                .builder()
                .id(Integer.valueOf(livreId))
                .ouvrage(new OuvrageDto().builder().id(Integer.valueOf(ouvrageId)).build())
                .bibliotheque(new BibliothequeDto().builder().id(3).build())
                .disponible(true)
                .reserve(false)
                .nextEmprunteur(null)
                .build();
        apiUrl = "http://localhost:" + port + "/reservation/notification";
        HttpEntity<LivreDto> entity = new HttpEntity<>(livreDispo, null);
        restTemplate.exchange(apiUrl, HttpMethod.POST, entity, ReservationDto.class);
    }

    @Then("^l'abonné d'id \"([^\"]*)\" a été notifié que sa réservation pour l'ouvrage \"([^\"]*)\"est disponible\"$")
    public void lAbonnéDIdAÉtéNotifiéQueSaRéservationPourLOuvrageEstDisponible(String abonneId, String ouvrageId) {
        apiUrl = "http://localhost:" + port + "/reservations/ouvrage/" + ouvrageId;
        HttpEntity<ReservationDto> entity = new HttpEntity<>(null, null);
        ResponseEntity<ReservationDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, ReservationDto[].class);
        reservations = response.getBody();

        ReservationDto reservationAbonne = new ReservationDto();
        for (ReservationDto reservationOuvrage: reservations) {
            if (reservationOuvrage.getEmprunteur().getId().equals(Integer.valueOf(abonneId))) {
                reservationAbonne = reservationOuvrage;
            }
        }
        Assert.assertTrue(reservationAbonne.getAlerte());
    }
}
