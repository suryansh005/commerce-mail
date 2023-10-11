package com.commerce.service.mail.commerceclonemail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService{

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void sendEmail(String from, String to, String body, String subject, byte[] attachment,
                          String attachmentName) throws MessagingException {

        if (attachment == null) {
            sendSimpleEmail(from, to, body, subject);
        } else {
            sendMailWithAttachments(from, to, body, subject, attachment, attachmentName);
        }
    }

    private void sendSimpleEmail(String from, String to, String body, String subject) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage, simpleMailMessage);
    }

    private void sendMailWithAttachments(String from, String to, String body, String subject,
                     byte[] attachment, String attachmentName) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        helper.addAttachment(attachmentName, new ByteArrayResource(attachment));
        javaMailSender.send(mimeMessage);
    }
}
