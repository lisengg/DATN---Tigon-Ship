package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DatVe;

public interface DatVeDAO extends JpaRepository<DatVe, Integer>{
	
	@Query(value = "SELECT TOP 1 * FROM datve where idhanhkhach=? ORDER BY NGAYDAT DESC", nativeQuery = true)
    DatVe getNgayDatMoiNhat(Integer idhanhkhach);

	@Query(value = "SELECT * FROM DatVe WHERE idhanhkhach = ?", nativeQuery = true)
    List<DatVe> ListDatVeByidKhach(Integer idhanhkhach); // lấy tất cả thông tin đặt vé của 1 hành khách
}
