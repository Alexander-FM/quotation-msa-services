package com.codesoft.employees.employee.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.employees.employee.dto.request.EmployeeRequestDto;
import com.codesoft.employees.employee.dto.response.EmployeeResponseDto;
import com.codesoft.employees.employee.mapper.EmployeeFieldsMapper;
import com.codesoft.employees.employee.model.entity.EmployeeEntity;
import com.codesoft.employees.employee.repository.EmployeeRepository;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  private final EmployeeFieldsMapper employeeFieldsMapper;

  @Override
  public List<EmployeeResponseDto> findAll() {
    final List<EmployeeEntity> entities = employeeRepository.findAll().stream().toList();
    return employeeFieldsMapper.toDtoList(entities);
  }

  @Override
  public EmployeeResponseDto findById(final Integer id) {
    final Optional<EmployeeEntity> EmployeeEntity = this.employeeRepository.findById(id);
    return EmployeeEntity.map(this.employeeFieldsMapper::toDto)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
  }

  @Override
  public EmployeeResponseDto create(final EmployeeRequestDto requestDto) {
    final EmployeeEntity entity = this.employeeRepository.save(this.employeeFieldsMapper.toEntity(requestDto));
    return this.employeeFieldsMapper.toDto(entity);
  }

  @Override
  public void deleteById(final Integer id) {
    final EmployeeEntity existingEntity = this.employeeRepository.findById(id)
        .orElseThrow(() -> new BaseException(BaseErrorMessage.NOT_FOUND));
    this.employeeRepository.deleteById(existingEntity.getId());
  }
}
