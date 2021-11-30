package com.vadrin.vtalk.controllers;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.vadrin.vtalk.models.LoginInfo;
import com.vadrin.vtalk.services.LoginInfoService;
import com.vadrin.vtalk.services.notifications.NotificationService;

@RestController
@RequestMapping("/v2")
public class LoginInfoController {

  @Autowired
  LoginInfoService loginInfoService;

  @Autowired
  List<NotificationService> notificationServices;

  @PostMapping("/login")
  public void postLoginInfo(HttpServletRequest request, @RequestBody JsonNode loginInfoDTO) {
    loginInfoService.save(request, loginInfoDTO);
    Iterator<NotificationService> iterator = notificationServices.stream()
        .sorted((a, b) -> Integer.compare(a.getPriority(), b.getPriority())).iterator();
    while (iterator.hasNext()) {
      try {
        iterator.next().notify(loginInfoDTO.get("receiver").asText());
        break;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @GetMapping("/logininfos")
  public List<LoginInfo> getLoginInfo() {
    return loginInfoService.getLoginInfos();
  }
}
