package com.example.demo.dto;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {

    /*
    The text contained in a message
     */

    @Column
    private String content;

    /*
    Unique ID of a message
     */

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /*
    The sender of the message
     */

    @Column
    private String sender;

    /*
    The date and time when the message was sent
     */

    @Column
    private LocalDateTime dateTime;

    @Column
    private Long conversationId;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
}
