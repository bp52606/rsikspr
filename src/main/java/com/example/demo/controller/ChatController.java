package com.example.demo.controller;

import com.example.demo.dto.Conversation;
import com.example.demo.dto.Message;
import com.example.demo.service.ConversationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Component
@RequiredArgsConstructor
public class ChatController {

    private final ConversationService conversationService;


    @PostMapping("/chats/{chatId}/myChat")
    public ResponseEntity<String> chatter(@PathVariable("chatId") Long chatId,
                                          @RequestBody Message message) {

        List<Conversation> chatsById = conversationService.getConversationsByConversationId(chatId);

        String reply;
        Conversation conversation;
        if (chatsById.isEmpty()) {

            conversation = conversationService.createConversation(chatId);

        } else {

            conversation = chatsById.get(chatsById.size() - 1);

        }

        conversation.setInputMessage(message);

        // nadi odgovor ako je definiran u json datoteci
        reply = generateReply(message.getContent());

        conversation.setOutputMessage(reply);
        conversation.setSender(message.getSender());
        conversationService.saveConversation(conversation);

        return ResponseEntity.status(HttpStatus.CREATED).body(reply);

    }

    private String generateReply(String message) {

        try (InputStream inputStream = ChatController.class.getResourceAsStream("/answers.json")) {
            byte[] jsonData = inputStream.readAllBytes();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);

            JsonNode answersNode = jsonNode.get("answers");
            for (JsonNode answerNode : answersNode) {
                String answerMessage = answerNode.get("message").asText();
                String reply = answerNode.get("reply").asText();
                if (message.equalsIgnoreCase(answerMessage)) {
                    return reply;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "Your input hasn't got a defined answer.";

    }


}
