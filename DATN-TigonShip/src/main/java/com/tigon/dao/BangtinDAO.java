package com.tigon.dao;





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
	
}
