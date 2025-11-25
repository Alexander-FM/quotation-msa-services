package com.codesoft.modules.module_concepts.mapper;

import java.util.List;

import com.codesoft.modules.module_concepts.dto.request.ModuleConceptsRequestDto;
import com.codesoft.modules.module_concepts.dto.response.ModuleConceptsResponseDto;
import com.codesoft.modules.module_concepts.model.entity.ModuleConceptsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleConceptsFieldsMapper {

  ModuleConceptsEntity toEntity(final ModuleConceptsRequestDto source);

  ModuleConceptsResponseDto toDto(final ModuleConceptsEntity destination);

  List<ModuleConceptsResponseDto> toDtoList(final List<ModuleConceptsEntity> entityList);
}
