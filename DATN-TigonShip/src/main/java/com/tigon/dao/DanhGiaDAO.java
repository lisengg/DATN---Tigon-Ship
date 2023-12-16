package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DanhGia;

public interface DanhGiaDAO extends JpaRepository<DanhGia, Integer> {

  // điểm trung bình tất cả các tuyến
  @Query(value = "SELECT AVG(DANHGIA) FROM DANHGIA ", nativeQuery = true)
  Integer AVGall();

  // các tuyến được đi nhiều nhất sắp xếp theo lượt đặt vé
  @Query(value = "SELECT TOP 5 SUM(d.SOGHE), TENTUYEN FROM lichtauchay l " +
      "INNER JOIN DATVE d ON d.IDLICHTAU = l.IDLICHTAU " +
      "INNER JOIN TUYEN t ON t.IDTUYEN = l.IDTUYEN " +
      "GROUP BY d.IDLICHTAU, t.TENTUYEN " +
      "ORDER BY SUM(d.SOGHE) DESC", nativeQuery = true)
  List<Object> Top5();

  // Các tuyến được đi đánh giá cao nhất giảm dần thấp nhất
  @Query(value = "SELECT TOP 5 tentuyen as 'tentuyen', AVG(DANHGIA) as 'danhgia' FROM DANHGIA"
  + " INNER JOIN tuyen ON tuyen.IDTUYEN = DANHGIA.IDTUYEN"
  + " GROUP BY TENTUYEN "
  + " ORDER BY danhgia DESC", nativeQuery = true)
List<Object> diemGiamDan();

  // Tuyến - điểm TB tuyến bắt đầu từ HÒN SƠN
  @Query(value = "SELECT t.TENTUYEN, AVG(DANHGIA) FROM DANHGIA d  inner join tuyen t  on d.IDTUYEN = t.IDTUYEN where TENTUYEN  like 'Hòn%' Group by t.TENTUYEN", nativeQuery = true)
  List<Object> AVGHonSon();

  // Tuyến - điểm TB tuyến bắt đầu từ KIÊN GIANG
  @Query(value = "SELECT t.TENTUYEN, AVG(DANHGIA) FROM DANHGIA d  inner join tuyen t  on d.IDTUYEN = t.IDTUYEN where TENTUYEN  like 'Kiên%' Group by t.TENTUYEN", nativeQuery = true)
  List<Object> AVGKienGiang();

  // Tuyến - điểm TB tuyến bắt đầu từ NAM DU
  @Query(value = "SELECT t.TENTUYEN, AVG(DANHGIA) FROM DANHGIA d  inner join tuyen t  on d.IDTUYEN = t.IDTUYEN where TENTUYEN  like 'Nam%' Group by t.TENTUYEN", nativeQuery = true)
  List<Object> AVGNamDu();

  // Tuyến - điểm TB tuyến bắt đầu từ PHÚ QUỐC
  @Query(value = "SELECT t.TENTUYEN, AVG(DANHGIA) FROM DANHGIA d  inner join tuyen t  on d.IDTUYEN = t.IDTUYEN where TENTUYEN  like 'Phú%' Group by t.TENTUYEN", nativeQuery = true)
  List<Object> AVGPhuQuoc();

  // Tuyến - điểm,bình luận, người đánh giá
  @Query(value = "select h.HOVATEN, d.DANHGIA, d.BINHLUAN, d.NGAYDANHGIA from DANHGIA d inner join TAIKHOAN h ON  d.IDTAIKHOAN = h.IDTAIKHOAN Where D.IDTUYEN=? ", nativeQuery = true)
  List<Object> danhGiaTuyen(Integer id);
  
  @Query(value = "SELECT count(*) FROM DANHGIA WHERE DANHGIA IN (4, 5) ", nativeQuery = true)
	long countAllDanhGiaTot();
  @Query(value = "SELECT count(*) FROM DANHGIA WHERE DANHGIA IN (1, 2, 3) ", nativeQuery = true)
	long countAllDanhGiaKem();

}
