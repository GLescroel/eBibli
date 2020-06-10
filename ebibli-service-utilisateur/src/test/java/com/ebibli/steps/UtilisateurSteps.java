package com.ebibli.steps;

import com.ebibli.config.StepDefs;
import com.ebibli.domain.EmpruntClient;
import com.ebibli.dto.RoleDto;
import com.ebibli.dto.UtilisateurDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.DataTable;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class UtilisateurSteps extends StepDefs {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private RestTemplate restTemplate = new RestTemplate();
    private String apiUrl;
    @LocalServerPort
    private int port;
    private String email;
    private Integer utilisateurId;
    private UtilisateurDto utilisateur;
    private UtilisateurDto[] utilisateurs;
    @MockBean
    @Autowired
    private EmpruntClient empruntClient;

    @Given("^je recherche l'utilisateur par son \"([^\"]*)\"$")
    public void jeRechercheUtilisateurParSonEmail(String email) {
        this.email = email;
    }

    @Given("^je recherche l'utilisateur d'\"([^\"]*)\"$")
    public void jeRechercheLUtilisateurD(String id) throws Throwable {
        this.utilisateurId = Integer.valueOf(id);
    }

    @When("^j'interroge le microservice utilisateur avec son email$")
    public void jInterrogeLeMicroserviceUtilisateurAvecEmail() {
        apiUrl = "http://localhost:" + port + "/utilisateur/email/" + email;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<UtilisateurDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, UtilisateurDto.class);
        utilisateur = response.getBody();
    }

    @When("^j'interroge le microservice utilisateur avec son id$")
    public void jInterrogeLeMicroserviceUtilisateurAvecId() {
        apiUrl = "http://localhost:" + port + "/utilisateur/id/" + utilisateurId;
        HttpEntity<String> entity = new HttpEntity<>(null, null);
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

    @Given("^je recherche tous les utilisateurs$")
    public void jeRechercheTousLesUtilisateurs() {

    }

    @When("^j'interroge le microservice utilisateur$")
    public void jInterrogeLeMicroserviceUtilisateur() {
        apiUrl = "http://localhost:" + port + "/utilisateurs";
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<UtilisateurDto[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, UtilisateurDto[].class);
        utilisateurs = response.getBody();
    }

    @Then("Les infos (.*) de tous les utilisateurs du fichier rechercheUtilisateurResultat.json sont retournées")
    public void lesInfosRechercheUtilisateurResultatDeTousLesUtilisateursDuFichierRechercheUtilisateurResultatJsonSontRetournées(String resultat) throws IOException {
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
        JsonNode jsonNode = rootNode.get(resultat);
        String cras = jsonNode.toString();
        jsonData = cras.getBytes("utf-8");
        List<UtilisateurDto> expectedUtilisateur = objectMapper.readValue(jsonData, new TypeReference<List<UtilisateurDto>>() {
        });

        Assert.assertEquals(expectedUtilisateur.get(0).getId(), utilisateurs[0].getId());
        Assert.assertEquals(expectedUtilisateur.get(0).getNom(), utilisateurs[0].getNom());
        Assert.assertEquals(expectedUtilisateur.get(0).getPrenom(), utilisateurs[0].getPrenom());
        Assert.assertEquals(expectedUtilisateur.get(0).getEmail(), utilisateurs[0].getEmail());
        Assert.assertEquals(expectedUtilisateur.get(0).getPassword(), utilisateurs[0].getPassword());
        Assert.assertEquals(expectedUtilisateur.get(0).getRole().getRole(), utilisateurs[0].getRole().getRole());

        Assert.assertEquals(expectedUtilisateur.get(1).getId(), utilisateurs[1].getId());
        Assert.assertEquals(expectedUtilisateur.get(1).getNom(), utilisateurs[1].getNom());
        Assert.assertEquals(expectedUtilisateur.get(1).getPrenom(), utilisateurs[1].getPrenom());
        Assert.assertEquals(expectedUtilisateur.get(1).getEmail(), utilisateurs[1].getEmail());
        Assert.assertEquals(expectedUtilisateur.get(1).getPassword(), utilisateurs[1].getPassword());
        Assert.assertEquals(expectedUtilisateur.get(1).getRole().getRole(), utilisateurs[1].getRole().getRole());

        Assert.assertEquals(expectedUtilisateur.get(2).getId(), utilisateurs[2].getId());
        Assert.assertEquals(expectedUtilisateur.get(2).getNom(), utilisateurs[2].getNom());
        Assert.assertEquals(expectedUtilisateur.get(2).getPrenom(), utilisateurs[2].getPrenom());
        Assert.assertEquals(expectedUtilisateur.get(2).getEmail(), utilisateurs[2].getEmail());
        Assert.assertEquals(expectedUtilisateur.get(2).getPassword(), utilisateurs[2].getPassword());
        Assert.assertEquals(expectedUtilisateur.get(2).getRole().getRole(), utilisateurs[2].getRole().getRole());

        Assert.assertEquals(expectedUtilisateur.get(3).getId(), utilisateurs[3].getId());
        Assert.assertEquals(expectedUtilisateur.get(3).getNom(), utilisateurs[3].getNom());
        Assert.assertEquals(expectedUtilisateur.get(3).getPrenom(), utilisateurs[3].getPrenom());
        Assert.assertEquals(expectedUtilisateur.get(3).getEmail(), utilisateurs[3].getEmail());
        Assert.assertEquals(expectedUtilisateur.get(3).getPassword(), utilisateurs[3].getPassword());
        Assert.assertEquals(expectedUtilisateur.get(3).getRole().getRole(), utilisateurs[3].getRole().getRole());

        Assert.assertEquals(expectedUtilisateur.get(4).getId(), utilisateurs[4].getId());
        Assert.assertEquals(expectedUtilisateur.get(4).getNom(), utilisateurs[4].getNom());
        Assert.assertEquals(expectedUtilisateur.get(4).getPrenom(), utilisateurs[4].getPrenom());
        Assert.assertEquals(expectedUtilisateur.get(4).getEmail(), utilisateurs[4].getEmail());
        Assert.assertEquals(expectedUtilisateur.get(4).getPassword(), utilisateurs[4].getPassword());
        Assert.assertEquals(expectedUtilisateur.get(4).getRole().getRole(), utilisateurs[4].getRole().getRole());

        Assert.assertEquals(expectedUtilisateur.get(5).getId(), utilisateurs[5].getId());
        Assert.assertEquals(expectedUtilisateur.get(5).getNom(), utilisateurs[5].getNom());
        Assert.assertEquals(expectedUtilisateur.get(5).getPrenom(), utilisateurs[5].getPrenom());
        Assert.assertEquals(expectedUtilisateur.get(5).getEmail(), utilisateurs[5].getEmail());
        Assert.assertEquals(expectedUtilisateur.get(5).getPassword(), utilisateurs[5].getPassword());
        Assert.assertEquals(expectedUtilisateur.get(5).getRole().getRole(), utilisateurs[5].getRole().getRole());

        Assert.assertEquals(expectedUtilisateur.get(6).getId(), utilisateurs[6].getId());
        Assert.assertEquals(expectedUtilisateur.get(6).getNom(), utilisateurs[6].getNom());
        Assert.assertEquals(expectedUtilisateur.get(6).getPrenom(), utilisateurs[6].getPrenom());
        Assert.assertEquals(expectedUtilisateur.get(6).getEmail(), utilisateurs[6].getEmail());
        Assert.assertEquals(expectedUtilisateur.get(6).getPassword(), utilisateurs[6].getPassword());
        Assert.assertEquals(expectedUtilisateur.get(6).getRole().getRole(), utilisateurs[6].getRole().getRole());

        Assert.assertEquals(expectedUtilisateur.get(7).getId(), utilisateurs[7].getId());
        Assert.assertEquals(expectedUtilisateur.get(7).getNom(), utilisateurs[7].getNom());
        Assert.assertEquals(expectedUtilisateur.get(7).getPrenom(), utilisateurs[7].getPrenom());
        Assert.assertEquals(expectedUtilisateur.get(7).getEmail(), utilisateurs[7].getEmail());
        Assert.assertEquals(expectedUtilisateur.get(7).getPassword(), utilisateurs[7].getPassword());
        Assert.assertEquals(expectedUtilisateur.get(7).getRole().getRole(), utilisateurs[7].getRole().getRole());
    }

    @Given("^je crée l'utilisateur suivant$")
    public void jeCréeLUtilisateurSuivant(DataTable dataTable) {
        List<List<String>> data = dataTable.raw();
        UtilisateurDto utilisateurDto = new UtilisateurDto()
                .builder()
                .nom(data.get(0).get(0))
                .prenom(data.get(0).get(1))
                .email(data.get(0).get(2))
                .password(data.get(0).get(3))
                .role(new RoleDto()
                        .builder()
                        .id(Integer.valueOf(data.get(0).get(4)))
                        .role(data.get(0).get(5))
                        .build())
                .build();

        email = utilisateurDto.getEmail();

        apiUrl = "http://localhost:" + port + "/utilisateur/creation";
        HttpEntity<UtilisateurDto> entity = new HttpEntity<UtilisateurDto>(utilisateurDto, null);
        restTemplate.exchange(apiUrl, HttpMethod.POST, entity, UtilisateurDto.class);
    }

    @And("^Je constate que l'utilisateur existe bien avec les données suivantes$")
    public void jeConstateQueLUtilisateurExisteBien(DataTable dataTable) {
        apiUrl = "http://localhost:" + port + "/utilisateur/email/" + email;
        HttpEntity<UtilisateurDto> entity = new HttpEntity<>(null, null);
        ResponseEntity<UtilisateurDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, UtilisateurDto.class);
        utilisateur = response.getBody();

        List<List<String>> data = dataTable.raw();
        Assert.assertNotEquals(utilisateurId, utilisateur.getId());
        Assert.assertEquals(data.get(0).get(0), utilisateur.getNom());
        Assert.assertEquals(data.get(0).get(1), utilisateur.getPrenom());
        Assert.assertEquals(data.get(0).get(2), utilisateur.getEmail());
        Assert.assertEquals(data.get(0).get(3), utilisateur.getPassword());
        Assert.assertEquals(Integer.valueOf(data.get(0).get(4)), utilisateur.getRole().getId());
        Assert.assertEquals(data.get(0).get(5), utilisateur.getRole().getRole());
    }

    @When("^Je supprime l'utilisateur$")
    public void jeSupprimeLUtilisateur() {
        Mockito.when(empruntClient.findEmpruntsEnCoursByUtilisateur(any())).thenReturn(new ArrayList<>());
        Mockito.when(empruntClient.findEmpruntsTermineByUtilisateur(any())).thenReturn(new ArrayList<>());
        apiUrl = "http://localhost:" + port + "/utilisateur/suppression/";
        HttpEntity<UtilisateurDto> entity = new HttpEntity<>(utilisateur, null);
        ResponseEntity<Boolean> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Boolean.class);
        Assert.assertTrue(response.getBody());
    }

    @Then("^L'utilisateur n'existe plus dans la base$")
    public void lUtilisateurNExistePlusDansLaBase() {
        apiUrl = "http://localhost:" + port + "/utilisateur/email/" + email;
        HttpEntity<UtilisateurDto> entity = new HttpEntity<>(null, null);
        ResponseEntity<UtilisateurDto> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, UtilisateurDto.class);
        Assert.assertNull(response.getBody());
    }
}
