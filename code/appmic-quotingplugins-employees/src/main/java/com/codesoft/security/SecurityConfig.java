package com.codesoft.security;

import com.codesoft.security.filter.CustomAuthorizationFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  private static final String ROLE_ADMIN = "ADMIN";

  private static final String ROLE_USER = "USER";

  private static final String ID = "/{id}";

  @Value("${app.endpoints.employee}")
  private String retrieveEmployeeRootPath;

  @Value("${app.endpoints.role}")
  private String retrieveRoleRootPath;

  @Value("${app.endpoints.user}")
  private String retrieveUserRootPath;

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
    final String employeePathWithId = StringUtils.join(retrieveEmployeeRootPath, ID);
    final String rolePathWithId = StringUtils.join(retrieveRoleRootPath, ID);
    final String userPathWithId = StringUtils.join(retrieveUserRootPath, ID);
    return http.authorizeHttpRequests(auth -> auth
        // Permitimos que K8s (y cualquiera) vea el estado de salud sin loguearse
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers("/api/employees/user/loginByUsernameAndPassword", "/api/employees/user/loginByUsername").permitAll()
        .requestMatchers(HttpMethod.GET, retrieveEmployeeRootPath, retrieveRoleRootPath, retrieveUserRootPath)
        .hasAnyRole(ROLE_ADMIN, ROLE_USER)
        .requestMatchers(HttpMethod.POST, retrieveEmployeeRootPath, retrieveRoleRootPath, retrieveUserRootPath)
        .hasAnyRole(ROLE_ADMIN, ROLE_USER)
        .requestMatchers(HttpMethod.PUT, employeePathWithId, rolePathWithId, userPathWithId).hasRole(ROLE_ADMIN)
        .requestMatchers(HttpMethod.DELETE, employeePathWithId, rolePathWithId, userPathWithId).hasRole(ROLE_ADMIN)
        .requestMatchers(HttpMethod.PATCH, employeePathWithId, rolePathWithId, userPathWithId).hasRole(ROLE_ADMIN)
        .anyRequest().authenticated())
      .exceptionHandling(exception -> exception
        .accessDeniedHandler(customAccessDeniedHandler)
        .authenticationEntryPoint(customAuthenticationEntryPoint))
      .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
      .csrf(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .build();
  }
}
