package com.codesoft.quotations.client.customer.dto;

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

  private String companyName;

  private String documentTypeCode;

  private String documentNumber;

  private String email;

  private String phoneNumber;

  private String phoneNumber2;

  private Boolean isActive;
}
