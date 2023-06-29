package com.example.demo.repository;

import com.example.demo.dto.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for messages.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    @Override
    List<Message> findAllById(Iterable<String> strings);

    List<Message> findByConversationId(Long conversationId);
}
