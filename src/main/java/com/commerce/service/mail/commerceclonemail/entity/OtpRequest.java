package com.commerce.service.mail.commerceclonemail.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Entity
//@Table(name = "otp_request")
@RedisHash(value = "otp_request")
public class OtpRequest implements Serializable {


//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    private Long id;

//    @Transient

    private static final long serialVersionUID = 1708925807375596799L;

    @TimeToLive
    public final static long expiration = 60L;

    private transient String subject;

    @Id
    private String email;

    private int retryAttempt;
    private String otpValue;
    private ZonedDateTime expiryTimestamp;

    private Boolean isActive;

    public OtpRequest(String subject, String email){
        this.subject = subject;
        this.email = email;
    }

    public OtpRequest(String email, int retryAttempt, String otpValue, ZonedDateTime expiryTimestamp,
                      boolean isActive) {
        this.email = email;
        this.retryAttempt = retryAttempt;
        this.otpValue = otpValue;
        this.expiryTimestamp = expiryTimestamp;
        this.isActive = isActive;
    }
}
