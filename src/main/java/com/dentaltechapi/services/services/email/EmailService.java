package com.dentaltechapi.services.services.email;

import com.dentaltechapi.model.entities.email.EmailModel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEMail(EmailModel email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hygor.henrique@live.com");
        message.setTo(email.getMailTo());
        message.setSubject(email.getMailSubject());
        message.setText(email.getMailBody());

        javaMailSender.send(message);
    }
}
