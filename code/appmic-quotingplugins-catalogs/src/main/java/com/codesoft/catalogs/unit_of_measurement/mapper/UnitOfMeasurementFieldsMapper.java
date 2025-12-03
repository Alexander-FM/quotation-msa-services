package com.codesoft.catalogs.unit_of_measurement.mapper;

import java.util.List;

import com.codesoft.catalogs.unit_of_measurement.dto.request.UnitOfMeasurementRequestDto;
import com.codesoft.catalogs.unit_of_measurement.dto.response.UnitOfMeasurementResponseDto;
import com.codesoft.catalogs.unit_of_measurement.model.entity.UnitOfMeasurementEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitOfMeasurementFieldsMapper {

  UnitOfMeasurementEntity toEntity(final UnitOfMeasurementRequestDto source);

  UnitOfMeasurementResponseDto toDto(final UnitOfMeasurementEntity destination);

  List<UnitOfMeasurementResponseDto> toDtoList(final List<UnitOfMeasurementEntity> entityList);
}
