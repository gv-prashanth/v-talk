package com.vadrin.vtalk.models;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Chat {
  public String message;
  public String sender;
  public String receiver;
  public Timestamp createdOn;
}
