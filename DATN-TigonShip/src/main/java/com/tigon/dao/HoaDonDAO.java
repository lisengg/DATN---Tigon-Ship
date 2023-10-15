package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.HoaDon;

public interface HoaDonDAO extends JpaRepository<HoaDon, Integer> {
    // tổng doanh thu mỗi ngày
    @Query(value = "SELECT SUM(tongtien) as total_tongtien FROM hoadon WHERE CAST(ngaylap AS DATE) = CAST(GETDATE() AS DATE)", nativeQuery = true)
    Integer doanhThu();

    @Query(value = "SELECT COUNT(h.MADATVE) AS COUNT_MADATVE, SUM(TONGTIEN) AS SUM_TONGTIEN, TENTUYEN FROM hoadon h "
    + "INNER JOIN DATVE d ON h.MADATVE = d.MADATVE "
    + "INNER JOIN lichtauchay l ON l.IDLICHTAU = d.IDLICHTAU "
    + "INNER JOIN TUYEN t ON l.IDTUYEN = t.IDTUYEN "
    + "WHERE CAST(ngaylap AS DATE) = CAST(GETDATE() AS DATE) "
    + "GROUP BY h.MADATVE, TENTUYEN", nativeQuery = true)
 List<Object> doanhThuChiTiet();
 
 
}
