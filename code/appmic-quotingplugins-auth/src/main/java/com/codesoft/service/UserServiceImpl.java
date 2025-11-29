package com.codesoft.service;

import java.util.Objects;

import com.codesoft.dto.UserResponseDto;
import com.codesoft.exception.AuthMessageEnum;
import com.codesoft.utils.AuthConstants;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.WebClientErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserServiceImpl implements UserService {

  private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  private final WebClient.Builder loadBalancedWebClientBuilder;

  private final WebClient simpleWebClient;

  private final Environment env;

  private final ObjectMapper objectMapper;

  public UserServiceImpl(final WebClient.Builder loadBalancedWebClientBuilder, final WebClient simpleWebClient, final Environment env,
    final ObjectMapper objectMapper) {
    this.loadBalancedWebClientBuilder = loadBalancedWebClientBuilder;
    this.simpleWebClient = simpleWebClient;
    this.env = env;
    this.objectMapper = objectMapper;
  }

  @Override
  public UserResponseDto validateUser(final String username, final String password) {
    try {
      final WebClient client = getWebClient();
      final String uri =
        env.getProperty(AuthConstants.MS_AUTH_SERVICE, AuthConstants.PORT_API_EMPLOYEE_USER_SERVICE);
      final GenericResponse<UserResponseDto> userResponseDto = client.get()
        .uri(uri, uriBuilder -> uriBuilder
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
      final WebClient client = getWebClient();
      final String uri = env.getProperty(AuthConstants.MS_AUTH_SERVICE, AuthConstants.PORT_API_EMPLOYEE_USER_SERVICE);
      final GenericResponse<UserResponseDto> userResponseDto = client.get()
        .uri(uri, uriBuilder -> uriBuilder
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

  private WebClient getWebClient() {
    if (StringUtils.isBlank(env.getProperty(AuthConstants.MS_AUTH_SERVICE))) {
      log.info("El uso de un cliente web simple como MS_AUTH_SERVICE no está configurado.");
      return simpleWebClient;
    } else {
      log.info("Usando el WebClient del balanceador de carga.");
      return loadBalancedWebClientBuilder.build();
    }
  }
}
