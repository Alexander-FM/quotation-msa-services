package com.codesoft.employees.role.mapper;

import java.util.List;
import java.util.Set;

import com.codesoft.employees.role.dto.request.RoleRequestDto;
import com.codesoft.employees.role.dto.response.RoleResponseDto;
import com.codesoft.employees.role.model.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleFieldsMapper {

  RoleEntity toEntity(final RoleRequestDto source);

  RoleResponseDto toDto(final RoleEntity destination);

  List<RoleResponseDto> toDtoList(final List<RoleEntity> entityList);

  Set<RoleRequestDto> toRequestDtoSet(final Set<RoleEntity> entities);
}
