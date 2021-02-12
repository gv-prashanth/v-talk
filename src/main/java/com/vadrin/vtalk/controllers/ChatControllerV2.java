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
import com.vadrin.vtalk.services.ImageService;

@RestController
@RequestMapping("/v2")
public class ChatControllerV2 {

  @Autowired
  ChatService chatService;

  @Autowired
  ImageService imageService;

  @PostMapping("/chats")
  public void postChats(@RequestBody ChatDTO chatDTO) {
    chatService.save(chatDTO);
  }

  @PostMapping("/get/chats")
  public List<Chat> getChats(@RequestBody ChatDTO chatDTO) {
    List<Chat> toReturn = chatService.findBySenderReceiver(chatDTO.getSender().toLowerCase(), chatDTO.getReceiver().toLowerCase());
    toReturn.parallelStream().filter(c -> c.getMessage().startsWith("data:image")).forEach(c -> c.setMessage(imageService.reduceImageSize(c.getMessage())));
    return toReturn;
  }

}
