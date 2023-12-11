package com.vadrin.vtalk.services;

import org.springframework.stereotype.Service;

import com.google.cloud.Timestamp;
import com.vadrin.vtalk.models.Chat;
import com.vadrin.vtalk.models.ChatDTO;

@Service
public class ChatBuilder {

  public Chat buildWithChatDTO(ChatDTO chatDTO) {
    Chat chat = new Chat();
    chat.setMessage(chatDTO.getMessage());
    chat.setSender(chatDTO.getSender().toLowerCase());
    chat.setReceiver(chatDTO.getReceiver().toLowerCase());
    chat.setCreatedOn(Timestamp.now());
    return chat;
  }

}
