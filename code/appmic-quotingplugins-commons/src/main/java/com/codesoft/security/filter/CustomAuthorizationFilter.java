package com.codesoft.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.codesoft.utils.GenericResponseConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

  private final SecretKey secretKey;

  public CustomAuthorizationFilter(final SecretKey secretKey) {
    this.secretKey = secretKey;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request, @org.springframework.lang.NonNull final HttpServletResponse response,
      @org.springframework.lang.NonNull final FilterChain filterChain)
      throws ServletException, IOException {
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (HttpMethod.OPTIONS.matches(request.getMethod())) {
      filterChain.doFilter(request, response);
      return;
    }
    if (header == null || !header.startsWith(GenericResponseConstants.PREFIX_TOKEN)) {
      filterChain.doFilter(request, response);
      return;
    }
    final String token = StringUtils.substringAfter(header, GenericResponseConstants.PREFIX_TOKEN);
    try {
      final Claims claims = Jwts.parser()
          .verifyWith(this.secretKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();
      final String username = claims.get("username", String.class);
      final List<String> roles = claims.get("roles", List.class); // This is personalized claim
      final List<GrantedAuthority> authorities = (roles == null) ? List.of() :
          roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toList());
      final Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception ex) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
    filterChain.doFilter(request, response);
  }
}
