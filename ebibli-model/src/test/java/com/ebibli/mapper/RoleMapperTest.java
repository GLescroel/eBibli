package com.ebibli.mapper;

import com.ebibli.domain.Role;
import com.ebibli.dto.RoleDto;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class RoleMapperTest {

    @Test
    public void testShouldMapRoleToDto() {
        Role role = new Role();
        role.setId(1);
        role.setRole("role test");

        RoleDto roleDto = Mappers.getMapper(RoleMapper.class).map(role);

        Assert.assertEquals(java.util.Optional.of(1).get(), roleDto.getId());
        Assert.assertEquals("role test", roleDto.getRole());
    }

    @Test
    public void testShouldMapDtoToRole() {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1);
        roleDto.setRole("role test");

        Role role = Mappers.getMapper(RoleMapper.class).map(roleDto);

        Assert.assertEquals(java.util.Optional.of(1).get(), role.getId());
        Assert.assertEquals("role test", role.getRole());
    }

    @Test
    public void testShouldMapNull() {
        Assert.assertNull(Mappers.getMapper(RoleMapper.class).map((Role) null));
        Assert.assertNull(Mappers.getMapper(RoleMapper.class).map((RoleDto) null));
    }
}