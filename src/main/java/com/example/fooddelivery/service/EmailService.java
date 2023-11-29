package com.example.fooddelivery.service;

import lombok.Builder;
import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

@Service
@Data
@Builder
public class EmailService {


    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;



    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendHtmlMessage(String to, String subject, String templateName, Context context) {
        String htmlContent = templateEngine.process(templateName, context);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(htmlContent);
        message.setFrom("your@example.com"); // Set your sender email address here
        message.setSentDate(new Date());

        mailSender.send(message);
    }
}
