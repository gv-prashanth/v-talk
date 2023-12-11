package com.vadrin.vtalk.models;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class UserInfo {

  public String username;
  public Long phone;
  public Timestamp lastNotification;
}
