package com.tigon.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tigon.model.DatVe;
import com.tigon.model.HoaDon;

public interface DatVeDAO extends JpaRepository<DatVe, Integer> {

	@Query(value = "SELECT TOP 1 * FROM datve where idtaikhoan=? ORDER BY NGAYDAT DESC", nativeQuery = true)
	DatVe getNgayDatMoiNhat(Integer idtaikhoan);

	@Query(value = "SELECT TOP 1 * from DatVe ORDER BY MADATVE DESC", nativeQuery = true)
	DatVe FINDIDMAX();

	@Query(value = "SELECT * FROM DatVe WHERE idtaikhoan = ? ORDER BY MADATVE DESC", nativeQuery = true)
	List<DatVe> ListDatVeByIdTaiKhoan(Integer idtaikhoan); // lấy tất cả thông tin đặt vé của 1 hành khách

	/* TÀI KHOẢN */
	@Query(value = "SELECT hd.MAHD, dv.NGAYDAT, NGAYDI, dv.MADATVE, NGAYVE, hd.TRANGTHAI, hk.HOVATEN, hk.sdt, dv.SOGHE,t.TENTAU,hd.TONGTIEN,dv.LOAIVE,hd.NGAYLAP,tuyen.TENTUYEN,hk.EMAIL "
			+ "FROM HOADON hd "
			+ "INNER JOIN DATVE dv ON dv.MADATVE = hd.MADATVE "
			+ "INNER JOIN TAIKHOAN hk ON hk.IDTAIKHOAN = dv.IDTAIKHOAN "
			+ "INNER JOIN LICHTAUCHAY ltc ON ltc.IDLICHTAU = dv.IDLICHTAU "
			+ "INNER JOIN TAU t ON t.IDTAU = ltc.IDTAU "
			+ "INNER JOIN TUYEN tuyen ON tuyen.IDTUYEN = ltc.IDTUYEN "
			+ "WHERE tuyen.IDTUYEN  = :id AND CAST(NGAYDI AS DATE) = :ngay ", nativeQuery = true)
	List<Object> thongTinDatVe(@Param("id") Integer id, @Param("ngay") Date ngay);

	/* Tất cả thông tin tài khoản */
	@Query(value = "SELECT hd.MAHD, dv.NGAYDAT, NGAYDI, dv.MADATVE, NGAYVE, hd.TRANGTHAI, hk.HOVATEN, hk.sdt, dv.SOGHE,t.TENTAU,hd.TONGTIEN,dv.LOAIVE,hd.NGAYLAP,tuyen.TENTUYEN,hk.EMAIL "
			+ "FROM HOADON hd "
			+ "INNER JOIN DATVE dv ON dv.MADATVE = hd.MADATVE "
			+ "INNER JOIN TAIKHOAN hk ON hk.IDTAIKHOAN = dv.IDTAIKHOAN "
			+ "INNER JOIN LICHTAUCHAY ltc ON ltc.IDLICHTAU = dv.IDLICHTAU "
			+ "INNER JOIN TAU t ON t.IDTAU = ltc.IDTAU "
			+ "INNER JOIN TUYEN tuyen ON tuyen.IDTUYEN = ltc.IDTUYEN "
			+ "ORDER BY dv.NGAYDAT DESC", nativeQuery = true)
	List<Object> thongTinTK();

	/* HÀNH KHÁCH */
	@Query(value = "SELECT hd.MAHD, dv.NGAYDAT, NGAYDI, dv.MADATVE, NGAYVE, hd.TRANGTHAI, hk.HOVATEN, hk.sdt, dv.SOGHE,t.TENTAU ,hd.TONGTIEN,dv.LOAIVE,hd.NGAYLAP,tuyen.TENTUYEN,hk.EMAIL "
			+ "FROM HOADON hd "
			+ "INNER JOIN DATVE dv ON dv.MADATVE = hd.MADATVE "
			+ "INNER JOIN HANHKHACH  hk ON hk.IDHANHKHACH = dv.IDHANHKHACH "
			+ "INNER JOIN LICHTAUCHAY ltc ON ltc.IDLICHTAU = dv.IDLICHTAU "
			+ "INNER JOIN TAU t ON t.IDTAU = ltc.IDTAU "
			+ "INNER JOIN TUYEN tuyen ON tuyen.IDTUYEN = ltc.IDTUYEN "
			+ "WHERE tuyen.IDTUYEN  = :id AND CAST(NGAYDI AS DATE) = :ngay ", nativeQuery = true)
	List<Object> thongTinDatVeHK(@Param("id") Integer id, @Param("ngay") Date ngay);

	/* Tất cả thông tin hành khách */
	@Query(value = "SELECT hd.MAHD, dv.NGAYDAT, NGAYDI, dv.MADATVE, NGAYVE, hd.TRANGTHAI, hk.HOVATEN, hk.sdt, dv.SOGHE,t.TENTAU,hd.TONGTIEN,dv.LOAIVE,hd.NGAYLAP,tuyen.TENTUYEN,hk.EMAIL "
			+ "FROM HOADON hd "
			+ "INNER JOIN DATVE dv ON dv.MADATVE = hd.MADATVE "
			+ "INNER JOIN HANHKHACH  hk ON hk.IDHANHKHACH = dv.IDHANHKHACH "
			+ "INNER JOIN LICHTAUCHAY ltc ON ltc.IDLICHTAU = dv.IDLICHTAU "
			+ "INNER JOIN TAU t ON t.IDTAU = ltc.IDTAU "
			+ "INNER JOIN TUYEN tuyen ON tuyen.IDTUYEN = ltc.IDTUYEN "
			+ "ORDER BY dv.NGAYDAT DESC", nativeQuery = true)
	List<Object> thongTinHK();

	// Thông tin đặt ghế (tên ghế - id ghế)
	@Query(value = "SELECT IDDATGHE,TENGHE,KHOANG FROM DATGHE INNER JOIN GHENGOI ON GHENGOI.IDGHE = DATGHE.IDGHE WHERE IDDATVE = ?1", nativeQuery = true)
	List<Object> thongTinGheDat(Integer iddatve);

	// Thông tin người đi cùng
	@Query(value = "SELECT * FROM NGUOIDICUNG WHERE MADATVE = ?1", nativeQuery = true)
	List<Object> thongTinNguoiDiCung(Integer iddatve);

	// Tính tiền của hành khách khi xóa hành khách đó
	@Query(value = "SELECT g.GIA FROM LOAIHK hk " +
			"INNER JOIN NGUOIDICUNG n ON hk.IDLOAIHK = n.IDLOAIHK " +
			"INNER JOIN GIAVE g ON g.IDLOAIHK = n.IDLOAIHK " +
			"INNER JOIN LOAIVE l ON l.IDLOAIVE = g.IDLOAIVE " +
			"WHERE n.IDNGUOIDICUNG = :id AND g.IDTUYEN = :idTuyen AND g.IDLOAIVE = :idLoaiVe", nativeQuery = true)
	List<Object> tongTien(@Param("id") Integer id, @Param("idTuyen") Integer idTuyen,@Param("idLoaiVe") Integer idLoaiVe);

	@Transactional
	@Modifying
	@Query(value = "UPDATE HOADON SET TONGTIEN = :tongtien WHERE MAHD = :id", nativeQuery = true)
	void tongTienHoaDon(@Param("id") Integer id, @Param("tongtien") BigDecimal tongtien);
			
	

}
