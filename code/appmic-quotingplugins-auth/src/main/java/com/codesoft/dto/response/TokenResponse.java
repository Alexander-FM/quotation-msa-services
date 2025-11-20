package com.codesoft.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("token_type")
  @Builder.Default
  private String tokenType = "Bearer";

  @JsonProperty("expires_in")
  private Long expiresIn; // Tiempo en milisegundos o segundos hasta que expire

  @JsonProperty("issued_at")
  private String issuedAt; // Fecha legible de emisión

  private String username;
}
