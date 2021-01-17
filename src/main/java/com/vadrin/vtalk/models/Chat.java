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
@Table(name = "chats")
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public int id;
  public String message;
  public String sender;
  public String receiver;
  public Timestamp createdOn;
}
