package com.tigon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.tigon.model.HanhKhach;

public interface HanhKhachDAO extends JpaRepository<HanhKhach, Integer>{
	@Query(value = "SELECT TOP 1 * from HANHKHACH ORDER BY IDHANHKHACH DESC", nativeQuery = true)
    HanhKhach FINDIDHKMAX();
}
