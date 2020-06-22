package com.ebibli.batch.config;

import com.ebibli.emailconfiguration.EmailConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Bean
    EmailConfiguration emailConfiguration() {
        return new EmailConfiguration();
    }
}
