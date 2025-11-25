package com.codesoft.modules.material_module.mapper;

import java.util.List;

import com.codesoft.modules.material_module.dto.request.MaterialModuleRequestDto;
import com.codesoft.modules.material_module.dto.response.MaterialModuleResponseDto;
import com.codesoft.modules.material_module.model.entity.MaterialModuleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialModuleFieldsMapper {

  MaterialModuleEntity toEntity(final MaterialModuleRequestDto source);

  MaterialModuleResponseDto toDto(final MaterialModuleEntity destination);

  List<MaterialModuleResponseDto> toDtoList(final List<MaterialModuleEntity> entityList);
}
