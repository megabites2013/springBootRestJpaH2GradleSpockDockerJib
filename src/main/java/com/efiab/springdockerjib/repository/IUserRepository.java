package com.efiab.springdockerjib.repository;

import com.efiab.springdockerjib.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
  User findUserByUsername(String name);

  @Override
  void delete(User user);
}
