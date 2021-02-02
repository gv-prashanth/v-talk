package com.vadrin.vtalk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vadrin.vtalk.models.ChatDTO;

@Controller
public class WebController {

  @PostMapping("/online")
  public String vadrin(@ModelAttribute ChatDTO chatDTO, Model model) {
    model.addAttribute("globalSender", chatDTO.getSender());
    model.addAttribute("globalReceiver", chatDTO.getReceiver());
    return "online";
  }
  
}
