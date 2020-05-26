package com.ebibli.steps;

import com.ebibli.config.EndpointProperties;
import com.ebibli.config.StepDefs;
import com.ebibli.dto.UtilisateurDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class UtilisateurSteps extends StepDefs {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurSteps.class);

    @Autowired
    private EndpointProperties endpointProperties;

    private static ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate();
    private String apiUrl;
    private String port = "9003";
    private String email;
    private UtilisateurDto utilisateur;

    @Given("^je recherche l'utilisateur par son \"([^\"]*)\"$")
    public void jeRechercheUtilisateurParSonEmail(String email) {
        this.email = email;
    }

    @When("^j'interroge le microservice utilisateur$")
    public void jInterrogeLeMicroserviceUtilisateur() {
        apiUrl = endpointProperties.getEndpointUtilisateur() + "/utilisateur/email/" + email;
        LOGGER.info("apiUrl = {}", apiUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "MyClient/1.0.0");
        headers.add("accept", "application/vnd.travis-ci.2.1+json");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<UtilisateurDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, UtilisateurDto.class);
        utilisateur = response.getBody();
    }

    @Then("Les infos (.*) de l'utilisateur du fichier rechercheUtilisateurResultat.json sont retournées")
    public void laListeRechercheUtilisateurResultatDesSitesDuFichierRechercheSiteResultatJsonSAffiche(String resultats) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        String nomFichier = "rechercheUtilisateurResultat.json";
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
        UtilisateurDto expectedUtilisateur = objectMapper.readValue(jsonData, new TypeReference<UtilisateurDto>() {
        });

        Assert.assertEquals(expectedUtilisateur.getId(), utilisateur.getId());
        Assert.assertEquals(expectedUtilisateur.getNom(), utilisateur.getNom());
        Assert.assertEquals(expectedUtilisateur.getPrenom(), utilisateur.getPrenom());
        Assert.assertEquals(expectedUtilisateur.getEmail(), utilisateur.getEmail());
        Assert.assertEquals(expectedUtilisateur.getPassword(), utilisateur.getPassword());
        Assert.assertEquals(expectedUtilisateur.getRole().getRole(), utilisateur.getRole().getRole());

    }
}
