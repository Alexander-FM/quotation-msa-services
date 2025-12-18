package com.codesoft.quotings.module.mapper;

import java.util.List;

import com.codesoft.quotings.module.dto.request.ModuleMaterialRequestDto;
import com.codesoft.quotings.module.dto.response.ModuleMaterialResponseDto;
import com.codesoft.quotings.module.model.entity.ModuleMaterialEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleMaterialMapper {

  ModuleMaterialEntity toEntity(final ModuleMaterialRequestDto source);

  ModuleMaterialResponseDto toDto(final ModuleMaterialEntity destination);

  List<ModuleMaterialResponseDto> toDtoList(final List<ModuleMaterialEntity> entityList);
}
