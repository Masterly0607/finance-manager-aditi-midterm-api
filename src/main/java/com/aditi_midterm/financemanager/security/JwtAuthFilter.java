package com.aditi_midterm.financemanager.security;

import com.aditi_midterm.financemanager.user.Role;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  public JwtAuthFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String auth = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (auth == null || !auth.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = auth.substring(7);

    try {
      // IMPORTANT: parse as ACCESS token only
      Claims claims = jwtService.parseAccessClaims(token);

      // Reject if someone tries to use a refresh token here
      String type = claims.get("type", String.class);
      if (!"access".equals(type)) {
        SecurityContextHolder.clearContext();
        filterChain.doFilter(request, response);
        return;
      }

      Long userId = Long.valueOf(claims.getSubject());
      String email = claims.get("email", String.class);
      String roleStr = claims.get("role", String.class);
      Role role = Role.valueOf(roleStr);

      var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));

      var authentication =
          new UsernamePasswordAuthenticationToken(
              new UserPrincipal(userId, email, role), null, authorities);

      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception ex) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }
}
