package com.codesoft.customers.customer.controller;

import java.util.List;

import com.codesoft.customers.customer.dto.request.CustomerRequestDto;
import com.codesoft.customers.customer.dto.response.CustomerResponseDto;
import com.codesoft.customers.customer.exception.CustomerException;
import com.codesoft.customers.customer.exception.CustomerMessage;
import com.codesoft.customers.customer.service.CustomerService;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping
  public ResponseEntity<GenericResponse<List<CustomerResponseDto>>> retrieve() {
    final List<CustomerResponseDto> customers = customerService.findAll();
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, customers));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<CustomerResponseDto>> retrieveById(@PathVariable(value = "id") final Integer id) {
    final CustomerResponseDto customer = customerService.findById(id);
    return ResponseEntity.status(HttpStatus.OK)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, customer));
  }

  @PostMapping
  public ResponseEntity<GenericResponse<CustomerResponseDto>> create(@Valid @RequestBody final CustomerRequestDto requestDto) {
    final CustomerResponseDto responseDto = this.customerService.create(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, responseDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<CustomerResponseDto>> update(@PathVariable(value = "id") final Integer id,
    @Valid @RequestBody final CustomerRequestDto requestDto) {
    if (id == null || id <= 0) {
      throw new CustomerException(CustomerMessage.CUSTOMER_NOT_FOUND);
    }
    final CustomerResponseDto existing = customerService.findById(id);
    if (ObjectUtils.isNotEmpty(existing)) {
      requestDto.setId(existing.getId());
      return ResponseEntity.status(HttpStatus.OK)
        .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, this.customerService.create(requestDto)));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, null));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<Object>> delete(@PathVariable(value = "id") final Integer id) {
    this.customerService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
      .body(GenericResponseUtils.buildGenericResponseSuccess(StringUtils.EMPTY, null));
  }
}
