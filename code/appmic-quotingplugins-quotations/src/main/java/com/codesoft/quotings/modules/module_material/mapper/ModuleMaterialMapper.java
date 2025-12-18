package com.codesoft.quotings.modules.module_material.mapper;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleMaterialMapper {

  ModuleMaterialEntity toEntity(final ModuleMaterialRequestDto source);

  ModuleMaterialResponseDto toDto(final ModuleMaterialEntity destination);

  List<ModuleMaterialResponseDto> toDtoList(final List<ModuleMaterialEntity> entityList);
}
