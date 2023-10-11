package com.commerce.service.mail.commerceclonemail.service;

import javax.mail.MessagingException;

public interface MailService {

    public void sendEmail(String from, String to, String body, String subject, byte[] attachment, String attachmentName)
            throws MessagingException;
}
