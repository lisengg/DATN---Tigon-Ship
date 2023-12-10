package com.tigon.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DatGhe;

public interface DatGheDAO extends JpaRepository<DatGhe, Integer>{
	 @Query(value = "SELECT IDGHE FROM DATGHE WHERE THOIGIAN = :ngayDi", nativeQuery = true)
	 List<Integer> findBookedSeats(Date ngayDi);
	 
	 @Query(value = "SELECT COUNT(*) FROM DATGHE WHERE THOIGIAN = :ngayDi", nativeQuery = true)
	 int countDatGheByNgayDi(Date ngayDi);
}
