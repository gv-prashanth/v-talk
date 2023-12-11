package com.vadrin.vtalk.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.Timestamp;
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

  public void save(ChatDTO chatDTO) throws InterruptedException, ExecutionException {
    String message = chatDTO.getMessage();
    if(chatDTO.getMessage().startsWith("data:image")) {
      chatDTO.setMessage(imageService.reduceImageSize(message));
    }
    chatRepository.save(chatBuilder.buildWithChatDTO(chatDTO));
    if(chatDTO.getMessage().startsWith("data:image")) {
      Attachment attachment = new Attachment();
      attachment.setData(message);
      attachment.setAttachmentMeta(chatDTO.getMessageMeta());
      attachmentRepository.save(attachment);
    }
  }

  public List<Chat> findBySenderReceiver(String sender, String receiver, Optional<String> lastPullChatId) throws InterruptedException, ExecutionException {
    if(lastPullChatId.isPresent())
    	return chatRepository.findLatestChatsAfterLastPull(sender, receiver, Timestamp.ofTimeSecondsAndNanos(Long.valueOf(lastPullChatId.get()), 0));
    else
    	return chatRepository.findLatestChats(sender, receiver);
  }
}
