package com.vadrin.vtalk.repositories;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.vadrin.vtalk.models.Attachment;

@Service
public class AttachmentRepository {

  @Autowired
  Firestore firestore;
  
  public void save(Attachment attachment) throws InterruptedException, ExecutionException {
    this.firestore.collection("AttachmentRepository").document().create(attachment).get();
  }

}
