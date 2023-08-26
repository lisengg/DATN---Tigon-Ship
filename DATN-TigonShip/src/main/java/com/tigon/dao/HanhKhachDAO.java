package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.HanhKhach;

public interface HanhKhachDAO extends JpaRepository<HanhKhach, String>{
	@Query("SELECT DISTINCT ar.HANHKHACH FROM HANHKHACH ar WHERE ar.QUYEN IN ('ADMIN', 'STAF')")
	List<HanhKhach> getAdministrators();
}
