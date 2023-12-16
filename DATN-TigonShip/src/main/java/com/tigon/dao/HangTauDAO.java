package com.tigon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.HangTau;

public interface HangTauDAO extends JpaRepository<HangTau, Integer>{
	@Query(value = "SELECT count(*) FROM HANGTAU ", nativeQuery = true)
	long countAllHangTau();
}
