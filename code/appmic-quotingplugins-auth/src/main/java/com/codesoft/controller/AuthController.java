package com.codesoft.controller;

import java.util.Date;
import java.util.List;

import com.codesoft.dto.RoleResponseDto;
import com.codesoft.dto.UserResponseDto;
import com.codesoft.dto.request.LoginRequest;
import com.codesoft.dto.response.TokenResponse;
import com.codesoft.exception.BaseException;
import com.codesoft.service.UserService;
import com.codesoft.utils.BaseErrorMessage;
import com.codesoft.utils.GenericResponse;
import com.codesoft.utils.GenericResponseConstants;
import com.codesoft.utils.GenericResponseUtils;
import com.codesoft.utils.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@Component
@Slf4j
public class AuthController {

  private final UserService userService;

  @Getter
  private SecretKey secretKey;

  @Value("${jwt.secret}")
  public void setSecret(final String secret) {
    secretKey = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public AuthController(final UserService userService) {
    this.userService = userService;
  }

  @Bean
  private static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @PostMapping("/login")
  public ResponseEntity<GenericResponse<TokenResponse>> login(@RequestBody final LoginRequest loginRequest,
    HttpServletResponse httpServletResponse) {
    if (ObjectUtils.isEmpty(loginRequest.username()) || ObjectUtils.isEmpty(loginRequest.password())) {
      throw new BaseException(BaseErrorMessage.BAD_REQUEST);
    }
    final UserResponseDto user = userService.validateUser(loginRequest.username(), loginRequest.password());
    final TokenResponse tokenResponse = buildTokenResponse(user, null);
    httpServletResponse.addHeader(GenericResponseConstants.HEADER_AUTHORIZATION, tokenResponse.getAccessToken());
    return ResponseEntity.ok(
      GenericResponseUtils.buildGenericResponseSuccess(JwtConstants.GENERATED_TOKEN, tokenResponse));
  }

  @PostMapping("/validate")
  public ResponseEntity<GenericResponse<Object>> validateToken(
    @RequestHeader(GenericResponseConstants.HEADER_AUTHORIZATION) final String authHeader) {
    try {
      if (authHeader == null || !authHeader.startsWith(GenericResponseConstants.PREFIX_TOKEN)) {
        throw new BaseException(BaseErrorMessage.UNAUTHORIZED);
      }
      final String token = StringUtils.substring(authHeader, GenericResponseConstants.PREFIX_TOKEN.length());
      Claims claims = Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
      return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseSuccess(JwtConstants.VALID_TOKEN, claims));
    } catch (final Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(GenericResponseUtils.buildGenericResponseError(JwtConstants.INVALID_TOKEN, null));
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<GenericResponse<TokenResponse>> refresh(
    @RequestHeader(GenericResponseConstants.HEADER_AUTHORIZATION) final String authHeader) {
    if (authHeader == null || !authHeader.startsWith(GenericResponseConstants.PREFIX_TOKEN)) {
      throw new BaseException(BaseErrorMessage.UNAUTHORIZED);
    }
    final String refreshToken = StringUtils.substring(authHeader, GenericResponseConstants.PREFIX_TOKEN.length());
    try {
      // 1. Validar el Refresh Token
      Claims claims = Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(refreshToken)
        .getPayload();
      final String username = claims.getSubject();
      // 2. Buscar usuario en BD (Válida si está activo y trae roles frescos)
      final UserResponseDto userFresh = userService.findByUsername(username);
      // 3. Generar nuevos tokens
      final TokenResponse tokenResponse = buildTokenResponse(userFresh, refreshToken);

      return ResponseEntity.ok(GenericResponseUtils.buildGenericResponseSuccess(JwtConstants.REFRESH_TOKEN_SUCCESS, tokenResponse));
    } catch (JwtException e) {
      log.warn("Refresh token inválido o expirado para el usuario. Error: {}", e.getMessage());
      throw new BaseException(BaseErrorMessage.UNAUTHORIZED);
    }
  }

  private TokenResponse buildTokenResponse(final UserResponseDto user, final String existingRefreshToken) {
    Date now = new Date();
    Date accessValidity = new Date(now.getTime() + JwtConstants.ACCESS_TOKEN_VALIDITY);

    final List<String> roles = user.getRoles().stream()
      .map(RoleResponseDto::getRoleName)
      .toList();

    // 1. Crear SIEMPRE un nuevo Access Token
    final Claims accessClaims = Jwts.claims()
      .add("username", user.getUsername())
      .add("roles", roles)
      .build();

    String accessToken = Jwts.builder()
      .subject(user.getUsername())
      .claims(accessClaims)
      .issuedAt(now)
      .expiration(accessValidity)
      .signWith(getSecretKey())
      .compact();

    // 2. Decidir sobre el Refresh Token
    String refreshToken;
    if (StringUtils.isNotBlank(existingRefreshToken)) {
      // Estamos en flujo de REFRESH: Devolvemos el mismo que nos enviaron
      refreshToken = existingRefreshToken;
    } else {
      // Estamos en flujo de LOGIN: Creamos uno nuevo
      Date refreshValidity = new Date(now.getTime() + JwtConstants.REFRESH_TOKEN_VALIDITY);
      refreshToken = Jwts.builder()
        .subject(user.getUsername())
        .issuedAt(now)
        .expiration(refreshValidity)
        .signWith(getSecretKey())
        .compact();
    }
    return TokenResponse.builder()
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .expiresIn(JwtConstants.ACCESS_TOKEN_VALIDITY) // Devuelve la duración del access token
      .issuedAt(now.toString())
      .username(user.getUsername())
      .build();
  }
}
