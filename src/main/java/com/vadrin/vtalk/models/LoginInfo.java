package com.vadrin.vtalk.models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "logininfo")
public class LoginInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;
  public String ipaddress;
  public String useragent;
  public String geoinfo;
  public String osinfo;
  public Timestamp logintime;
  public String sender;
}
