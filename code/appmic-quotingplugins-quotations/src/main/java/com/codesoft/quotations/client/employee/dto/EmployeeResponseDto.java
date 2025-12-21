package com.codesoft.quotations.client.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {

  private Integer id;

  private String fullName;

  private String documentTypeCode;

  private String documentNumber;

  private String phoneNumber;

  private String phoneNumber2;

  private String streetAddress;

  private Integer userId;

  private Boolean isActive;
}
