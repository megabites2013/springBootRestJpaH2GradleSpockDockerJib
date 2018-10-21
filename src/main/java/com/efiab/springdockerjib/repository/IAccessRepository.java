package com.efiab.springdockerjib.repository;

import com.efiab.springdockerjib.model.Access;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccessRepository extends JpaRepository<Access, Integer> {
  Access findAccessByAccessName(String name);

  @Override
  void delete(Access access);
}
