package com.efiab.springdockerjib.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
  public JWTLoginFilter(String url, AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(url));
    setAuthenticationManager(authManager);
    // System.out.println("JWTLoginFilter called");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws IOException {
    AccountCredentials creds =
        new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(
            creds.getUsername(), creds.getPassword(), Collections.emptyList());
    Authentication authentication = getAuthenticationManager().authenticate(token);
    return authentication;
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
      throws IOException {
    TokenAuthenticationService.addAuthentication(res, auth.getName());
  }
}
