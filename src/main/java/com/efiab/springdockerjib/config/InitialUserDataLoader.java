package com.efiab.springdockerjib.config;

import com.efiab.springdockerjib.model.Access;
import com.efiab.springdockerjib.model.Role;
import com.efiab.springdockerjib.model.User;
import com.efiab.springdockerjib.repository.IAccessRepository;
import com.efiab.springdockerjib.repository.IRoleRepository;
import com.efiab.springdockerjib.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

/**
 * The type InitialUserDataLoader, to load a user record, for demo only.
 *
 * <p>InitialUserDataLoader class
 *
 * @author Sebex
 * @date 2018/10/20
 */
@Component
public class InitialUserDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private boolean alreadySetup = false;

  @Autowired private IUserRepository userRepository;

  @Autowired private IRoleRepository roleRepository;

  @Autowired private IAccessRepository accessRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {

    if (alreadySetup) {
      return;
    }

    Access readAccess = createAccessIfNotFound("READ_ACCESS");
    Access writeAccess = createAccessIfNotFound("WRITE_ACCESS");

    Collection<Access> adminAccesss = Arrays.asList(readAccess, writeAccess);
    createRoleIfNotFound("ADMIN", adminAccesss);
    createRoleIfNotFound("USER", Arrays.asList(readAccess));

    Role adminRole = roleRepository.findRoleByRoleName("ADMIN");
    User user = new User();
    user.setUsername("admin");
    user.setPassword(passwordEncoder.encode("password"));
    user.setEmail("test@test.com");
    user.setRoles(Arrays.asList(adminRole));
    user.setMobile("+31-1234567");
    user.setSalary(2999.95);
    user.setEnabled(true);
    userRepository.save(user);

    alreadySetup = true;
  }

  @Transactional
  protected Access createAccessIfNotFound(String name) {

    Access Access = accessRepository.findAccessByAccessName(name);
    if (Access == null) {
      Access = new Access(name);
      accessRepository.save(Access);
    }
    return Access;
  }

  @Transactional
  protected Role createRoleIfNotFound(String name, Collection<Access> Accesss) {

    Role role = roleRepository.findRoleByRoleName(name);
    if (role == null) {
      role = new Role(name);
      role.setAccesses(Accesss);
      roleRepository.save(role);
    }
    return role;
  }
}
