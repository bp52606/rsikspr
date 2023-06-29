package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Conversation {

    /*
    Unique ID of a conversation (based on a sender)
     */

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "sender", nullable = false, length = 50)
    private String sender;

//    private List<Message> messages;

    /*
    The date and time when the conversation started
     */
    @Column
    private LocalDateTime started;

    /*
    The date and time when the conversation ended
     */

    @Column
    private LocalDateTime ended;

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getEnded() {
        return ended;
    }

    public void setEnded(LocalDateTime ended) {
        this.ended = ended;
    }


    public Long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public void setInputMessage(Message message) {
    }

    public void setOutputMessage(String reply) {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
