package com.vadrin.vtalk.repositories;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.vadrin.vtalk.models.UserInfo;

@Service
public class UserInfoRepository {
  
  @Autowired
  Firestore firestore;

  public Optional<UserInfo> findById(String username) throws InterruptedException, ExecutionException {
    ApiFuture<DocumentSnapshot> documentFuture = this.firestore.document("UserInfoRepository/"+username).get();
    UserInfo userInfo = documentFuture.get().toObject(UserInfo.class);
    if(userInfo == null)
      return Optional.empty();
    else
      return Optional.of(userInfo);
  }

  public void save(UserInfo toSave) throws InterruptedException, ExecutionException {
    this.firestore.document("UserInfoRepository/"+toSave.getUsername()).set(toSave).get();
  }

}
