package com.vadrin.vtalk.models;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class LoginInfo {
  public String ipaddress;
  public String useragent;
  public String geoinfo;
  public String osinfo;
  public Timestamp logintime;
  public String sender;
}
