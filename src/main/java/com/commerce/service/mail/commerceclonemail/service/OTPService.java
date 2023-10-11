package com.commerce.service.mail.commerceclonemail.service;

import javax.mail.MessagingException;

public interface OTPService {

    public void sendOTP(String from, String to, String subject) throws MessagingException;
}
