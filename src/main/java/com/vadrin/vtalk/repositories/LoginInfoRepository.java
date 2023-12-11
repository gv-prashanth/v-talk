package com.vadrin.vtalk.repositories;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.vadrin.vtalk.models.LoginInfo;

@Service
public class LoginInfoRepository {

  @Autowired
  Firestore firestore;

  public void save(LoginInfo loginInfo) throws InterruptedException, ExecutionException {
    this.firestore.collection("LoginInfoRepository").document().create(loginInfo).get();
  }
  
}
