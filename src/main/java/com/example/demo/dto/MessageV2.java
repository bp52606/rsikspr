package com.example.demo.dto;

import jakarta.persistence.Entity;

public class MessageV2 extends Message {

    private String to;
    private String displayName;

    private Content content;


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Content getContentV2() {
        return this.content;
    }

    public void setContentV2(Content content) {
        this.content = content;
    }
}
