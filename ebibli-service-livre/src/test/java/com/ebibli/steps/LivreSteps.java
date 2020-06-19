package com.ebibli.steps;

import com.ebibli.config.StepDefs;
import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.LivreDto;
import com.ebibli.dto.UtilisateurDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public class LivreSteps extends StepDefs {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate();
    private String apiUrl;
    @LocalServerPort
    private int port;
    private LivreDto livre;
    private LivreDto[] livres;
    @MockBean
    @Autowired
    private UtilisateurClient utilisateurClient;

    @When("^je recherche la liste de tous les livres$")
    public void jeRechercheLaListeDeTousLesLivres() {
        apiUrl = "http://localhost:" + port + "/livres";
    }

    @Given("^je recherche la liste de tous les livres disponibles$")
    public void jeRechercheLaListeDeTousLesLivresDisponibles() {
        apiUrl = "http://localhost:" + port + "/livresDispo";
    }

    @Given("^je recherche la liste de tous les livres de la bibliotheque \"([^\"]*)\"$")
    public void jeRechercheLaListeDeTousLesLivresDeLaBibliotheque(String bibliothequeId) throws Throwable {
        apiUrl = "http://localhost:" + port + "/livres/" + bibliothequeId;
    }

    @Given("^je recherche la liste de tous les livres de l'ouvrage \"([^\"]*)\"$")
    public void jeRechercheLaListeDeTousLesLivresDeLOuvrage(String ouvrageId) throws Throwable {
        apiUrl = "http://localhost:" + port + "/livres/ouvrage/" + ouvrageId;
    }

    @Given("^je recherche la liste de tous les livres disponibles de l'ouvrage \"([^\"]*)\"$")
    public void jeRechercheLaListeDeTousLesLivresDisponiblesDeLOuvrage(String ouvrageId) throws Throwable {
        apiUrl = "http://localhost:" + port + "/livresDispo/ouvrage/" + ouvrageId;
    }

    @When("^je demande cette liste au microservice Livre$")
    public void jInterrogeLeMicroserviceLivre() {
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<LivreDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, LivreDto[].class);
        livres = response.getBody();
    }

    @Then("Les infos (.*) de la liste des livres du fichier json sont retournées")
    public void lesInfosRechercheAllLivresResultatDeLaListeDesLivresDuFichierRechercheAllLivresResultatJsonSontRetournées(String resultat) throws IOException {
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
        List<LivreDto> expectedLivres = objectMapper.readValue(jsonData, new TypeReference<List<LivreDto>>() {
        });

        for (int i = 0 ; i < expectedLivres.size() ; i++) {
            Assert.assertEquals(expectedLivres.get(i).getId(), livres[i].getId());
            Assert.assertEquals(expectedLivres.get(i).getOuvrage().getId(), livres[i].getOuvrage().getId());
            Assert.assertEquals(expectedLivres.get(i).getBibliotheque().getId(), livres[i].getBibliotheque().getId());
            Assert.assertEquals(expectedLivres.get(i).getDisponible(), livres[i].getDisponible());
            Assert.assertEquals(expectedLivres.get(i).getReserve(), livres[i].getReserve());
        }
    }

    @Given("^le livre d'id \"([^\"]*)\" est disponible dans la bibliothèque \"([^\"]*)\"$")
    public void leLivreDIdEstDisponibleDansLaBibliothèque(String livreId, String bibliothequeId) {
        apiUrl = "http://localhost:" + port + "/livre/" + livreId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<LivreDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, LivreDto.class);
        livre = response.getBody();

        Assert.assertNotNull(livre);
        Assert.assertEquals(Integer.valueOf(bibliothequeId), livre.getBibliotheque().getId());
        Assert.assertTrue(livre.getDisponible());
        Assert.assertFalse(livre.getReserve());
    }

    @When("^l'abonné d'id \"([^\"]*)\" réserve ce livre$")
    public void lAbonnéDIdRéserveCeLivre(String abonneId)  {
        Mockito.when(utilisateurClient.getUtilisateurById(Integer.valueOf(abonneId))).thenReturn(new UtilisateurDto().builder().id(Integer.valueOf(abonneId)).build());
        apiUrl = "http://localhost:" + port + "/livre/" + livre.getId() + "/reserve/" + abonneId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<LivreDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, LivreDto.class);
        livre = response.getBody();
    }

    @Then("^le livre \"([^\"]*)\" est réservé pour l'abonné d'id \"([^\"]*)\"$")
    public void leLivreEstRéservéPourLAbonnéDId(String livreId, String abonneId) {
        apiUrl = "http://localhost:" + port + "/livre/" + livreId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<LivreDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, LivreDto.class);
        livre = response.getBody();

        Assert.assertNotNull(livre);
        Assert.assertTrue(livre.getDisponible());
        Assert.assertTrue(livre.getReserve());
        Assert.assertEquals(Integer.valueOf(abonneId), livre.getNextEmprunteur().getId());
    }

    @When("^l'abonné d'id \"([^\"]*)\" emprunte le livre \"([^\"]*)\"$")
    public void lAbonnéDIdEmprunteLeLivre(String abonneId, String livreId) {
        apiUrl = "http://localhost:" + port + "/livre/" + livreId + "/emprunt";
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<LivreDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, LivreDto.class);
        livre = response.getBody();
    }

    @Then("^La réservation est supprimée$")
    public void laRéservationEstSupprimée() {
        apiUrl = "http://localhost:" + port + "/livre/" + livre.getId() + "/cancelReservation";
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<LivreDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, LivreDto.class);
        livre = response.getBody();
    }

    @And("^le livre \"([^\"]*)\" est emprunté par l'abonné d'id \"([^\"]*)\"$")
    public void leLivreEstEmpruntéParLAbonnéDId(String livreId, String abonneId) {
        apiUrl = "http://localhost:" + port + "/livre/" + livreId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<LivreDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, LivreDto.class);
        livre = response.getBody();

        Assert.assertNotNull(livre);
        Assert.assertFalse(livre.getDisponible());
    }

    @And("^le livre \"([^\"]*)\" n'est plus réservé$")
    public void leLivreNEstPlusRéservé(String livreId) {
        Assert.assertFalse(livre.getReserve());
        Assert.assertNull(livre.getNextEmprunteur());
    }

    @When("^l'abonné d'id \"([^\"]*)\" retourne le livre \"([^\"]*)\"$")
    public void lAbonnéDIdRetourneLeLivre(String abonneId, String livreId) {
        apiUrl = "http://localhost:" + port + "/livre/" + livreId + "/retour";
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<LivreDto> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, LivreDto.class);
        livre = response.getBody();
    }

    @Then("^Le livre d'id \"([^\"]*)\" est disponible$")
    public void leLivreDIdEstDisponible(String livreId) {
        Assert.assertTrue(livre.getDisponible());
    }
}
