package com.codesoft.catalogs.adjustment_factor.mapper;

import java.util.List;

import com.codesoft.catalogs.adjustment_factor.dto.request.AdjustmentFactorRequestDto;
import com.codesoft.catalogs.adjustment_factor.dto.response.AdjustmentFactorResponseDto;
import com.codesoft.catalogs.adjustment_factor.model.entity.AdjustmentFactorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdjustmentFactorFieldsMapper {

  AdjustmentFactorEntity toEntity(final AdjustmentFactorRequestDto source);

  AdjustmentFactorResponseDto toDto(final AdjustmentFactorEntity destination);

  List<AdjustmentFactorResponseDto> toDtoList(final List<AdjustmentFactorEntity> entityList);
}
