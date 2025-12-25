package com.codesoft.quotations.security;

import java.nio.charset.StandardCharsets;

import com.codesoft.security.CustomAccessDeniedHandler;
import com.codesoft.security.CustomAuthenticationEntryPoint;
import com.codesoft.security.filter.CustomAuthorizationFilter;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Bean
  public SecretKey jwtSecretKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  @Bean
  public CustomAuthorizationFilter customAuthorizationFilter(SecretKey jwtSecretKey) {
    return new CustomAuthorizationFilter(jwtSecretKey);
  }

  @Bean
  public CustomAccessDeniedHandler customAccessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }

  @Bean
  public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
    return new CustomAuthenticationEntryPoint();
  }
}
