package com.tigon.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tigon.model.GheNgoi;

public interface GheNgoiDAO extends JpaRepository<GheNgoi, Integer>{
    @Query(value = "SELECT * FROM ghengoi WHERE idtau = ?", nativeQuery = true)
    List<GheNgoi> gheNgoiByIdtau(Integer idtau); // lấy tất cả ghế ngồi qua id 1 táu
    
    @Query(value = "SELECT * FROM GHENGOI WHERE KHOANG = 1 and idtau = ?", nativeQuery = true)
	List<GheNgoi> findByghekhoangduoi(Integer idtau);
    
    @Query(value = "SELECT * FROM GHENGOI WHERE KHOANG = 2 and idtau = ?", nativeQuery = true)
	List<GheNgoi> findByghekhoangtren(Integer idtau);
    
    @Query(value = "SELECT * FROM GHENGOI WHERE TENGHE = ?", nativeQuery = true)
	GheNgoi findByghe(String TENGHE);
    
    /* ghế ngồi admin của cơ */
    @Query(value = "SELECT * FROM GHENGOI WHERE  KHOANG = 1 AND idtau = :idtau ", nativeQuery = true)
    List<GheNgoi> gheNgoi1(@Param("idtau") Integer idtau); 
    
    @Query(value = "SELECT * FROM GHENGOI WHERE  KHOANG = 2 AND idtau = :idtau ", nativeQuery = true)
    List<GheNgoi> gheNgoi2(@Param("idtau") Integer idtau); 

    @Query(value = "SELECT * FROM GHENGOI WHERE IDGHE = :idghe",nativeQuery = true)
	GheNgoi gheNgoiChiTiet(@Param("idghe") Integer idghe);

    @Transactional/* Update  trạng thái ghế ngồi*/
	@Modifying
	@Query(value = "UPDATE GHENGOI SET TRANGTHAI = :trangthai WHERE IDGHE = :idghe", nativeQuery = true)
	void updateGheNgoi(@Param("trangthai") String trangthai, @Param("idghe") Integer idghe);
}
