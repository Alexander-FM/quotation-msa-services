package com.codesoft.employees.employee.mapper;

import java.util.List;

import com.codesoft.employees.employee.dto.request.EmployeeRequestDto;
import com.codesoft.employees.employee.dto.response.EmployeeResponseDto;
import com.codesoft.employees.employee.model.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeFieldsMapper {

  EmployeeEntity toEntity(final EmployeeRequestDto source);

  EmployeeResponseDto toDto(final EmployeeEntity destination);

  List<EmployeeResponseDto> toDtoList(final List<EmployeeEntity> entityList);
}
