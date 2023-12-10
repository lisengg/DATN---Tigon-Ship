package com.tigon.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.Tuyen;

public interface TuyenDAO extends JpaRepository<Tuyen, Integer>{
	@Query(value = "SELECT * FROM TUYEN WHERE TENTUYEN = ?1", nativeQuery = true)
    Tuyen findByTuyen(String TENTUYEN);
	
	@Query(value = "SELECT * FROM TUYEN WHERE TENTUYEN = ?1", nativeQuery = true)
    List<Tuyen> findListByTuyen(String TENTUYEN);
}
