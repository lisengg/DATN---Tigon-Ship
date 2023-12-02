package com.tigon.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tigon.model.DatVe;

public interface DatVeDAO extends JpaRepository<DatVe, Integer>{
	
	@Query(value = "SELECT TOP 1 * FROM datve where idhanhkhach=? ORDER BY NGAYDAT DESC", nativeQuery = true)
    DatVe getNgayDatMoiNhat(Integer idhanhkhach);

	@Query(value = "SELECT * FROM DatVe WHERE idhanhkhach = ?", nativeQuery = true)
    List<DatVe> ListDatVeByidKhach(Integer idhanhkhach); // lấy tất cả thông tin đặt vé của 1 hành khách


    //Thông tin đặt vé ngày(8 bảng:)))))
    @Query(value = "SELECT hd.MAHD, dv.NGAYDAT, NGAYDI, NGAYVE, hd.TRANGTHAI, hk.HOVATEN, hk.sdt, dv.SOGHE, ltc.GIOXUATPHAT, t.TENTAU, tuyen.TENTUYEN " +
        "FROM HOADON hd " +
        "INNER JOIN DATVE dv ON dv.MADATVE = hd.MADATVE " +
        "INNER JOIN HANHKHACH hk ON hk.IDHANHKHACH = dv.IDHANHKHACH " +
        "INNER JOIN LICHTAUCHAY ltc ON ltc.IDLICHTAU = dv.IDLICHTAU " +
        "INNER JOIN TAU t ON t.IDTAU = ltc.IDTAU " +
        "INNER JOIN TUYEN tuyen ON tuyen.IDTUYEN = ltc.IDTUYEN " +
//        "INNER JOIN DATGHE dg ON dg.IDDATVE = dv.MADATVE " +
//        "INNER JOIN GHENGOI gn ON gn.IDGHE = dg.IDGHE " +
        "WHERE tuyen.IDTUYEN = :id AND CAST(dv.NGAYDAT AS DATE) = :ngay AND hd.TRANGTHAI LIKE N'%Đã thanh toán%'", nativeQuery = true)
        List<Object> thongTinDatVe(@Param("id") Integer id, @Param("ngay") Date ngay);
}
