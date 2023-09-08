package com.tigon.dao;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.Tuyen;

public interface TuyenDAO extends JpaRepository<Tuyen, Integer>{
	@Query("SELECT t FROM Tuyen t WHERE t.TENTUYEN LIKE %?1%")

	List<Tuyen> findByTentuyenContaining(String keyword);






}
