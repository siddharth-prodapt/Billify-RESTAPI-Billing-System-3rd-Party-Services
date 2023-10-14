package com.prodapt.billingsystem.email;

import com.prodapt.billingsystem.api.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class EmailServices {
    @Autowired
    private SMTPEmailSender smtpEmailSender;

    public void testEmail(String emailId) {

        MessageEmail email = new MessageEmail();

        email.setSubject("Registration Successful | SHIKSHAK SETU");

        email.setTo(Collections.singletonList(emailId));
        String message = "Dear ";

        email.setBody(message);
        email.setHtml(false);

        smtpEmailSender.sendMessage(email);
    }

}
