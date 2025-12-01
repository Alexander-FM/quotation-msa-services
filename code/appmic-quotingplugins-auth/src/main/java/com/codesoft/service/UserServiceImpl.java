package com.codesoft.service;

import java.util.Objects;

import com.codesoft.config.WebClientFactory;
import com.codesoft.dto.UserResponseDto;
import com.codesoft.exception.AuthMessageEnum;
import com.codesoft.utils.AuthConstants;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.WebClientErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final WebClient webClient;

  private final ObjectMapper objectMapper;

  public UserServiceImpl(final WebClientFactory webClientFactory, final ObjectMapper objectMapper) {
    this.webClient = webClientFactory.retrieveWebClient(
      AuthConstants.MS_AUTH_SERVICE,
      AuthConstants.PORT_API_EMPLOYEE_USER_SERVICE
    );
    this.objectMapper = objectMapper;
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
        return userResponseDto.getBody();
      }
      return null;
    } catch (final Exception ex) {
      throw WebClientErrorHandler.handle(ex, objectMapper, AuthMessageEnum.AUTH_EMPLOYEE_SERVICE_UNAVAILABLE);
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
      throw WebClientErrorHandler.handle(ex, objectMapper, AuthMessageEnum.AUTH_EMPLOYEE_SERVICE_UNAVAILABLE);
    }
  }
}
