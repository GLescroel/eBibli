package com.ebibli.steps;

import com.ebibli.config.StepDefs;
import com.ebibli.dto.BibliothequeDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class BibliothequeSteps extends StepDefs {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate();
    private String apiUrl;
    @LocalServerPort
    private int port;
    private String email;
    private Integer bibliothequeId;
    private BibliothequeDto bibliotheque;
    private BibliothequeDto[] bibliotheques;

    @Given("^je recherche la bibliotheque d'\"([^\"]*)\"$")
    public void jeRechercheLUtilisateurD(String id) {
        this.bibliothequeId = Integer.valueOf(id);
    }

    @When("^j'interroge le microservice bibliotheque avec son id$")
    public void jInterrogeLeMicroserviceBibliothequeAvecId() {
        apiUrl = "http://localhost:" + port + "/bibliotheque/" + bibliothequeId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<BibliothequeDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, BibliothequeDto.class);
        bibliotheque = response.getBody();
    }

    @Then("Les infos (.*) de la bibliotheque du fichier rechercheBibliothequeResultat.json sont retournées")
    public void laListeRechercheUtilisateurResultatDuFichierRechercheBibliothequeResultatJsonSAffiche(String resultats) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        String nomFichier = "rechercheBibliothequeResultat.json";
        String path = "bdd/" + nomFichier;
        URL url = classLoader.getResource(path);
        File file;
        byte[] jsonData = null;
        if (url != null) {
            file = new File(url.getFile());
            jsonData = Files.readAllBytes(file.toPath());
        }
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode jsonNode = rootNode.get(resultats);
        String cras = jsonNode.toString();
        jsonData = cras.getBytes("utf-8");
        BibliothequeDto expectedBibliotheque = objectMapper.readValue(jsonData, new TypeReference<BibliothequeDto>() {
        });

        Assert.assertEquals(expectedBibliotheque.getId(), bibliotheque.getId());
        Assert.assertEquals(expectedBibliotheque.getNom(), bibliotheque.getNom());

    }

    @Given("^je recherche les bibliotheques$")
    public void jeRechercheLesBibliotheques() {
    }

    @When("^j'interroge le microservice bibliotheque$")
    public void jInterrogeLeMicroserviceBibliotheque() {
        apiUrl = "http://localhost:" + port + "/bibliotheques";
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<BibliothequeDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, BibliothequeDto[].class);
        bibliotheques = response.getBody();
    }

    @Then("Les infos (.*) de toutes les bibliotheques du fichier rechercheBibliothequeResultat.json sont retournées")
    public void lesInfosRechercheBibliothequeResultatDeToutesLesBibliothequesDuFichierRechercheBibliothequeResultatJsonSontRetournées(String resultats) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String nomFichier = "rechercheBibliothequeResultat.json";
        String path = "bdd/" + nomFichier;
        URL url = classLoader.getResource(path);
        File file;
        byte[] jsonData = null;
        if (url != null) {
            file = new File(url.getFile());
            jsonData = Files.readAllBytes(file.toPath());
        }
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode jsonNode = rootNode.get(resultats);
        String cras = jsonNode.toString();
        jsonData = cras.getBytes("utf-8");
        List<BibliothequeDto> expectedBibliotheques = objectMapper.readValue(jsonData, new TypeReference<List<BibliothequeDto>>() {
        });

        Assert.assertEquals(expectedBibliotheques.get(0).getId(), bibliotheques[0].getId());
        Assert.assertEquals(expectedBibliotheques.get(0).getNom(), bibliotheques[0].getNom());
        Assert.assertEquals(expectedBibliotheques.get(1).getId(), bibliotheques[1].getId());
        Assert.assertEquals(expectedBibliotheques.get(1).getNom(), bibliotheques[1].getNom());
        Assert.assertEquals(expectedBibliotheques.get(2).getId(), bibliotheques[2].getId());
        Assert.assertEquals(expectedBibliotheques.get(2).getNom(), bibliotheques[2].getNom());
    }
}
