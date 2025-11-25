package com.codesoft.customers.customer.mapper;

import java.util.List;

import com.codesoft.customers.customer.dto.request.CustomerRequestDto;
import com.codesoft.customers.customer.dto.response.CustomerResponseDto;
import com.codesoft.customers.customer.model.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerFieldsMapper {

  CustomerEntity toEntity(final CustomerRequestDto source);

  CustomerResponseDto toDto(final CustomerEntity destination);

  List<CustomerResponseDto> toDtoList(final List<CustomerEntity> entityList);
}
