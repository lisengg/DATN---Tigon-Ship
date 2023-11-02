package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DanhGia;

public interface DanhGiaDAO extends JpaRepository<DanhGia, Integer> {

  // điểm trung bình tất cả các tuyến
  @Query(value = "SELECT AVG(DANHGIA) FROM DANHGIAHANHKHACH ", nativeQuery = true)
  Integer AVGall();

  // các tuyến được đi nhiều nhất
  @Query(value = "SELECT COUNT(d.IDLICHTAU), TENTUYEN FROM lichtauchay l " +
      "INNER JOIN DATVE d ON d.IDLICHTAU = l.IDLICHTAU " +
      "INNER JOIN TUYEN t ON t.IDTUYEN = l.IDTUYEN " +
      "GROUP BY d.IDLICHTAU, t.TENTUYEN " +
      "ORDER BY COUNT(d.IDLICHTAU) DESC", nativeQuery = true)
  List<Object> Top5();

  // Tuyến - điểm TB tuyến bắt đầu từ HÒN SƠN
  @Query(value = "SELECT t.TENTUYEN, AVG(DANHGIA) FROM DANHGIAHANHKHACH d  inner join tuyen t  on d.IDTUYEN = t.IDTUYEN where TENTUYEN  like 'Hòn%' Group by t.TENTUYEN", nativeQuery = true)
  List<Object> AVGHonSon();

  // Tuyến - điểm TB tuyến bắt đầu từ KIÊN GIANG
  @Query(value = "SELECT t.TENTUYEN, AVG(DANHGIA) FROM DANHGIAHANHKHACH d  inner join tuyen t  on d.IDTUYEN = t.IDTUYEN where TENTUYEN  like 'Kiên%' Group by t.TENTUYEN", nativeQuery = true)
  List<Object> AVGKienGiang();

  // Tuyến - điểm TB tuyến bắt đầu từ NAM DU
  @Query(value = "SELECT t.TENTUYEN, AVG(DANHGIA) FROM DANHGIAHANHKHACH d  inner join tuyen t  on d.IDTUYEN = t.IDTUYEN where TENTUYEN  like 'Nam%' Group by t.TENTUYEN", nativeQuery = true)
  List<Object> AVGNamDu();

  // Tuyến - điểm TB tuyến bắt đầu từ PHÚ QUỐC
  @Query(value = "SELECT t.TENTUYEN, AVG(DANHGIA) FROM DANHGIAHANHKHACH d  inner join tuyen t  on d.IDTUYEN = t.IDTUYEN where TENTUYEN  like 'Phú%' Group by t.TENTUYEN", nativeQuery = true)
  List<Object> AVGPhuQuoc();

  // Tuyến - điểm,bình luận, người đánh giá
  @Query(value = "select h.HOVATEN, d.DANHGIA, d.BINHLUAN from DANHGIAHANHKHACH d inner join HANHKHACH h ON  d.IDHANHKHACH = h.IDHANHKHACH Where D.IDTUYEN=? ", nativeQuery = true)
  List<Object> danhGiaTuyen(Integer id);

}
