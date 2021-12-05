package com.vadrin.vtalk.services.notifications;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vadrin.vtalk.repositories.UserInfoRepository;

@Service
public class TNEPassNotificationService implements NotificationService {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  UserInfoRepository userInfoRepository;

  public void notify(String receiver) throws IOException {
    if (userInfoRepository.findById(receiver).isPresent()) {
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode req = mapper.createObjectNode();
      req.put("MobileNumber", userInfoRepository.findById(receiver).get().getPhone());
      JsonNode resp = restTemplate.postForObject("https://tnepass-api.tnega.org/otp/get", req, JsonNode.class);
      System.out.println(resp);
      if(!resp.get("statusCode").asText().equalsIgnoreCase("200"))
        throw new IOException();
    }
  }

  @Override
  public int getPriority() {
    return 2;
  }

}
