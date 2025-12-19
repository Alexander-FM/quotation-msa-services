package com.codesoft.quotations.modules.module_material.mapper;

import java.util.List;

import com.codesoft.quotations.modules.module_material.dto.request.ModuleMaterialRequestDto;
import com.codesoft.quotations.modules.module_material.dto.response.ModuleMaterialResponseDto;
import com.codesoft.quotations.modules.module_material.entity.ModuleMaterialEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModuleMaterialFieldsMapper {

  @Mapping(target = "module.createdAt", ignore = true)
  ModuleMaterialEntity toEntity(final ModuleMaterialRequestDto source);

  ModuleMaterialResponseDto toDto(final ModuleMaterialEntity destination);

  List<ModuleMaterialResponseDto> toDtoList(final List<ModuleMaterialEntity> entityList);
}
