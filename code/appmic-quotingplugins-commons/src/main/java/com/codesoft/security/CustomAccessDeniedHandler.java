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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(GenericResponseConstants.CONTENT_TYPE);
    response.getWriter().write(new ObjectMapper()
        .writeValueAsString(getErrorResponseGenericResponse(accessDeniedException.getMessage(), request.getRequestURI())));
  }

  private static GenericResponse<ErrorResponse> getErrorResponseGenericResponse(final String message, final String path) {
    return GenericResponseUtils.buildGenericResponseError(StringUtils.EMPTY, new ErrorResponse(HttpServletResponse.SC_FORBIDDEN,
        StringUtils.joinWith(GenericResponseConstants.DASH, message, path)));
  }
}
