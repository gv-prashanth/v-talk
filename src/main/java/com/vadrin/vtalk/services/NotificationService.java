package com.vadrin.vtalk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vadrin.vtalk.repositories.UserInfoRepository;

@Service
public class NotificationService {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  UserInfoRepository userInfoRepository;

  public void notify(String receiver) {
    userInfoRepository.findById(receiver).ifPresent(info -> {
      try {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode req = mapper.createObjectNode();
        req.put("MobileNumber", info.getPhone());
        restTemplate.postForObject("https://tnepass-api.tnega.org/otp/get", req, JsonNode.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

}
