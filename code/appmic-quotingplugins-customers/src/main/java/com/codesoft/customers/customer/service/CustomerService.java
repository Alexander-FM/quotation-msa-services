package com.codesoft.customers.customer.service;

import java.util.List;

import com.codesoft.customers.customer.dto.request.CustomerRequestDto;
import com.codesoft.customers.customer.dto.response.CustomerResponseDto;

public interface CustomerService {

  List<CustomerResponseDto> findAll();

  CustomerResponseDto findById(final Integer id);

  CustomerResponseDto create(final CustomerRequestDto requestDto);

  void deleteById(final Integer id);

}
