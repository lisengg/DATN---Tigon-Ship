package com.tigon.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.tigon.model.OTP;

@Service
public interface OTPService {
	  List<OTP> findByMaOTP(String maOTP);
	  List<OTP> findAll();
	  OTP findById(Integer id);
}
