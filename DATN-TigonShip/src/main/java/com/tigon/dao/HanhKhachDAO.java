package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.tigon.model.HanhKhach;

public interface HanhKhachDAO extends JpaRepository<HanhKhach, Integer> {
	@Query(value = "SELECT DISTINCT a FROM HANHKHACH a WHERE a.QUYEN IN ('ADMIN', 'STAFF')", nativeQuery = true)
	List<HanhKhach> getAdministrators();

	@Query(value = "SELECT * FROM HANHKHACH o WHERE o.EMAIL=?1", nativeQuery = true)
	HanhKhach findIdByEmailOrPhone(String username);

	@Query(value = "SELECT * FROM HANHKHACH WHERE EMAIL=?1", nativeQuery = true)
	HanhKhach getAllEmail(String email);

	@Query(value = "UPDATE hanhkhach SET hovaten=?1, sdt=?2, cccd=?3, diachi=?4 WHERE idhanhkhach=?5", nativeQuery = true)
	HanhKhach updateHanhKhach(String hovaten, String sdt, String cccd, String diachi, Integer id);
	
	@Query(value = "SELECT count(*) FROM HANHKHACH WHERE QUYEN = 'USER'", nativeQuery = true)
	List<Object> AllHK();
}