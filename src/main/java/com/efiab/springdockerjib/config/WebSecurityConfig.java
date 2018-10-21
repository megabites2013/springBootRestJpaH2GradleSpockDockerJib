package com.efiab.springdockerjib.config;

import com.efiab.springdockerjib.security.AppUserDetailService;
import com.efiab.springdockerjib.security.JWTAuthenticationFilter;
import com.efiab.springdockerjib.security.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Web security config.
 *
 * <p>WebSecurityConfig class
 *
 * @author Sebex
 * @date 2018/10/20
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private AppUserDetailService userDetailService;

  /**
   * Distributed centralized session with redis is another
   * option: @Configuration @EnableRedisHttpSession public class HttpSessionConfig { }
   *
   * @param http the HttpSecurity
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/")
        .permitAll()
        .antMatchers("/app.html")
        .permitAll()
        .antMatchers("/swagger-ui.html")
        .permitAll()
        .antMatchers("/v2/**/**")
        .permitAll()
        .antMatchers("/csrf")
        .permitAll()
        .antMatchers("/webjars/**/**")
        .permitAll()
        .antMatchers("/swagger-resources/**/**")
        .permitAll()
        .antMatchers("/console/**/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/login")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(
            new JWTLoginFilter("/login", authenticationManager()),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // if Create a default account in mem
    /* auth.inMemoryAuthentication()
    .withUser("admin")
    .password(passwordEncoder().encode("password"))
    .roles("ADMIN");*/

    // we auth against DB
    auth.userDetailsService(userDetailService);
  }

  /**
   * Password encoder password encoder.
   *
   * @return the password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) {
    // AuthenticationTokenFilter will ignore the below paths
    web.ignoring()
        .antMatchers(
            HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js")
        .and()
        .ignoring()
        .antMatchers("/console/**/**");
  }
}
