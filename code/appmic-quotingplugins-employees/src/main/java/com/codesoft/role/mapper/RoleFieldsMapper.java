package com.codesoft.role.mapper;

import java.util.List;

import com.codesoft.role.dto.request.RoleRequestDto;
import com.codesoft.role.dto.response.RoleResponseDto;
import com.codesoft.role.model.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleFieldsMapper {

  RoleEntity toEntity(final RoleRequestDto source);

  RoleResponseDto toDto(final RoleEntity destination);

  List<RoleResponseDto> toDtoList(final List<RoleEntity> entityList);
}
