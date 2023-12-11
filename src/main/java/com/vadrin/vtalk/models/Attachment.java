package com.vadrin.vtalk.models;

import lombok.Data;

@Data
public class Attachment {
  public int chatId;
  public String data;
  public String attachmentMeta;
}
