package com.vadrin.vtalk.services.notifications;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.cloud.Timestamp;
import com.vadrin.vtalk.models.UserInfo;
import com.vadrin.vtalk.repositories.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WBLabourNotificationService implements NotificationService {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  UserInfoRepository userInfoRepository;

  public void notify(String receiver) throws IOException, InterruptedException, ExecutionException {
    Optional<UserInfo> userInfo = userInfoRepository.findById(receiver);
	if (userInfo.isPresent()) {
      if(isRecentlyNotified(userInfo.get().getLastNotification())) {
    	log.info("WBLabourNotificationService Recently notified. Not going to spam. " + userInfo.get().getUsername());
        return;
      }
      String formIdValue = getFormId();
      Document doc = Jsoup.connect("https://wblabour.gov.in/system/ajax").data("form_id", "otp_login_form").data("mobile", userInfo.get().getPhone().toString()).data("form_build_id", formIdValue).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE).ignoreHttpErrors(true).validateTLSCertificates(false).post();
      log.error("Response of WBLabourNotificationService" + doc.toString());
      UserInfo toSave = userInfo.get();
      toSave.setLastNotification(Timestamp.now());
      userInfoRepository.save(toSave);
    }
  }

  private String getFormId() throws IOException {
    Document doc = Jsoup.connect("https://wblabour.gov.in/enlogin").validateTLSCertificates(false).get();
    String toReturn = doc.select("input[name=form_build_id]").val();
    return toReturn;
  }

  @Override
  public int getPriority() {
    return 2;
  }

}
