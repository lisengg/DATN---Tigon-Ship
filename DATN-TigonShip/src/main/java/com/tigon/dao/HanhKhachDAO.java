package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DatVe;
import com.tigon.model.HanhKhach;

public interface HanhKhachDAO extends JpaRepository<HanhKhach, Integer>{
    @Query(value = "SELECT * FROM DatVe WHERE idhanhkhach = ?", nativeQuery = true)
    List<DatVe> ListDatVeByidKhach(Integer idhanhkhach); // lấy tất cả thông tin đặt vé của 1 hành khách

    
}
