package com.codesoft.employees.employee.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.employees.employee.client.catalog_item.service.CatalogItemClient;
import com.codesoft.employees.employee.dto.request.EmployeeRequestDto;
import com.codesoft.employees.employee.dto.response.EmployeeResponseDto;
import com.codesoft.employees.employee.exception.EmployeeException;
import com.codesoft.employees.employee.exception.EmployeeMessage;
import com.codesoft.employees.employee.mapper.EmployeeFieldsMapper;
import com.codesoft.employees.employee.model.entity.EmployeeEntity;
import com.codesoft.employees.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  private final EmployeeFieldsMapper employeeFieldsMapper;

  private final CatalogItemClient catalogItemClient;

  @Override
  public List<EmployeeResponseDto> findAll() {
    final List<EmployeeEntity> entities = employeeRepository.findAll().stream().toList();
    return employeeFieldsMapper.toDtoList(entities);
  }

  @Override
  public EmployeeResponseDto findById(final Integer id) {
    final Optional<EmployeeEntity> employeeEntity = this.employeeRepository.findById(id);
    return employeeEntity.map(this.employeeFieldsMapper::toDto)
      .orElseThrow(() -> new EmployeeException(EmployeeMessage.EMPLOYEE_NOT_FOUND));
  }

  @Override
  public EmployeeResponseDto create(final EmployeeRequestDto requestDto) {
    try {
      final String documentTypeCode = catalogItemClient.searchByDocumentTypeCode(requestDto.getDocumentTypeCode()).getCode();
      requestDto.setDocumentTypeCode(documentTypeCode);
      final EmployeeEntity entity = this.employeeRepository.save(this.employeeFieldsMapper.toEntity(requestDto));
      return this.employeeFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException e) {
      throw new EmployeeException(EmployeeMessage.EMPLOYEE_ALREADY_EXISTS);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    final EmployeeEntity existingEntity = this.employeeRepository.findById(id)
      .orElseThrow(() -> new EmployeeException(EmployeeMessage.EMPLOYEE_NOT_FOUND));
    this.employeeRepository.deleteById(existingEntity.getId());
  }
}
