package com.tigon.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.GiaVe;
import com.tigon.model.TaiKhoan;

public interface GiaVeDAO extends JpaRepository<GiaVe, Integer>{
	
	@Query(value = "SELECT * FROM GIAVE WHERE IDTUYEN=?1 AND IDLOAIHK=?2 AND IDLOAIVE=?3", nativeQuery = true)
	GiaVe findByIdTuyenIdLoaiVeTongTien(Integer tuyen,Integer loaihk,Integer loaive);

	@Query(value = "SELECT * FROM GIAVE WHERE IDTUYEN=?1 AND IDLOAIVE=?2 AND IDLOAIHK= 1", nativeQuery = true)
	GiaVe findByIdTuyenIdLoaiVe(Integer tuyen,Integer loaive);
	
	@Query(value = "SELECT * FROM GIAVE WHERE IDTUYEN=?1 AND IDLOAIVE=?2 AND IDLOAIHK= 2", nativeQuery = true)
	GiaVe findByIdTuyenIdLoaiVeTreEm(Integer tuyen,Integer loaive);
	
}
