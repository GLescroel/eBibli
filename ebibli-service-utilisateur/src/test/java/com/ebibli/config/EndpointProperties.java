package com.ebibli.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "clients", ignoreUnknownFields = true)
public class EndpointProperties {

    @Value("${clients.com-ebibli-utilisateur.endpoint}")
    private String endpointUtilisateur;

    @Value("${clients.com-ebibli-emprunt.endpoint}")
    private String endpointEmprunt;

    public String getEndpointUtilisateur() { return endpointUtilisateur; }
    public void setEndpointUtilisateur(String endpointUtilisateur) { this.endpointUtilisateur = endpointUtilisateur; }
    public String getEndpointEmprunt() { return endpointEmprunt; }
    public void setEndpointEmprunt(String endpointEmprunt) { this.endpointEmprunt = endpointEmprunt; }
}
