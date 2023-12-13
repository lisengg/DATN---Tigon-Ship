package com.tigon.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.tigon.model.LichTauChay;

public interface LichTauChayDAO extends JpaRepository<LichTauChay, Integer>{
	@Query(value = "SELECT * FROM LICHTAUCHAY WHERE IDTUYEN = ?1", nativeQuery = true)
	LichTauChay findByLichTau(Integer IDTUYEN);
	
	@Query(value = "SELECT count(*) FROM LICHTAUCHAY ", nativeQuery = true)
	long countAllLichTau();
	
	
}
