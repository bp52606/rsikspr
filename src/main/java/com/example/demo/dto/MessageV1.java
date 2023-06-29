package com.example.demo.dto;

import jakarta.persistence.Entity;

public class MessageV1 extends Message {

        private String to;


        public String getTo() {
                return to;
        }

        public void setTo(String to) {
                this.to = to;
        }

}
