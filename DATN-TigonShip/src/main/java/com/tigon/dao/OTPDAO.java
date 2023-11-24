package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.OTP;

public interface OTPDAO extends JpaRepository<OTP, Integer>{
	@Query(value = "Select * From OTP Where MAOTP=?1", nativeQuery = true)
	  List<OTP> findByMaOTP(String maOTP);
}
