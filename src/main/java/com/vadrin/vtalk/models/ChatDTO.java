package com.vadrin.vtalk.models;

import lombok.Data;

@Data
public class ChatDTO {

  public String message;
  public String sender;
  public String receiver;

}
