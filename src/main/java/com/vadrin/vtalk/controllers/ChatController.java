package com.vadrin.vtalk.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

  private String lastChat = "Welcome to V-Talk!";
  private int chatline = 1;

  @GetMapping("/receive")
  public String receive() {
    return lastChat;
  }

  @GetMapping("/send")
  public void send(@RequestParam(name = "inptchat", required = true) String inptchat) {
    this.lastChat = inptchat;
    this.chatline++;
  }

  @GetMapping("/chatline")
  public int chatline() {
    return chatline;
  }

}
