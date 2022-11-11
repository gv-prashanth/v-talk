package com.vadrin.vtalk.services.notifications;

import java.io.IOException;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KLGuestHouseNotificationService implements NotificationService {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  UserInfoRepository userInfoRepository;

  public void notify(String receiver) throws IOException {
    if (userInfoRepository.findById(receiver).isPresent()) {
      String[] formInfo = getFormId();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      headers.add("Cookie", "JSESSIONID="+formInfo[1]);
      MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
      map.add("task", "generateotp");
      map.add("username", userInfoRepository.findById(receiver).get().getPhone());
      map.add("process_id", formInfo[0]);
      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
      ResponseEntity<String> response = restTemplate.postForEntity("http://stateprotocol.kerala.gov.in/public_login",
          request, String.class);
      log.error("Response of KLGuestHouseNotificationService" + response.getBody());
      if(!response.getBody().contains("OTP sent to the provided Mobile Number"))
        throw new IOException();
    }
  }

  private String[] getFormId() throws IOException {
    Response resp = Jsoup.connect("http://stateprotocol.kerala.gov.in/public_login").method(Method.GET).execute();
    String processId = Jsoup.parse(resp.body()).select("input[name=process_id]").val();
    String jsessionId = resp.cookie("JSESSIONID");
    String[] toReturn = {processId, jsessionId};
    return toReturn;
  }

  @Override
  public int getPriority() {
    return 3;
  }

}
