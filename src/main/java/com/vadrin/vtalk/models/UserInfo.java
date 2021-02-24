package com.vadrin.vtalk.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "userinfo")
public class UserInfo {

  @Id
  public String username;
  public String phone;
}
