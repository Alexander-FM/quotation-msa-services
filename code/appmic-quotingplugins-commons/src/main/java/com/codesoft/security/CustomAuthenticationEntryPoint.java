package com.codesoft.security;

import java.io.IOException;

import com.codesoft.exception.ErrorResponse;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(GenericResponseConstants.CONTENT_TYPE);
    response.getWriter().write(new ObjectMapper()
        .writeValueAsString(getErrorResponseGenericResponse(authException.getMessage(), request.getRequestURI())));
  }

  private static GenericResponse<ErrorResponse> getErrorResponseGenericResponse(final String message, final String path) {
    return GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED,
        StringUtils.joinWith(GenericResponseConstants.DASH, message, path)));
  }
}
