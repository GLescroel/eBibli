package com.ebibli.configuration;

import com.ebibli.emailconfiguration.EmailConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Configuration {

    @Bean
    EmailConfiguration emailConfiguration() {
        return new EmailConfiguration();
    }
}
