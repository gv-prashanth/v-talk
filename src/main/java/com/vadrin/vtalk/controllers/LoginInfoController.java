package com.vadrin.vtalk.controllers;

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

@RestController
@RequestMapping("/v2")
public class LoginInfoController {

  @Autowired
  LoginInfoService loginInfoService;

  @PostMapping("/login")
  public void postLoginInfo(@RequestBody JsonNode loginInfoDTO, HttpServletRequest request) {
    loginInfoService.save(request, loginInfoDTO);
  }

  @GetMapping("/logininfos")
  public List<LoginInfo> getLoginInfo() {
    return loginInfoService.getLoginInfos();
  }
}
