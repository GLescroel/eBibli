package com.ebibli.batch.config;

import org.junit.Assert;
import org.junit.Test;

public class BiblioJobPropertiesTest {

    @Test
    public void testBiblioJobProperties() {
        BiblioJobProperties biblioJobProperties = new BiblioJobProperties();
        biblioJobProperties.setReportPath("test path");
        biblioJobProperties.setSkipLimit(10);
        biblioJobProperties.setChunkSize(15);

        Assert.assertEquals(10, biblioJobProperties.getSkipLimit());
        Assert.assertEquals(15, biblioJobProperties.getChunkSize());
        Assert.assertEquals("test path", biblioJobProperties.getReportPath());
    }
}