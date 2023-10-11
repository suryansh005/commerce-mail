package com.commerce.service.mail.commerceclonemail.service;

import com.commerce.service.mail.commerceclonemail.entity.OtpRequest;
import com.commerce.service.mail.commerceclonemail.repository.OtpRequestRepository;
import com.commerce.service.mail.commerceclonemail.utils.EmailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPServiceImpl implements OTPService{

    @Autowired
    private MailService mailService;

    @Autowired
    private OtpRequestRepository otpRequestRepository;

    @Override
    public void sendOTP(String from, String to, String subject) throws MessagingException {

        String otpValue = generateOTP();

        String body = EmailConstants.otpTemplate.replaceAll("\\{otp\\}", otpValue);
//        mailService.sendEmail(from, to, body,subject, null, null);

        Optional<OtpRequest> cachedOtpRequest = Optional.empty();

        try {
            cachedOtpRequest = otpRequestRepository.findById(to);
        } catch(Exception ex){
            System.out.println("Exception occured at sendOTP. Error "+ex);
        }

        OtpRequest otpRequest = cachedOtpRequest.isPresent() ? cachedOtpRequest.get() : null;

//        todo - Add a code at main app to check if retryattempt has been more than 3 times

        if(otpRequest != null){
//            otpRequest= new OtpRequest(to, cachedOtpRequest.get().getRetryAttempt()+1, otpValue,
//                    ZonedDateTime.of(LocalDateTime.now().plusMinutes(15), ZoneId.of("UTC")), true);
            otpRequest.setExpiryTimestamp(ZonedDateTime.of(LocalDateTime.now().plusMinutes(15), ZoneId.of("UTC")));
            otpRequest.setRetryAttempt(cachedOtpRequest.get().getRetryAttempt()+1);
            otpRequest.setOtpValue(otpValue);

        } else {
            otpRequest = new OtpRequest(to, 0, otpValue,
                    ZonedDateTime.of(LocalDateTime.now().plusMinutes(15), ZoneId.of("UTC")), true);
        }

        otpRequestRepository.save(otpRequest);
    }

    private String generateOTP(){
        Random random = new Random();
        int randomNumber = (int)(100000 + random.nextFloat() * 900000);
        return String.valueOf(randomNumber);
    }
}
