package com.ebibli.steps;

import com.ebibli.config.StepDefs;
import com.ebibli.dto.OuvrageDto;
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
import java.util.List;

public class OuvrageSteps extends StepDefs {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate();
    private String apiUrl;
    @LocalServerPort
    private int port;
    private Integer ouvrageId;
    private OuvrageDto ouvrage;
    private OuvrageDto[] ouvrages;

    @Given("^je recherche l'ouvrage d'\"([^\"]*)\"$")
    public void jeRechercheLOuvrageD(String id) {
        this.ouvrageId = Integer.valueOf(id);
    }

    @When("^j'interroge le microservice ouvrage avec son id$")
    public void jInterrogeLeMicroserviceOuvrageAvecId() {
        apiUrl = "http://localhost:" + port + "/ouvrage/id/" + ouvrageId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<OuvrageDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, OuvrageDto.class);
        ouvrage = response.getBody();
    }

    @Then("Les infos (.*) de l'ouvrage du fichier rechercheOuvrage.json sont retournées")
    public void laListeRechercheUtilisateurResultatDuFichierRechercheOuvrageResultatJsonSAffiche(String resultat) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        String nomFichier = "rechercheOuvrage.json";
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
        OuvrageDto expectedOuvrage = objectMapper.readValue(jsonData, new TypeReference<OuvrageDto>() {
        });

        Assert.assertEquals(expectedOuvrage.getId(), ouvrage.getId());
        Assert.assertEquals(expectedOuvrage.getTitre(), ouvrage.getTitre());
        Assert.assertEquals(expectedOuvrage.getResume(), ouvrage.getResume());
        Assert.assertEquals(expectedOuvrage.getImage(), ouvrage.getImage());

    }

    @Given("^je recherche tous les ouvrages$")
    public void jeRechercheLesOuvrages() {
        apiUrl = "http://localhost:" + port + "/ouvrages";
    }

    @When("^j'interroge le microservice ouvrage$")
    public void jInterrogeLeMicroserviceOuvrage() {
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<OuvrageDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, OuvrageDto[].class);
        ouvrages = response.getBody();
    }

    @Then("Les infos (.*) de tous les ouvrages du fichier json sont retournées")
    public void lesInfosRechercheOuvragesResultatDeTousLesOuvragesDuFichierJsonSontRetournées(String resultat) throws IOException {
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
        List<OuvrageDto> expectedOuvrages = objectMapper.readValue(jsonData, new TypeReference<List<OuvrageDto>>() {
        });

        for (int i = 0 ; i < expectedOuvrages.size(); i++) {
            Assert.assertEquals(expectedOuvrages.get(i).getId(), ouvrages[i].getId());
            Assert.assertEquals(expectedOuvrages.get(i).getTitre(), ouvrages[i].getTitre());
            Assert.assertEquals(expectedOuvrages.get(i).getResume(), ouvrages[i].getResume());
            Assert.assertEquals(expectedOuvrages.get(i).getImage(), ouvrages[i].getImage());
        }
    }
}
