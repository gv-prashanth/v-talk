package com.vadrin.vtalk.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vadrin.vtalk.models.Chat;
import com.vadrin.vtalk.models.ChatDTO;
import com.vadrin.vtalk.repositories.ChatRepository;

@Service
public class ChatService {

  @Autowired
  ChatRepository chatRepository;

  @Autowired
  ChatBuilder chatBuilder;

  public void save(ChatDTO chatDTO) {
    chatRepository.save(chatBuilder.buildWithChatDTO(chatDTO));
  }

  public List<Chat> findBySenderReceiver(String sender, String receiver) {
    Set<String> combined = Stream.of(sender, receiver).collect(Collectors.toCollection(HashSet::new));
    return chatRepository.findLatestChats(combined, sender, receiver);
  }
}
