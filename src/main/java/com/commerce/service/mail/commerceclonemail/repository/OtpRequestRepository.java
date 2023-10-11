package com.commerce.service.mail.commerceclonemail.repository;

import com.commerce.service.mail.commerceclonemail.entity.OtpRequest;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface OtpRequestRepository extends CrudRepository<OtpRequest, String> {



}
