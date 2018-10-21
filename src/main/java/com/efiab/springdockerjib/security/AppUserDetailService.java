package com.efiab.springdockerjib.security;

import com.efiab.springdockerjib.model.User;
import com.efiab.springdockerjib.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * AppUserDetailService class
 *
 * @author Sebex
 * @date 2018/10/20
 */
@Service
public class AppUserDetailService implements UserDetailsService {

  @Autowired private IUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {

    User user = userRepository.findUserByUsername(username);
    if (user == null) {
      return null;
    }

    return org.springframework.security.core.userdetails.User.withUsername(username)
        .password(user.getPassword())
        .roles(user.getRolesAsStr())
        .authorities(Collections.emptyList())
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}
