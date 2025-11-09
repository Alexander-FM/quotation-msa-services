package com.codesoft.employee.mapper;

import java.util.List;

import com.codesoft.employee.dto.request.EmployeeRequestDto;
import com.codesoft.employee.dto.response.EmployeeResponseDto;
import com.codesoft.employee.model.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeFieldsMapper {

  EmployeeEntity toEntity(final EmployeeRequestDto source);

  EmployeeResponseDto toDto(final EmployeeEntity destination);

  List<EmployeeResponseDto> toDtoList(final List<EmployeeEntity> entityList);
}
