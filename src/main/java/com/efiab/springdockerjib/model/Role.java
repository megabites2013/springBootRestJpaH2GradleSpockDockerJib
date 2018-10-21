package com.efiab.springdockerjib.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull @Column private String roleName;

  @ManyToMany(mappedBy = "roles")
  private Collection<User> users;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "roles_accesses",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "access_id", referencedColumnName = "id"))
  private Collection<Access> accesses;

  public Role(String roleName) {
    this.roleName = roleName;
  }

  public Role() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public Collection<User> getUsers() {
    return users;
  }

  public void setUsers(Collection<User> users) {
    this.users = users;
  }

  public Collection<Access> getAccesses() {
    return accesses;
  }

  public void setAccesses(Collection<Access> accesses) {
    this.accesses = accesses;
  }
}
