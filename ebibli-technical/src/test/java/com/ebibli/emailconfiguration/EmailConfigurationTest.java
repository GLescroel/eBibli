package com.ebibli.emailconfiguration;

import org.junit.Assert;
import org.junit.Test;

public class EmailConfigurationTest {

    @Test
    public void testEmailConfiguration() {
        EmailConfiguration emailConfiguration = new EmailConfiguration();
        emailConfiguration.setHost("localhost");
        emailConfiguration.setPort(25);
        emailConfiguration.setStarttls(false);
        emailConfiguration.setProtocol("smtp");
        emailConfiguration.setAuth(true);
        emailConfiguration.setPassword("123456");
        emailConfiguration.setUsername("user@oc.com");

        Assert.assertEquals("localhost", emailConfiguration.getHost());
        Assert.assertEquals(25, emailConfiguration.getPort());
        Assert.assertEquals(true, emailConfiguration.getAuth());
        Assert.assertEquals(false, emailConfiguration.getStarttls());
        Assert.assertEquals("user@oc.com", emailConfiguration.getUsername());
        Assert.assertEquals("123456", emailConfiguration.getPassword());
        Assert.assertEquals("smtp", emailConfiguration.getProtocol());
    }
}