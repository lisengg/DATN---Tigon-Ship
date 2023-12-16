package com.tigon.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DatGhe;

public interface DatGheDAO extends JpaRepository<DatGhe, Integer>{
	 @Query(value = "SELECT IDGHE FROM DATGHE WHERE THOIGIAN = ?1 and idtuyen=?2 and idtau=?3", nativeQuery = true)
	 List<Integer> findBookedSeats(Date ngayDi, Integer Idtuyen, Integer idtau);
	 
	 @Query(value = "SELECT COUNT(*) FROM DATGHE WHERE THOIGIAN = ?1 and idtau=?2 and idtuyen=?3", nativeQuery = true)
	 int countDatGheTimTuyen(Date ngayDi, Integer idtau, Integer Idtuyen);
	 
	 @Query(value = "SELECT * FROM DATGHE WHERE IDDATVE=?", nativeQuery = true)
	 List<DatGhe> layDanhSachGheTheoMaDatVe(Integer idMadatve);
	 
}
