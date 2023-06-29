package com.example.demo.service;

import com.example.demo.dto.Bill;
import com.example.demo.dto.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    // price of messages in euros
    private static final double price = 0.15;


    public double calculateConversationBill(List<Message> messages){

        return messages.size()*price;

    }

    public Bill createABill(Long userId, double price, Long conversationId){
        Bill bill = new Bill();
        bill.setUserId(userId);
        bill.setConversationId(conversationId);
        bill.setCost(price);
        return bill;
    }

    public void sendBillToUser(Bill bill){

    }
}
