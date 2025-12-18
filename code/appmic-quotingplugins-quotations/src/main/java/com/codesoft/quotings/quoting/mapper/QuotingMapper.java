package com.codesoft.quotings.quoting.mapper;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuotingMapper {

  ModuleMaterialEntity toEntity(final ModuleMaterialRequestDto source);

  ModuleMaterialResponseDto toDto(final ModuleMaterialEntity destination);

  List<ModuleMaterialResponseDto> toDtoList(final List<ModuleMaterialEntity> entityList);
}
