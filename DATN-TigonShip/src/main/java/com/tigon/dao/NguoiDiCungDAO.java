package com.tigon.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.NguoiDiCung;

public interface NguoiDiCungDAO extends JpaRepository<NguoiDiCung, Integer>{
	@Query(value = "SELECT * FROM NGUOIDICUNG WHERE MADATVE=?1", nativeQuery = true)	
	List<NguoiDiCung> ListNguoiDiCungByiddatve(Integer id);
}
