package com.scm.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailController 
{
	@Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email, String token ) throws MessagingException, UnsupportedEncodingException
    {
        System.out.println(token);
        //Code for Sending OTP to the respected user email id.
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("scmsupport@support.com", "SCMXPertLite Support");
        helper.setTo(email);
        String subject = "Password Changed Confirmation From SCMXPertLite Support";
        String content = "<html>"
                			+"<body>"
                				+ "<p> hello world</p>"
                				+ "<a href='http://127.0.0.1:3000/ResetPassword.html?"+token+"'> click here</a>"
                			+ "</body>"
                		+ "</html>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message); 
    }
}
