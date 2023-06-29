package com.example.demo.service;

import com.example.demo.dto.Conversation;
import com.example.demo.dto.Message;
import com.example.demo.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService  {

    private final ConversationRepository conversationRepository;

    private final BillingService billingService;

    private final MessageService messageService;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, BillingService billingService, MessageService messageService) {
        this.conversationRepository = conversationRepository;
        this.billingService = billingService;
        this.messageService = messageService;
    }

    public void saveConversation(Conversation conversation) {
        conversationRepository.save(conversation);
    }

    public List<Conversation> getConversationsByConversationId(Long conversationId) {
        return conversationRepository.findAllById(Collections.singleton(conversationId));
    }

    // creates conversation, returns its id
    public Conversation createConversation(Long chatId) {

        Conversation conversation = new Conversation();
        conversation.setId(chatId);
        conversation.setStarted(LocalDateTime.now());
        return conversation;

    }

    public void closeConversation(Long conversationId){

        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        Conversation conversation = null;
        if(conversationOptional.isPresent()){
            conversation = conversationOptional.get();
        }
        
        if(conversation!=null){

            conversation.setEnded(LocalDateTime.now());
            conversationRepository.save(conversation);

            billingService.calculateConversationBill(messageService.getMessagesFromConversation(conversation));

        }

    }

    public List<Conversation> getAllConversations(){
        return  conversationRepository.findAll();
    }

//    public List<Conversation> getOpenConversations(){
//
//    }

    public void deleteConversation(Long conversationId){

    }

//    public List<Message> getConversationHistory(Long conversationId){
//
//    }






}
