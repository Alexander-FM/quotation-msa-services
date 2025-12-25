package com.codesoft.service;

import java.util.Objects;

import com.codesoft.config.WebClientFactory;
import com.codesoft.dto.UserResponseDto;
import com.codesoft.exception.AuthMessageEnum;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.WebClientErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final WebClient webClient;

  private final WebClientErrorHandler errorHandler;

  // Inyectas tu clase
  public UserServiceImpl(final WebClientFactory webClientFactory, final WebClientErrorHandler errorHandler,
    @Value("${app.external.employee-service-url}") final String employeeServiceUrl) {
    log.info("Connecting to Employee Service at: {}", employeeServiceUrl);
    this.webClient = webClientFactory.retrieveWebClient(employeeServiceUrl);
    this.errorHandler = errorHandler;
  }

  @Override
  public UserResponseDto validateUser(final String username, final String password) {
    try {
      final GenericResponse<UserResponseDto> userResponseDto = webClient.get()
        .uri("/user/loginByUsernameAndPassword", uriBuilder -> uriBuilder
          .queryParam("username", username)
          .queryParam("password", password)
          .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<GenericResponse<UserResponseDto>>() {
        })
        .block();
      if (Objects.nonNull(userResponseDto) && ObjectUtils.isNotEmpty(userResponseDto.getBody())) {
        log.info("User found successfully: {}", userResponseDto.getBody().getUsername());
        return userResponseDto.getBody();
      }
      return null;
    } catch (final Exception ex) {
      throw errorHandler.handle(ex, AuthMessageEnum.AUTH_EMPLOYEE_SERVICE_UNAVAILABLE);
    }
  }

  @Override
  public UserResponseDto findByUsername(String username) {
    try {
      final GenericResponse<UserResponseDto> userResponseDto = webClient.get()
        .uri("/user/loginByUsername", uriBuilder -> uriBuilder
          .queryParam("username", username) // Solo enviamos username
          .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<GenericResponse<UserResponseDto>>() {
        })
        .block();
      if (Objects.nonNull(userResponseDto) && ObjectUtils.isNotEmpty(userResponseDto.getBody())) {
        return userResponseDto.getBody();
      }
      return null;
    } catch (final Exception ex) {
      throw errorHandler.handle(ex, AuthMessageEnum.AUTH_EMPLOYEE_SERVICE_UNAVAILABLE);
    }
  }
}
