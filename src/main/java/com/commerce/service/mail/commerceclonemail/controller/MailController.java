package com.commerce.service.mail.commerceclonemail.controller;

import com.commerce.service.mail.commerceclonemail.dao.EmailDetail;
import com.commerce.service.mail.commerceclonemail.service.MailService;
import com.commerce.service.mail.commerceclonemail.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Value("mail.from")
    private String mailFrom;

    @Autowired
    private MailService mailService;

    @Autowired
    private OTPService otpService;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDetail emailDetail) {

        try {
            if (emailDetail != null) {
                mailService.sendEmail(mailFrom, emailDetail.getEmail(), emailDetail.getBody(),
                        emailDetail.getSubject(), emailDetail.getAttachment(), emailDetail.getAttachmentName());
            }

            return "Success";
        } catch(Exception ex){
            System.out.println(ex);
            return "Error "+ex;
        }
    }

    @PostMapping("/sendotp")
    public ResponseEntity<String> sendOTP(@RequestBody EmailDetail emailDetail) {

        try {
            if (emailDetail != null) {
                otpService.sendOTP(mailFrom, emailDetail.getEmail(), emailDetail.getSubject());
            }

            return ResponseEntity.status(HttpStatus.OK).body("OTP sent");
        } catch(Exception ex){
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured");
        }
    }

}
