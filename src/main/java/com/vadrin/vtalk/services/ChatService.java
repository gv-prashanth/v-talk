package com.vadrin.vtalk.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vadrin.vtalk.models.Attachment;
import com.vadrin.vtalk.models.Chat;
import com.vadrin.vtalk.models.ChatDTO;
import com.vadrin.vtalk.repositories.AttachmentRepository;
import com.vadrin.vtalk.repositories.ChatRepository;

@Service
public class ChatService {

  @Autowired
  ChatRepository chatRepository;

  @Autowired
  AttachmentRepository attachmentRepository;

  @Autowired
  ImageService imageService;
  
  @Autowired
  ChatBuilder chatBuilder;

  public void save(ChatDTO chatDTO) {
    String message = chatDTO.getMessage();
    if(chatDTO.getMessage().startsWith("data:image")) {
      chatDTO.setMessage(imageService.reduceImageSize(message));
    }
    Chat chat = chatRepository.save(chatBuilder.buildWithChatDTO(chatDTO));
    if(chat.getMessage().startsWith("data:image")) {
      Attachment attachment = new Attachment();
      attachment.setChatId(chat.getId());
      attachment.setData(message);
      attachment.setAttachmentMeta(chatDTO.getMessageMeta());
      attachmentRepository.save(attachment);
    }
  }

  public List<Chat> findBySenderReceiver(String sender, String receiver) {
    Set<String> combined = Stream.of(sender, receiver).collect(Collectors.toCollection(HashSet::new));
    return chatRepository.findLatestChats(combined, sender, receiver);
  }
}
