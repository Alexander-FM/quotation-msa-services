package com.codesoft.employees.employee.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.codesoft.employees.employee.client.catalog_item.service.CatalogItemClient;
import com.codesoft.employees.employee.dto.request.EmployeeRequestDto;
import com.codesoft.employees.employee.dto.response.EmployeeResponseDto;
import com.codesoft.employees.employee.exception.EmployeeException;
import com.codesoft.employees.employee.exception.EmployeeMessage;
import com.codesoft.employees.employee.mapper.EmployeeFieldsMapper;
import com.codesoft.employees.employee.model.entity.EmployeeEntity;
import com.codesoft.employees.employee.repository.EmployeeRepository;
import com.codesoft.employees.user.dto.response.UserResponseDto;
import com.codesoft.employees.user.mapper.UserFieldsMapper;
import com.codesoft.employees.user.model.entity.UserEntity;
import com.codesoft.employees.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  private final UserRepository userRepository;

  private final UserFieldsMapper userFieldsMapper;

  private final EmployeeFieldsMapper employeeFieldsMapper;

  private final CatalogItemClient catalogItemClient;

  @Override
  public List<EmployeeResponseDto> findAll() {
    final List<EmployeeEntity> employees = employeeRepository.findAll().stream().toList();
    if (employees.isEmpty()) {
      return List.of();
    }
    final List<Integer> userIds = employees.stream()
      .map(EmployeeEntity::getUserId)
      .distinct()
      .toList();
    final Map<Integer, UserResponseDto> userMap = userRepository.findAllById(userIds).stream()
      .map(userFieldsMapper::toDto)
      .collect(Collectors.toMap(UserResponseDto::getId, dto -> dto));
    return employees.stream().map(emp -> {
      EmployeeResponseDto dto = employeeFieldsMapper.toDto(emp);
      dto.setUserResponseDto(userMap.get(emp.getUserId()));
      return dto;
    }).toList();
  }

  @Override
  public EmployeeResponseDto findById(final Integer id) {
    final EmployeeEntity employeeEntity = this.employeeRepository.findById(id)
      .orElseThrow(() -> new EmployeeException(EmployeeMessage.EMPLOYEE_NOT_FOUND));
    EmployeeResponseDto responseDto = this.employeeFieldsMapper.toDto(employeeEntity);
    final UserResponseDto userResponseDto = fetchUserId(employeeEntity.getUserId());
    responseDto.setUserResponseDto(userResponseDto);
    return responseDto;
  }

  @Override
  public EmployeeResponseDto searchByDocumentNumber(final String documentNumber) {
    final EmployeeEntity employeeEntity = this.employeeRepository.findByDocumentNumber(documentNumber)
      .orElseThrow(() -> new EmployeeException(EmployeeMessage.EMPLOYEE_NOT_FOUND));
    final EmployeeResponseDto responseDto = this.employeeFieldsMapper.toDto(employeeEntity);
    final UserResponseDto userResponseDto = fetchUserId(employeeEntity.getUserId());
    responseDto.setUserResponseDto(userResponseDto);
    return responseDto;
  }

  @Override
  public EmployeeResponseDto create(final EmployeeRequestDto requestDto) {
    if (this.employeeRepository.existsByUserIdAndIdNot(requestDto.getUserId(), requestDto.getId())) {
      throw new EmployeeException(EmployeeMessage.EMPLOYEE_USER_CONFLICT);
    }
    try {
      final String documentTypeCode = catalogItemClient.searchByDocumentTypeCode(requestDto.getDocumentTypeCode()).getCode();
      requestDto.setDocumentTypeCode(documentTypeCode);
      final UserResponseDto userResponseDto = fetchUserId(requestDto.getUserId());
      requestDto.setUserId(userResponseDto.getId());
      final EmployeeEntity entity = this.employeeRepository.save(this.employeeFieldsMapper.toEntity(requestDto));
      final EmployeeResponseDto dto = this.employeeFieldsMapper.toDto(entity);
      dto.setUserResponseDto(userResponseDto);
      return dto;
    } catch (final DataIntegrityViolationException e) {
      log.warn("Data integrity violation while creating employee: {}", e.getMessage());
      throw new EmployeeException(EmployeeMessage.EMPLOYEE_CONFLICT);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    final EmployeeEntity existingEntity = this.employeeRepository.findById(id)
      .orElseThrow(() -> new EmployeeException(EmployeeMessage.EMPLOYEE_NOT_FOUND));
    this.employeeRepository.deleteById(existingEntity.getId());
  }

  private UserResponseDto fetchUserId(final Integer userId) {
    log.info("Fetching user with ID: {}", userId);
    final Optional<UserEntity> entity = this.userRepository.findById(userId);
    log.info("User fetch result for ID {}: {}", userId, entity.isPresent() ? "Found" : "Not Found");
    return entity.map(this.userFieldsMapper::toDto)
      .orElseThrow(() -> new EmployeeException(EmployeeMessage.USER_NOT_FOUND));
  }
}
