package com.tigon.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.HoaDon;

public interface HoaDonDAO extends JpaRepository<HoaDon, Integer> {
    // tổng doanh thu mỗi ngày
    @Query(value = "SELECT SUM(tongtien) as total_tongtien FROM hoadon WHERE CAST(ngaylap AS DATE) = CAST(GETDATE() AS DATE)", nativeQuery = true)
    Integer doanhThu();

    // doanh thu mỗi ngày
    @Query(value = "SELECT SUM(TONGTIEN),COUNT(D.MADATVE) AS 'SUM HD',SUM(D.SOGHE) AS 'GHE', TENTUYEN FROM hoadon h "
            + "INNER JOIN DATVE d ON h.MADATVE = d.MADATVE "
            + "INNER JOIN lichtauchay l ON l.IDLICHTAU = d.IDLICHTAU "
            + "INNER JOIN TUYEN t ON l.IDTUYEN = t.IDTUYEN "
            + "WHERE CAST(ngaylap AS DATE) = CAST(GETDATE() AS DATE) "
            + "GROUP BY TENTUYEN, l.IDTUYEN, t.TENTUYEN", nativeQuery = true)
    List<Object> doanhThuChiTiet();

    // Biểu đồ doanh thu hôm nay
    @Query(value = "SELECT t.KIHIEU ,SUM(TONGTIEN) AS 'TONGDOANHTHU' FROM hoadon h "
            + "INNER JOIN DATVE d ON h.MADATVE = d.MADATVE "
            + "INNER JOIN lichtauchay l ON l.IDLICHTAU = d.IDLICHTAU "
            + "INNER JOIN TUYEN t ON l.IDTUYEN = t.IDTUYEN "
            + "WHERE CAST(ngaylap AS DATE) = CAST(GETDATE() AS DATE) "
            + "GROUP BY TENTUYEN, l.IDTUYEN, t.KIHIEU", nativeQuery = true)
    List<Object> bieudodoanhthu();

    // Doanh thu theo ngày mong muốn
    @Query(value = "SELECT SUM(h.TONGTIEN) AS TONGTIEN, COUNT(d.MADATVE) AS SUM_HD, SUM(d.SOGHE) AS GHE, t.TENTUYEN " +
            "FROM hoadon h " +
            "INNER JOIN DATVE d ON h.MADATVE = d.MADATVE " +
            "INNER JOIN lichtauchay l ON l.IDLICHTAU = d.IDLICHTAU " +
            "INNER JOIN TUYEN t ON l.IDTUYEN = t.IDTUYEN " +
            "WHERE CONVERT(DATE, h.ngaylap) = ?1 " + // Sử dụng CONVERT để chuyển đổi sang kiểu DATE
            "GROUP BY t.TENTUYEN, l.IDTUYEN", nativeQuery = true)
    List<Object> doanhThuTheoNgay(Date ngay);

    @Query(value = "SELECT * FROM GIAVE WHERE MADATVE=?1", nativeQuery = true)
    public HoaDon findByMaDateVe(Integer madatve);
    
    @Query(value = "SELECT TOP 1 * from hoadon ORDER BY MADATVE DESC", nativeQuery = true)
    HoaDon findMaxDatVe();
}
