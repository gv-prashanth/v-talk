package com.vadrin.vtalk.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vadrin.vtalk.models.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

  @Query(nativeQuery = true, value = "select * from chats where (sender in ?1 or receiver in ?1) and created_on >= ((select created_on from chats where sender=?2 order by created_on desc limit 1) - interval '120 seconds') order by created_on asc")
  public List<Chat> findLatestChats(Set<String> combined, String sender);

}
