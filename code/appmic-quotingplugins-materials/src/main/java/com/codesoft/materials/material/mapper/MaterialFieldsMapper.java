package com.codesoft.materials.material.mapper;

import java.util.List;

import com.codesoft.materials.material.dto.request.MaterialRequestDto;
import com.codesoft.materials.material.dto.response.MaterialResponseDto;
import com.codesoft.materials.material.model.entity.MaterialEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialFieldsMapper {

  MaterialEntity toEntity(final MaterialRequestDto source);

  MaterialResponseDto toDto(final MaterialEntity destination);

  List<MaterialResponseDto> toDtoList(final List<MaterialEntity> entityList);
}
