package com.efiab.springdockerjib.repository;

import com.efiab.springdockerjib.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Integer> {

  Role findRoleByRoleName(String name);

  @Override
  void delete(Role role);
}
