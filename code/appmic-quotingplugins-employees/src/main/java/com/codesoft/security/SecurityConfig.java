package com.codesoft.security;

import java.util.Arrays;
import java.util.List;

import com.codesoft.security.filter.CustomAuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

  private static final String ROLE_ADMIN = "ADMIN";

  private static final String ROLE_USER = "USER";

  private static final String EMPLOYEE_ROOT_PATH = "/api/employees";

  private static final String EMPLOYEE_PATH = "/api/employees/{id}";

  private static final String ROLE_ROOT_PATH = "/api/roles";

  private static final String ROLE_PATH = "/api/roles/{id}";

  private static final String USER_ROOT_PATH = "/api/users";

  private static final String USER_PATH = "/api/users/{id}";

  private final CustomAuthorizationFilter customAuthorizationFilter;

  private final CustomAccessDeniedHandler customAccessDeniedHandler;

  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  public SecurityConfig(final CustomAuthorizationFilter customAuthorizationFilter, CustomAccessDeniedHandler customAccessDeniedHandler,
      CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
    this.customAuthorizationFilter = customAuthorizationFilter;
    this.customAccessDeniedHandler = customAccessDeniedHandler;
    this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/users/login", "/api/users").permitAll()
            .requestMatchers(HttpMethod.GET, EMPLOYEE_ROOT_PATH, ROLE_ROOT_PATH).hasAnyRole(ROLE_ADMIN, ROLE_USER)
            .requestMatchers(HttpMethod.POST, EMPLOYEE_ROOT_PATH, ROLE_ROOT_PATH, USER_ROOT_PATH).hasAnyRole(ROLE_ADMIN, ROLE_USER)
            .requestMatchers(HttpMethod.PUT, EMPLOYEE_PATH, ROLE_PATH, USER_PATH).hasRole(ROLE_ADMIN)
            .requestMatchers(HttpMethod.DELETE, EMPLOYEE_PATH, ROLE_PATH, USER_PATH).hasRole(ROLE_ADMIN)
            .requestMatchers(HttpMethod.PATCH, EMPLOYEE_PATH, ROLE_PATH, USER_PATH).hasRole(ROLE_ADMIN)
            .anyRequest().authenticated())
        .exceptionHandling(exception -> exception
            .accessDeniedHandler(customAccessDeniedHandler)
            .authenticationEntryPoint(customAuthenticationEntryPoint))
        .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(List.of("*"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
    config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  FilterRegistrationBean<CorsFilter> corsFilter() {
    FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
        new CorsFilter(corsConfigurationSource()));
    corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return corsBean;
  }
}
