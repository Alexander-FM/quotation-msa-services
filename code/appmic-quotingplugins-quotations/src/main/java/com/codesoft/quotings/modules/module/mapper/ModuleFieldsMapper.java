package com.codesoft.quotings.modules.module.mapper;

import java.util.List;

import com.codesoft.quotings.modules.module.dto.request.ModuleRequestDto;
import com.codesoft.quotings.modules.module.dto.response.ModuleResponseDto;
import com.codesoft.quotings.modules.module.entity.ModuleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModuleFieldsMapper {

  @Mapping(target = "createdAt", ignore = true)
  ModuleEntity toEntity(final ModuleRequestDto source);

  ModuleResponseDto toDto(final ModuleEntity destination);

  List<ModuleResponseDto> toDtoList(final List<ModuleEntity> entityList);
}
