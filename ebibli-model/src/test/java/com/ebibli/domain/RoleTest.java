package com.ebibli.domain;

import org.junit.Assert;
import org.junit.Test;

public class RoleTest {

    @Test
    public void testGettersAndSetters() {
        Role role = new Role();
        role.setId(1);
        role.setRole("role test");

        Assert.assertEquals(java.util.Optional.of(1).get(), role.getId());
        Assert.assertEquals("role test", role.getRole());
    }
}