package com.vadrin.vtalk.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "attachments")
public class Attachment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;
  public int chatId;
  public String data;
  public String attachmentMeta;
}
