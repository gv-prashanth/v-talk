package com.vadrin.vtalk.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vadrin.vtalk.models.Chat;
import com.vadrin.vtalk.models.ChatDTO;
import com.vadrin.vtalk.services.ChatService;

@RestController
@RequestMapping("/v2")
public class ChatControllerV2 {

  @Autowired
  ChatService chatService;

  @PostMapping("/chats")
  public void postChats(@RequestBody ChatDTO chatDTO) {
    chatService.save(chatDTO);
  }

  @PostMapping("/get/chats")
  public List<Chat> getChats(@RequestBody ChatDTO chatDTO) {
    return chatService.findBySenderReceiver(chatDTO.getSender().toLowerCase(), chatDTO.getReceiver().toLowerCase());
  }

}
