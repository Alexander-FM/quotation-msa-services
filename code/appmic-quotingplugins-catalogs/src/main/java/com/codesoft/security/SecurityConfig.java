package com.codesoft.security;

import java.util.Arrays;
import java.util.List;

import com.codesoft.security.filter.CustomAuthorizationFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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

  private static final String ID = "/{id}";

  @Value("${app.endpoints.catalog}")
  private String retrieveCatalogRootPath;

  @Value("${app.endpoints.adjustment-factor}")
  private String retrieveAdjustmentFactorRootPath;

  @Value("${app.endpoints.unit-of-measurement}")
  private String retrieveUnitOfMeasurementRootPath;

  private final CustomAuthorizationFilter customAuthorizationFilter;

  private final CustomAccessDeniedHandler customAccessDeniedHandler;

  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  public SecurityConfig(final CustomAuthorizationFilter customAuthorizationFilter,
    final CustomAccessDeniedHandler customAccessDeniedHandler,
    final CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
    this.customAuthorizationFilter = customAuthorizationFilter;
    this.customAccessDeniedHandler = customAccessDeniedHandler;
    this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    final String pathAdjustmentFactorWithId = StringUtils.join(retrieveAdjustmentFactorRootPath, ID);
    final String pathCatalogWithId = StringUtils.join(retrieveCatalogRootPath, ID);
    final String pathUnitOfMeasurementWithId = StringUtils.join(retrieveUnitOfMeasurementRootPath, ID);
    return http.authorizeHttpRequests(auth -> auth
        // Permitimos que K8s (y cualquiera) vea el estado de salud sin loguearse
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers(HttpMethod.GET, retrieveAdjustmentFactorRootPath, retrieveCatalogRootPath, retrieveUnitOfMeasurementRootPath)
        .hasAnyRole(ROLE_ADMIN, ROLE_USER)
        .requestMatchers(HttpMethod.POST, retrieveAdjustmentFactorRootPath, retrieveCatalogRootPath, retrieveUnitOfMeasurementRootPath)
        .hasAnyRole(ROLE_ADMIN, ROLE_USER)
        .requestMatchers(HttpMethod.PUT, pathAdjustmentFactorWithId, pathCatalogWithId, pathUnitOfMeasurementWithId).hasRole(ROLE_ADMIN)
        .requestMatchers(HttpMethod.DELETE, pathAdjustmentFactorWithId, pathCatalogWithId, pathUnitOfMeasurementWithId).hasRole(ROLE_ADMIN)
        .requestMatchers(HttpMethod.PATCH, pathAdjustmentFactorWithId, pathCatalogWithId, pathUnitOfMeasurementWithId).hasRole(ROLE_ADMIN)
        .anyRequest().authenticated())
      .exceptionHandling(exception -> exception
        .accessDeniedHandler(customAccessDeniedHandler)
        .authenticationEntryPoint(customAuthenticationEntryPoint))
      .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
      .csrf(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
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
