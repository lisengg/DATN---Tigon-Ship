package com.tigon.dao;


import org.springframework.data.jpa.repository.JpaRepository;


import com.tigon.model.Tuyen;

public interface TuyenDAO extends JpaRepository<Tuyen, Integer>{
//	@Query(value = "SELECT * FROM Tuyen WHERE idtuyen = ?", nativeQuery = true)
   
}
