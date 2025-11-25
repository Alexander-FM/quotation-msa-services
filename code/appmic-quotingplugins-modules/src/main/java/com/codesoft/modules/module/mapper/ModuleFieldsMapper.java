package com.codesoft.modules.module.mapper;

import java.util.List;

import com.codesoft.modules.module.dto.request.ModuleRequestDto;
import com.codesoft.modules.module.dto.response.ModuleResponseDto;
import com.codesoft.modules.module.model.entity.ModuleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleFieldsMapper {

  ModuleEntity toEntity(final ModuleRequestDto source);

  ModuleResponseDto toDto(final ModuleEntity destination);

  List<ModuleResponseDto> toDtoList(final List<ModuleEntity> entityList);
}
