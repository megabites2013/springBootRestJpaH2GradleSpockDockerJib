package com.efiab.springdockerjib.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Access {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull @Column private String accessName;

  @ManyToMany(mappedBy = "accesses")
  private Collection<Role> roles;

  public Access(String accessName) {
    this.accessName = accessName;
  }

  public Access() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccessName() {
    return accessName;
  }

  public void setAccessName(String accessName) {
    this.accessName = accessName;
  }

  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }
}
