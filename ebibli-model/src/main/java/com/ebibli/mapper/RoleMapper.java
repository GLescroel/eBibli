package com.ebibli.mapper;

import com.ebibli.dto.RoleDto;
import com.ebibli.domain.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {

    RoleDto map(Role role);
    Role map(RoleDto roleDto);

}
