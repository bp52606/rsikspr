package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.ConversationService;
import com.example.demo.service.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@Component
@RequiredArgsConstructor
public class ChatController {

    private final ConversationService conversationService;
    private final MessageService messageService;


    @PostMapping("/chats/{chatId}/myChat")
    public ResponseEntity<String> chatter(@PathVariable("chatId") Long chatId,
                                          @RequestBody JsonNode messageNode) {

        List<Conversation> chatsById = conversationService.getConversationsByConversationId(chatId);

        String reply;
        Conversation conversation;
        if (chatsById.isEmpty()) {

            conversation = conversationService.createConversation(chatId);

        } else {

            conversation = chatsById.get(chatsById.size() - 1);

        }

        Message message = new Message();
        message.setConversationId(conversation.getId());

        if(messageNode.has("from")) {
            String sender = messageNode.get("from").asText();
            message.setSender(sender);

        } else {
            return ResponseEntity.badRequest().body("Invalid message format");
        }

        String to;

        if(messageNode.has("to")){
            to = messageNode.get("to").asText();
        } else {
            return ResponseEntity.badRequest().body("Invalid message format");
        }

        String content;
        if(messageNode.has("content")) {

            Content cnt = new Content();

            if(messageNode.get("content").has("text")){

                content = messageNode.get("content").get("text").asText();
                cnt.setText(messageNode.get("content").get("text").asText());

            } else {

                return ResponseEntity.badRequest().body("Invalid message format");
            }

            if(messageNode.has("displayName")){

            } else {

                return ResponseEntity.badRequest().body("Invalid message format");
            }

        } else if(messageNode.has("text")){

            content = messageNode.get("text").asText();

        } else {
            return ResponseEntity.badRequest().body("Invalid message format");
        }

        message.setContent(content);

        conversation.setInputMessage(message);

        // nadi odgovor ako je definiran u json datoteci
        reply = generateReply(message.getContent());

        conversation.setOutputMessage(reply);
        conversation.setSender(message.getSender());

        message.setId(1L);
        messageService.saveMessage(message);
        conversationService.saveConversation(conversation);


        return ResponseEntity.status(HttpStatus.CREATED).body(reply);

    }



    @GetMapping("/chats/{chatId}/myChat")
    public List<Message> displayMessagesInConversation(Conversation conversation){
        return messageService.getMessagesFromConversation(conversation);
    }

    @GetMapping("/chats/{chatId}")
    public List<Conversation> displayConversationsFromUser(Conversation conversation){
        return conversationService.getConversationsBySender(conversation.getSender());
    }


    @GetMapping("/chats")
    public List<Conversation> displayConversations(){
        return conversationService.getAllConversations();
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
