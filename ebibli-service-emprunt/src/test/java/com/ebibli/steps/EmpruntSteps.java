package com.ebibli.steps;

import com.ebibli.config.StepDefs;
import com.ebibli.domain.LivreClient;
import com.ebibli.domain.ReservationClient;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.EmpruntDto;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.OuvrageDto;
import com.ebibli.dto.UtilisateurDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

public class EmpruntSteps extends StepDefs {

    private static ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    @Autowired
    UtilisateurClient utilisateurClient;
    @MockBean
    @Autowired
    LivreClient livreClient;
    @MockBean
    @Autowired
    ReservationClient reservationClient;
    private RestTemplate restTemplate = new RestTemplate();
    private String apiUrl;
    @LocalServerPort
    private int port;
    private Integer empruntId;
    private Integer emprunteurId;
    private Integer livreId;
    private EmpruntDto emprunt;
    private EmpruntDto[] emprunts;

    @Before
    public void setup() {
    }

    @Given("^l'abonné d'\"([^\"]*)\" existe et le livre d'\"([^\"]*)\" est disponible$")
    public void lAbonnéDExisteEtLeLivreDEstDisponible(String arg0, String arg1) {
        Mockito.when(utilisateurClient.getUtilisateurById(any())).thenReturn(new UtilisateurDto().builder().id(4).build());
        Mockito.when(livreClient.getLivreById(any())).thenReturn(new LivreDto().builder().id(23).ouvrage(new OuvrageDto().builder().id(16).build()).reserve(false).build());
    }

    @When("^l'abonné d'\"([^\"]*)\" emprunte le livre d'\"([^\"]*)\"$")
    public void lAbonnéDEmprunteLeLivreD(String utilisateurId, String livreId) {
        doNothing().when(reservationClient).cancelReservation(any(), any());
        this.livreId = Integer.valueOf(livreId);
        emprunteurId = Integer.valueOf(utilisateurId);
        apiUrl = "http://localhost:" + port + "/emprunt/" + livreId + "/" + utilisateurId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<EmpruntDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, EmpruntDto.class);
        emprunt = response.getBody();
    }

    @And("^on recherche les emprunts en cours de l'abonné$")
    public void onRechercheLesEmpruntsEnCoursDeLAbonne() {
        apiUrl = "http://localhost:" + port + "/emprunts/encours/abonne/" + emprunteurId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<EmpruntDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, EmpruntDto[].class);
        emprunts = response.getBody();
    }

    @And("^on recherche l'emprunt en cours pour ce livre$")
    public void onRechercheLEmpruntEnCoursPourCeLivre() {
        apiUrl = "http://localhost:" + port + "/emprunt/encours/livre/" + livreId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<EmpruntDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, EmpruntDto.class);
        emprunt = response.getBody();
    }

    @Then("^l'emprunt fait bien parti des emprunts en cours de l'abonné$")
    public void lEmpruntFaitBienPartiDesEmpruntsEnCoursDeLAbonné() {
        boolean empruntFound = false;
        for (EmpruntDto emprunt : emprunts) {
            if (emprunt.getLivre().getId().equals(livreId)) {
                empruntFound = true;
                this.emprunt = emprunt;
            }
        }
        Assert.assertTrue(empruntFound);
        Assert.assertTrue(emprunt.getEncours());
        Assert.assertEquals(Date.valueOf(LocalDate.now()).toString(), emprunt.getDateEmprunt().toString());
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(4)).toString(), emprunt.getDateRetourPrevu().toString());
    }

    @And("^l'emprunt est bien en cours pour ce livre$")
    public void lEmpruntEstBienEnCoursPourCeLivre() {
        Assert.assertEquals(emprunteurId, emprunt.getEmprunteur().getId());
        Assert.assertEquals(livreId, emprunt.getLivre().getId());
        Assert.assertTrue(emprunt.getEncours());
        Assert.assertEquals(Date.valueOf(LocalDate.now()).toString(), emprunt.getDateEmprunt().toString());
    }

    @When("^l'abonné d'\"([^\"]*)\" prolonge l'emprunt du livre d'\"([^\"]*)\"$")
    public void lAbonnéDProlongeLEmpruntDuLivreD(String utilisateurId, String livreId) {
        apiUrl = "http://localhost:" + port + "/prolongation/" + emprunt.getId();
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<EmpruntDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, EmpruntDto.class);
        emprunt = response.getBody();
    }

    @Then("^l'emprunt a été prolongé$")
    public void lEmpruntAÉtéProlongé() {
        Assert.assertEquals(Date.valueOf(LocalDate.now().plusWeeks(4).plusWeeks(4)).toString(), emprunt.getDateRetourPrevu().toString());
        Assert.assertTrue(emprunt.getProlonge());
    }

    @When("^l'abonné d'\"([^\"]*)\" retourne le livre d'\"([^\"]*)\"$")
    public void lAbonnéDRetourneLeLivreD(String utilisateurId, String livreId) {
        apiUrl = "http://localhost:" + port + "/retour/" + emprunt.getLivre().getBibliotheque().getId() + "/" + emprunt.getLivre().getId();
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<EmpruntDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, EmpruntDto.class);
        emprunt = response.getBody();
    }

    @Then("^l'emprunt est terminé$")
    public void lEmpruntDeLAbonnéEstTerminé() {
        Assert.assertFalse(emprunt.getEncours());
        Assert.assertEquals(Date.valueOf(LocalDate.now()).toString(), emprunt.getDateRetour().toString());
    }

    @And("^l'emprunt de l'abonné \"([^\"]*)\" est dans sa liste d'emprunts terminés$")
    public void lEmpruntDeLAbonnéEstDansSaListeDEmpruntsTerminés(String utilisateurId) throws Throwable {
        apiUrl = "http://localhost:" + port + "/emprunts/termine/abonne/" + utilisateurId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<EmpruntDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, EmpruntDto[].class);
        emprunts = response.getBody();

        boolean empruntFound = false;
        for (EmpruntDto empruntTermine : emprunts) {
            if (empruntTermine.getId().equals(emprunt.getId())) {
                empruntFound = true;
            }
        }
        Assert.assertTrue(empruntFound);
    }

    @And("^l'emprunt de l'abonné \"([^\"]*)\" est dans sa liste complète d'emprunts$")
    public void lEmpruntDeLAbonnéEstDansSaListeComplèteDEmprunts(String utilisateurId) {
        apiUrl = "http://localhost:" + port + "/emprunts/abonne/" + utilisateurId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<EmpruntDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, EmpruntDto[].class);
        emprunts = response.getBody();

        boolean empruntFound = false;
        for (EmpruntDto empruntTermine : emprunts) {
            if (empruntTermine.getId().equals(emprunt.getId())) {
                empruntFound = true;
            }
        }
        Assert.assertTrue(empruntFound);
    }

    @When("^l'abonné d'\"([^\"]*)\" est supprimé de l'emprunt$")
    public void lAbonnéDEstSuppriméDeLEmprunt(String utilisateurId) {
        apiUrl = "http://localhost:" + port + "/emprunt/suppression_emprunteur/" + emprunt.getId();
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<EmpruntDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, EmpruntDto.class);
        emprunt = response.getBody();
    }

    @Then("^l'abonné d'\"([^\"]*)\" n'est plus référencé dans l'emprunt$")
    public void lAbonnéDNEstPlusRéférencéDansLEmprunt(String utilisateurId) {
        Assert.assertNull(emprunt.getEmprunteur());
    }
}
