package com.efiab.springdockerjib.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * TokenAuthenticationService class
 *
 * @author Sebex
 * @date 2018/10/20
 */
@Service
public class TokenAuthenticationService {

  private static final long EXPIRATIONTIME = 3_600_000; // 1 hr
  private static final SecretKey SECRET = MacProvider.generateKey();
  private static final String TOKEN_PREFIX = "Bearer";
  private static final String HEADER_STRING = "Authorization";

  public static void addAuthentication(HttpServletResponse res, String username)
      throws IOException {
    String JWT =
        Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
            .signWith(SECRET, SignatureAlgorithm.HS512)
            .compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    res.addHeader("Content-Type", "application/json");
    res.getWriter().println("{\"Result\":\"JWT Authentication Success.\"}");
    res.flushBuffer();
  }

  public static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      // parse the token.
      String user =
          Jwts.parser()
              .setSigningKey(SECRET)
              .parseClaimsJws(token.replace(TOKEN_PREFIX, "").trim())
              .getBody()
              .getSubject();
      return user != null
          ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList())
          : null;
    }

    return null;
  }
}
