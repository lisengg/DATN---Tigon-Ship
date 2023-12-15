package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tigon.model.TaiKhoan;

public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, Integer> {
	@Query(value = "SELECT DISTINCT a FROM TAIKHOAN a WHERE a.VAITRO IN ('ADMIN', 'STAFF')", nativeQuery = true)
	List<TaiKhoan> getAdministrators();

	@Query(value = "SELECT * FROM TAIKHOAN o WHERE o.EMAIL=?1", nativeQuery = true)
	TaiKhoan findIdByEmailOrPhone(String username);

	@Query(value = "SELECT * FROM TAIKHOAN WHERE EMAIL=?1", nativeQuery = true)
	TaiKhoan getAllEmail(String email);

	@Query(value = "UPDATE taikhoan SET hovaten=?1, sdt=?2, cccd=?3, diachi=?4 WHERE idtaikhoan=?5", nativeQuery = true)
	TaiKhoan updateTaiKhoan(String hovaten, String sdt, String cccd, String diachi, Integer id);
	
	@Query(value = "SELECT count(*) FROM TAIKHOAN WHERE VAITRO = 'KHACHHANG'", nativeQuery = true)
	long countByVaiTro(@Param("vaiTro") String vaiTro);
}
