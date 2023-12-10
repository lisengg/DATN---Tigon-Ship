package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DatGhe;

public interface DatGheDAO extends JpaRepository<DatGhe, Integer>{
	 @Query(value = "SELECT IDGHE FROM DATGHE", nativeQuery = true)
	 List<Integer> findBookedSeats();
	 
	 @Query(value = "SELECT * FROM DATGHE WHERE IDDATVE=?", nativeQuery = true)
	 List<DatGhe> layDanhSachGheTheoMaDatVe(Integer idMadatve);
}
