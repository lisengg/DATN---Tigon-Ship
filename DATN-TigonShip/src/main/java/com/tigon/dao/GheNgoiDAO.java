package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.tigon.model.GheNgoi;

public interface GheNgoiDAO extends JpaRepository<GheNgoi, Integer>{
    @Query(value = "SELECT * FROM ghengoi WHERE idtau = ?", nativeQuery = true)
    List<GheNgoi> gheNgoiByIdtau(Integer idtau); // lấy tất cả ghế ngồi qua id 1 táu
    
    @Query(value = "SELECT * FROM GHENGOI WHERE KHOANG = 1", nativeQuery = true)
	List<GheNgoi> findByghekhoangduoi();
    
    @Query(value = "SELECT * FROM GHENGOI WHERE KHOANG = 2", nativeQuery = true)
	List<GheNgoi> findByghekhoangtren();
    
    @Query(value = "SELECT * FROM GHENGOI WHERE TENGHE = ?", nativeQuery = true)
	GheNgoi findByghe(String TENGHE);
    
}
