package com.tigon.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tigon.model.DatVe;

public interface DatVeDAO extends JpaRepository<DatVe, Integer>{
	
	@Query(value = "SELECT TOP 1 * FROM datve where idtaikhoan=? ORDER BY NGAYDAT DESC", nativeQuery = true)
    DatVe getNgayDatMoiNhat(Integer idtaikhoan);

	@Query(value = "SELECT TOP 1 * from DatVe ORDER BY MADATVE DESC", nativeQuery = true)
    DatVe FINDIDMAX();
	
	@Query(value = "SELECT * FROM DatVe WHERE idtaikhoan = ?", nativeQuery = true)
    List<DatVe> ListDatVeByIdTaiKhoan(Integer idtaikhoan); // lấy tất cả thông tin đặt vé của 1 hành khách
	
	@Query(value = "SELECT hd.MAHD, dv.NGAYDAT, NGAYDI, dv.MADATVE, NGAYVE, hd.TRANGTHAI, hk.HOVATEN, hk.sdt, dv.SOGHE, ltc.GIOXUATPHAT, t.TENTAU, tuyen.TENTUYEN "
	        + "FROM HOADON hd "
	        + "INNER JOIN DATVE dv ON dv.MADATVE = hd.MADATVE "
	        + "INNER JOIN TAIKHOAN hk ON hk.IDTAIKHOAN = dv.IDTAIKHOAN "
	        + "INNER JOIN LICHTAUCHAY ltc ON ltc.IDLICHTAU = dv.IDLICHTAU "
	        + "INNER JOIN TAU t ON t.IDTAU = ltc.IDTAU "
	        + "INNER JOIN TUYEN tuyen ON tuyen.IDTUYEN = ltc.IDTUYEN "
	        + "WHERE tuyen.IDTUYEN  = :id AND CAST(NGAYDI AS DATE) = :ngay AND hd.TRANGTHAI LIKE N'%Đã thanh toán%'", nativeQuery = true)
	List<Object> thongTinDatVe(@Param("id") Integer id, @Param("ngay") Date ngay);

	 
}
