package com.commerce.service.mail.commerceclonemail.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetail {

    private String subject;
    private String email;
    private String body;
    private String attachmentName;
    private byte[] attachment;

}
