package com.example.demo.service;

import com.example.demo.dto.Conversation;
import com.example.demo.dto.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);

    }

    public List<Message> getMessagesFromConversation(Conversation conversation){
        return messageRepository.findByConversationId(conversation.getId());
    }
}
