package com.tigon.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.tigon.model.Bangtin;

public interface BangtinDAO extends JpaRepository<Bangtin, Integer> {
	 @Transactional
	    @Modifying
	  @Query(value = "DELETE FROM BANGTIN WHERE TIEUDE = ?1", nativeQuery =
	  true) 
	 void  deletetieu_de(String TIEUDE);
	 @Query(value = "Select * FROM BANGTIN WHERE TIEUDE = ?1", nativeQuery =
			  true) 
			 public Bangtin findBytieude(String TIEUDE);
	 
	 @Query(value = "SELECT TOP 5 * FROM BANGTIN ORDER BY NGAYDANG DESC;", nativeQuery =true) 
	List<Bangtin>  top5();
	
}
