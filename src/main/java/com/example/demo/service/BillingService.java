package com.example.demo.service;

import com.example.demo.dto.Bill;
import com.example.demo.dto.Conversation;
import com.example.demo.dto.Message;
import com.example.demo.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    // price of messages in euros
    private static final double price = 0.15;
    private final ConversationRepository conversationRepository;



    private final EmailService emailService = new EmailService("smtp.gmail.com",  "barbaradirectioner90@gmail.com", "BarbaraAdore1d", 587);

    public BillingService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }


    public double calculateConversationBill(List<Message> messages){

        return messages.size()*price;

    }

    public Bill createABill(String user, double price, Long conversationId){
        Bill bill = new Bill();
        bill.setUser(user);
        bill.setConversationId(conversationId);
        bill.setCost(price);
        return bill;
    }

    public void sendBillToUser(Bill bill) throws MessagingException {

        StringBuilder sb = new StringBuilder();
        Optional<Conversation> optConversation = conversationRepository.findById(bill.getConversationId());

        if(optConversation.isPresent()){

            Conversation conversation = optConversation.get();
            sb.append("Dear ").append(bill.getUser()).append("\n\n");
            sb.append("Your bill for the conversation you had between ").append(conversation.getStarted()).append(" and ").append(conversation.getEnded())
                    .append("contains an mount of ").append(bill.getCost()).append(" EUR and must be paid within the next 10 days");

            emailService.sendEmail(emailService.getUsername(), "Bill for texting", sb.toString());

        }


    }
}
