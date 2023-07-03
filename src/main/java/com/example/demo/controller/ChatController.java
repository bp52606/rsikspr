package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.ConversationService;
import com.example.demo.service.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

            if(cnt.equals("Bye")){
                closeConversation(conversation);
            }

        } else if(messageNode.has("text")){

            content = messageNode.get("text").asText();

        } else {
            return ResponseEntity.badRequest().body("Invalid message format");
        }

        message.setContent(content);

        conversation.setInputMessage(message);

        // nadi odgovor ako je definiran u json datoteci
        reply = conversationService.generateReply(message.getContent());
        Message messageReply = new Message();
        messageReply.setSender("Chatbot");
        messageReply.setContent(reply);
        messageReply.setConversationId(chatId);
        messageReply.setDateTime(LocalDateTime.now());

        conversation.setOutputMessage(reply);
        conversation.setSender(message.getSender());

        Random random = new Random();
        message.setId(random.nextLong());
        messageService.saveMessage(message);
        messageReply.setId(random.nextLong());
        messageService.saveMessage(messageReply);
        conversationService.saveConversation(conversation);

        return ResponseEntity.status(HttpStatus.CREATED).body(reply);

    }

    @GetMapping("/chats/{chatId}/myChat")
    public List<String> displayMessagesInConversation(@PathVariable("chatId") Long chatId){
        return messageService.getMessagesFromConversation(chatId).stream().map(a->a.getContent()).collect(Collectors.toList());
    }

    @GetMapping("/chats/{chatId}")
    public ResponseEntity<?> displayConversationsFromUser(@PathVariable("chatId") Long chatId){
        return ResponseEntity.ok().body(conversationService.getConversationsByConversationId(chatId));
    }


    @GetMapping("/chats")
    public ResponseEntity<?> displayConversations(){
        return ResponseEntity.ok().body(conversationService.getAllConversations());
    }


    @PostMapping("/chats/delete")
    public ResponseEntity<?> deleteConversation(@RequestBody Conversation conversation){
        return ResponseEntity.ok().body(conversationService.deleteConversation(conversation.getId()));
    }

    @PostMapping("/chats/openConversations")
    public ResponseEntity<?> listOpenConversations(){
        return ResponseEntity.ok().body(conversationService.getOpenConversations());
    }

    @PostMapping("/chats/close")
    public ResponseEntity<?> closeConversation(@RequestBody Conversation conversation){
        return ResponseEntity.ok().body(conversationService.closeConversation(conversation.getId()));
    }

}
