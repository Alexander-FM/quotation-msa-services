package com.codesoft.customers.customer.service;

import java.util.List;
import java.util.Set;

import com.codesoft.customers.customer.dto.request.CustomerRequestDto;
import com.codesoft.customers.customer.dto.response.CustomerResponseDto;

public interface CustomerService {

  List<CustomerResponseDto> findAll();

  List<CustomerResponseDto> searchAllByDocumentNumber(final Set<String> idList);

  CustomerResponseDto findById(final Integer id);

  CustomerResponseDto searchByDocumentNumber(final String documentNumber);

  CustomerResponseDto create(final CustomerRequestDto requestDto);

  void deleteById(final Integer id);

}
