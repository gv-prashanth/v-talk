package com.vadrin.vtalk.services;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.vadrin.vtalk.repositories.UserInfoRepository;

@Service
public class NotificationService {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  UserInfoRepository userInfoRepository;

  /*
   * public void notify(String receiver) {
   * userInfoRepository.findById(receiver).ifPresent(info -> { try { ObjectMapper
   * mapper = new ObjectMapper(); ObjectNode req = mapper.createObjectNode();
   * req.put("MobileNumber", info.getPhone()); JsonNode resp =
   * restTemplate.postForObject("https://tnepass-api.tnega.org/otp/get", req,
   * JsonNode.class); System.out.println(resp); } catch (Exception e) {
   * e.printStackTrace(); } }); }
   */

  public void notify(String receiver) {
    userInfoRepository.findById(receiver).ifPresent(info -> {
      try {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("form_id", "otp_login_form");
        map.add("mobile", info.getPhone());
        map.add("form_build_id", getFormId());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://www.wblabour.gov.in/system/ajax", request,
            String.class);
        System.out.println(response);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
  
  private String getFormId() throws IOException {
    Document doc = Jsoup.connect("https://wblabour.gov.in/enlogin").get();
    String toReturn = doc.select("input[name=form_build_id]").val();
    return toReturn;
  }

}
