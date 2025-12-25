package com.codesoft.quotations.quotation.dto.request;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuotationRequestDto {

  private Integer id;

  @NotNull(message = "El documento del cliente es obligatorio.")
  @NotEmpty(message = "El documento del cliente no puede estar vacío.")
  @Size(max = 20, message = "El documento del cliente excede los 20 caracteres.")
  private String customerDocumentNumber;

  @NotNull(message = "El documento del vendedor es obligatorio.")
  @NotEmpty(message = "El documento del vendedor no puede estar vacío.")
  @Size(max = 20, message = "El documento del vendedor excede los 20 caracteres.")
  private String employeeDocumentNumber;

  @Valid
  @NotEmpty(message = "La cotización debe tener al menos un detalle (módulo).")
  private Set<QuotationDetailRequestDto> details = new HashSet<>();

}

