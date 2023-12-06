package com.tigon.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.GiaVe;
import com.tigon.model.TaiKhoan;

public interface GiaVeDAO extends JpaRepository<GiaVe, Integer>{

	@Query(value = "SELECT * FROM GIAVE WHERE IDTUYEN=?1 AND IDLOAIVE=?2", nativeQuery = true)
	GiaVe findByIdTuyenIdLoaiVe(Integer tuyen,Integer loaive);
	
}
