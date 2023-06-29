package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    private final String host;
    private final String username;
    private final String password;

    private final int port;

    public EmailService(String host, String username, String password, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public void sendEmail(String goalMail, String subject, String content) throws MessagingException {

        Properties properties = createEmailProperties();

        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }

        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(goalMail));
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);

    }

    private Properties createEmailProperties() {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;

    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public int getPort() {
        return port;
    }
}
