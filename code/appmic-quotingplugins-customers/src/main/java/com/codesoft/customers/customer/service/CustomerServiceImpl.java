package com.codesoft.customers.customer.service;

import java.util.List;
import java.util.Optional;

import com.codesoft.customers.customer.dto.request.CustomerRequestDto;
import com.codesoft.customers.customer.dto.response.CustomerResponseDto;
import com.codesoft.customers.customer.exception.CustomerException;
import com.codesoft.customers.customer.exception.CustomerMessage;
import com.codesoft.customers.customer.mapper.CustomerFieldsMapper;
import com.codesoft.customers.customer.model.entity.CustomerEntity;
import com.codesoft.customers.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  private final CustomerFieldsMapper customerFieldsMapper;

  @Override
  public List<CustomerResponseDto> findAll() {
    final List<CustomerEntity> entities = customerRepository.findAll().stream().toList();
    return customerFieldsMapper.toDtoList(entities);
  }

  @Override
  public CustomerResponseDto findById(final Integer id) {
    final Optional<CustomerEntity> customerEntity = this.customerRepository.findById(id);
    return customerEntity.map(this.customerFieldsMapper::toDto)
        .orElseThrow(() -> new CustomerException(CustomerMessage.CUSTOMER_NOT_FOUND));
  }

  @Override
  public CustomerResponseDto create(final CustomerRequestDto requestDto) {
    try {
      final CustomerEntity entity = this.customerRepository.save(this.customerFieldsMapper.toEntity(requestDto));
      return this.customerFieldsMapper.toDto(entity);
    } catch (final DataIntegrityViolationException e) {
      throw new CustomerException(CustomerMessage.CUSTOMER_ALREADY_EXISTS);
    }
  }

  @Override
  public void deleteById(final Integer id) {
    final CustomerEntity existingEntity = this.customerRepository.findById(id)
        .orElseThrow(() -> new CustomerException(CustomerMessage.CUSTOMER_NOT_FOUND));
    this.customerRepository.deleteById(existingEntity.getId());
  }
}
