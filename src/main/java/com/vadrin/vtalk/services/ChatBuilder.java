package com.vadrin.vtalk.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.stereotype.Service;

import com.vadrin.vtalk.models.Chat;
import com.vadrin.vtalk.models.ChatDTO;

@Service
public class ChatBuilder {

  public Chat buildWithChatDTO(ChatDTO chatDTO) {
    Chat chat = new Chat();
    chat.setMessage(chatDTO.getMessage());
    chat.setSender(chatDTO.getSender().toLowerCase());
    chat.setReceiver(chatDTO.getReceiver().toLowerCase());
    chat.setCreatedOn(Timestamp.from(Instant.now()));
    return chat;
  }

}
