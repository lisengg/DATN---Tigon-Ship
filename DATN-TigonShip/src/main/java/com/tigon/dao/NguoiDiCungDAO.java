package com.tigon.dao;


import org.springframework.data.jpa.repository.JpaRepository;


import com.tigon.model.NguoiDiCung;

public interface NguoiDiCungDAO extends JpaRepository<NguoiDiCung, Integer>{
	//@Query("SELECT p FROM NGUOIDICUNG p WHERE p.DATVE.MADATVE=?1")	
	//List<NguoiDiCung> ListNguoiDiCungByiddatve(Integer id);

//	List<NguoiDiCung> findByDATVEId(Integer cid);
}
