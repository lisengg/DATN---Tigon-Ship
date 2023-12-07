package com.tigon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tigon.dao.OTPDAO;
import com.tigon.model.OTP;
import com.tigon.service.OTPService;
@Service
public class OTPServiceImpl implements OTPService{

	@Autowired
	OTPDAO dao;
	
	@Override
	public List<OTP> findByMaOTP(String maOTP) {
		// TODO Auto-generated method stub
		return dao.findByMaOTP(maOTP);
	}

	@Override
	public List<OTP> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public OTP findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

}
