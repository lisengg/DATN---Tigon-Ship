package com.tigon.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.Tau;

public interface TauDAO extends JpaRepository<Tau, Integer>{
	@Query(value = "SELECT count(*) FROM TAU ", nativeQuery = true)
	long countAllTau();
}
