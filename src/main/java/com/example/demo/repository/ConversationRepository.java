package com.example.demo.repository;

import com.example.demo.dto.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


/**
 * Repository for conversations.
 */
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findBySender(String sender);

    @Override
    List<Conversation> findAllById(Iterable<Long> longs);

    List<Conversation> findByStartedNotNullAndEndedNull();

}
