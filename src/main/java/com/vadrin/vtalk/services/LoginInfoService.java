package com.vadrin.vtalk.services;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.cloud.Timestamp;
import com.vadrin.vtalk.models.LoginInfo;
import com.vadrin.vtalk.repositories.LoginInfoRepository;

@Service
public class LoginInfoService {

  @Autowired
  LoginInfoRepository loginInfoRepository;
  
  @Autowired
  ClientInfoService clientInfoService;

  public void save(HttpServletRequest request, JsonNode loginInfoDTO) throws InterruptedException, ExecutionException {
    LoginInfo loginInfo = new LoginInfo();
    loginInfo.setUseragent(clientInfoService.getUserAgent(request));
    loginInfo.setIpaddress(clientInfoService.getClientIpAddr(request));
    loginInfo.setOsinfo(clientInfoService.getClientOS(request));
    loginInfo.setGeoinfo(null);
    loginInfo.setLogintime(Timestamp.now());
    loginInfo.setSender(loginInfoDTO.get("sender").asText());
    loginInfoRepository.save(loginInfo);
  }

}
