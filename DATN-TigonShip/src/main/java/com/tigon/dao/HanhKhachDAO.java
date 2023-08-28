package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.HanhKhach;

public interface HanhKhachDAO extends JpaRepository<HanhKhach, Integer>{
	@Query(value = "SELECT DISTINCT a FROM HANHKHACH a WHERE a.QUYEN IN ('ADMIN', 'STAF')",nativeQuery = true)
	List<HanhKhach> getAdministrators();
	
	@Query(value = "SELECT * FROM HANHKHACH o WHERE o.EMAIL=?1",nativeQuery = true)
	HanhKhach findIdByEmailOrPhone(String username);
}
