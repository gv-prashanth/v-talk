package com.vadrin.vtalk.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.vadrin.vtalk.models.Chat;

@Service
public class ChatRepository {

  @Autowired
  Firestore firestore;

  public List<Chat> findLatestChats(String sender, String receiver) throws InterruptedException, ExecutionException {
    CollectionReference collectionReference = this.firestore.collection("ChatRepository");
    ApiFuture<QuerySnapshot> queryFuture = collectionReference.whereIn("sender", Arrays.asList(receiver))
        .whereIn("receiver", Arrays.asList(sender)).orderBy("createdOn", Direction.DESCENDING).limit(1).get();
    List<QueryDocumentSnapshot> queryDocuments = queryFuture.get().getDocuments();
    Optional<Chat> receiverLastSentChat = queryDocuments.stream().map(x -> x.toObject(Chat.class)).findAny();
    if (receiverLastSentChat.isPresent()) {
      ApiFuture<QuerySnapshot> queryFuture2 = collectionReference.whereIn("sender", Arrays.asList(sender))
          .whereIn("receiver", Arrays.asList(receiver))
          .whereLessThanOrEqualTo("createdOn", receiverLastSentChat.get().getCreatedOn())
          .orderBy("createdOn", Direction.DESCENDING).limit(1).get();
      List<QueryDocumentSnapshot> queryDocuments2 = queryFuture2.get().getDocuments();
      Optional<Chat> chat = queryDocuments2.stream().map(x -> x.toObject(Chat.class)).findAny();
      if (chat.isPresent())
        return findLatestChatsAfterLastPull(sender, receiver, chat.get().getCreatedOn());
      else
        return findLatestChatsAfterLastPull(sender, receiver, receiverLastSentChat.get().getCreatedOn());
    }
    return findLatestChatsAfterLastPull(sender, receiver, Timestamp.now());
  }

  public List<Chat> findLatestChatsAfterLastPull(String sender, String receiver, Timestamp timeFromWhichToPull)
      throws InterruptedException, ExecutionException {
    CollectionReference collectionReference = this.firestore.collection("ChatRepository");
    ApiFuture<QuerySnapshot> queryFuture = collectionReference.whereIn("sender", Arrays.asList(sender, receiver))
        .whereIn("receiver", Arrays.asList(sender, receiver))
        .whereGreaterThanOrEqualTo("createdOn", timeFromWhichToPull).orderBy("createdOn", Direction.ASCENDING).get();
    List<QueryDocumentSnapshot> queryDocuments = queryFuture.get().getDocuments();
    List<Chat> toReturn = queryDocuments.stream().map(x -> x.toObject(Chat.class)).collect(Collectors.toList());
    return toReturn;
  }

  public void save(Chat buildWithChatDTO) throws InterruptedException, ExecutionException {
    this.firestore.collection("ChatRepository").document().create(buildWithChatDTO).get();
  }

}
