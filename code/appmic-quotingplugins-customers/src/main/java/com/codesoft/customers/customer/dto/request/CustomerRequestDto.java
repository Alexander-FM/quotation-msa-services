package com.codesoft.customers.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

  private Integer id;

  @NotNull(message = "El nombre de la compañía no puede ser nulo.")
  @NotEmpty(message = "El nombre de la compañía no puede estar vacío.")
  private String companyName;

  @NotNull(message = "El tipo de documento no puede ser nulo.")
  @NotEmpty(message = "El tipo de documento no puede estar vacío.")
  @Size(max = 20, message = "El tipo de documento no puede exceder 20 caracteres.")
  private String documentTypeCode;

  @NotNull(message = "El número de documento no puede ser nulo.")
  @NotEmpty(message = "El número de documento no puede estar vacío.")
  @Size(max = 20, message = "El número de documento no puede exceder 20 caracteres.")
  private String documentNumber;

  @NotNull(message = "El correo electrónico no puede ser nulo.")
  @NotEmpty(message = "El correo electrónico no puede estar vacío.")
  @Email(message = "El formato del correo electrónico es inválido.")
  private String email;

  @NotNull(message = "El teléfono principal no puede ser nulo.")
  @NotEmpty(message = "El teléfono principal no puede estar vacío.")
  @Size(max = 20, message = "El teléfono principal no puede exceder 20 caracteres.")
  @Pattern(regexp = "^[0-9+\\-()\\s]+$", message = "Formato de teléfono inválido.")
  private String phoneNumber;

  @Size(max = 20, message = "El teléfono secundario no puede exceder 20 caracteres.")
  @Pattern(regexp = "^[0-9+\\-()\\s]*$", message = "Formato de teléfono secundario inválido.")
  private String phoneNumber2;

  @NotNull(message = "El estado activo no puede ser nulo.")
  private Boolean isActive;
}
