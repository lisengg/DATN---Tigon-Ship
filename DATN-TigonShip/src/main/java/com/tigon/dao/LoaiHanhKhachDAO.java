package com.tigon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.LoaiHanhKhach;

public interface LoaiHanhKhachDAO extends JpaRepository<LoaiHanhKhach, Integer>{

	@Query(value = "SELECT * FROM LOAIHK WHERE IDLOAIHK=1",nativeQuery = true)
	LoaiHanhKhach getIdNguoiLon();
	
}
