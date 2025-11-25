package com.codesoft.customers.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {

  private Integer id;

  private String fullName;

  private String documentTypeCode;

  private String documentNumber;

  private String phoneNumber;

  private String phoneNumber2;

  private String email;

  private String streetAddress;

  private Boolean isActive;
}
