package com.ebibli.dto;

import org.junit.Assert;
import org.junit.Test;

public class RoleDtoTest {

    @Test
    public void testGettersAndSetters() {
        RoleDto role = new RoleDto();
        role.setId(1);
        role.setRole("role test");

        Assert.assertEquals(java.util.Optional.of(1).get(), role.getId());
        Assert.assertEquals("role test", role.getRole());
    }

    @Test
    public void testBuilder() {
        RoleDto role = new RoleDto()
                .builder()
                .id(1)
                .role("role test")
                .build();

        Assert.assertEquals(java.util.Optional.of(1).get(), role.getId());
        Assert.assertEquals("role test", role.getRole());
    }
}