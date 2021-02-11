package com.vadrin.vtalk.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vadrin.vtalk.models.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

  @Query(nativeQuery = true, value = "select * from chats where sender in ?1 and receiver in ?1 and created_on > (select created_on from chats where receiver=?3 and sender=?2 and created_on <= (select created_on from chats where receiver=?2 and sender=?3 order by created_on desc limit 1) order by created_on desc limit 1) order by created_on asc")
  public List<Chat> findLatestChats(Set<String> combined, String sender, String receiver);

}
