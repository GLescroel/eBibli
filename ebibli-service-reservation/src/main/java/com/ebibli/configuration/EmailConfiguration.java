package com.ebibli.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "email", ignoreUnknownFields = false)
public class EmailConfiguration {

    @Value("${email.port}")
    private int port;

    @Value("${email.host}")
    private String host;

    @Value("${email.protocol}")
    private String protocol;

    @Value("${email.auth}")
    private Boolean auth;

    @Value("${email.starttls}")
    private Boolean starttls;

    public int getPort() { return port; }

    public void setPort(int port) { this.port = port; }

    public String getHost() { return host; }

    public void setHost(String host) { this.host = host; }

    public String getProtocol() { return protocol; }

    public void setProtocol(String protocol) { this.protocol = protocol; }

    public Boolean getAuth() { return auth; }

    public void setAuth(Boolean auth) { this.auth = auth; }

    public Boolean getStarttls() {
        return starttls;
    }

    public void setStarttls(Boolean starttls) {
        this.starttls = starttls;
    }
}
