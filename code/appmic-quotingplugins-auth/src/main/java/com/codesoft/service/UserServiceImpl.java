package com.codesoft.service;

import java.util.Objects;

import com.codesoft.dto.UserResponseDto;
import com.codesoft.exception.AuthException;
import com.codesoft.exception.AuthMessageEnum;
import com.codesoft.exception.BaseException;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Service
public class UserServiceImpl implements UserService {

  private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  private final WebClient.Builder loadBalancedWebClientBuilder;

  private final WebClient simpleWebClient;

  private final Environment env;

  public UserServiceImpl(final WebClient.Builder loadBalancedWebClientBuilder, final WebClient simpleWebClient, final Environment env) {
    this.loadBalancedWebClientBuilder = loadBalancedWebClientBuilder;
    this.simpleWebClient = simpleWebClient;
    this.env = env;
  }

  @Override
  public UserResponseDto validateUser(final String username, final String password) {
    try {
      final WebClient client = getWebClient();
      final String uri = env.getProperty("MS_MAINTENANCE_NAME", "http://127.0.0.1:8082/api/employees/user/login");
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
      throw new BaseException(BaseErrorMessage.NOT_FOUND);
    } catch (final WebClientRequestException ex) {
      log.error("Servicio de empleados no disponible (connection refused): {}", ex.getMessage());
      throw new AuthException(AuthMessageEnum.AUTH_EMPLOYEE_SERVICE_UNAVAILABLE);
    } catch (final BaseException ex) {
      throw ex;
    } catch (final Exception ex) {
      log.error("Exception: {}", ex.getMessage());
      throw new BaseException(BaseErrorMessage.ERROR_INTERNAL);
    }
  }

  private WebClient getWebClient() {
    if (StringUtils.isBlank(env.getProperty("MS_MAINTENANCE_NAME"))) {
      log.info("Using simpleWebClient as MS_MAINTENANCE_NAME is not set.");
      return simpleWebClient;
    } else {
      log.info("Using loadBalancedWebClient.");
      return loadBalancedWebClientBuilder.build();
    }
  }
}
