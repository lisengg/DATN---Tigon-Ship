package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DatVe;

public interface DatVeDAO extends JpaRepository<DatVe, Integer>{
	//@Query(value = "SELECT * FROM DatVe WHERE DV.IDHANHKHACH = ?", nativeQuery = true)
	@Query(value = "SELECT * FROM DatVe WHERE idhanhkhach = ?", nativeQuery = true)
    List<DatVe> ListDatVeByidKhach(Integer idhanhkhach); // lấy tất cả thông tin đặt vé của 1 hành khách
    
	

}
