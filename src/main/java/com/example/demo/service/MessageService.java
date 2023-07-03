package com.example.demo.service;

import com.example.demo.dto.Conversation;
import com.example.demo.dto.Message;
import com.example.demo.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;


    public void saveMessage(Message message) {
        messageRepository.save(message);

    }

    public List<Message> getMessagesFromConversation(Long chatId){
        return messageRepository.findByConversationId(chatId);
    }
}
