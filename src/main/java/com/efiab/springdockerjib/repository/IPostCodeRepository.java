package com.efiab.springdockerjib.repository;

import com.efiab.springdockerjib.model.PostCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostCodeRepository extends JpaRepository<PostCode, Integer> {}
