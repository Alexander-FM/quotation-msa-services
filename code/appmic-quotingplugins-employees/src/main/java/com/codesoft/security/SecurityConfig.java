package com.codesoft.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable) // desactivar si clientes no usan cookies
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/**").permitAll() // permitir tus APIs durante desarrollo
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults()); // mantener basic auth para otras rutas si se necesita
    return http.build();
  }

  // Opcional: define un usuario en memoria para evitar la contraseña generada por Spring
  @Bean
  public UserDetailsService users(BCryptPasswordEncoder byBCryptPasswordEncoder) {
    var user = User.builder()
        .username("admin")
        .password(byBCryptPasswordEncoder.encode("admin"))
        .roles("ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}
