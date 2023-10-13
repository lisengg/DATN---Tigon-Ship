package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DanhGia;

public interface DanhGiaDAO extends JpaRepository<DanhGia, Integer> {

  // điểm trung bình tất cả các tuyến
  @Query(value = "SELECT AVG(DANHGIA) FROM DANHGIAHANHKHACH ", nativeQuery = true)
  Integer AVGall();

  // Top 5 người bình luận nhiều nhất
  @Query(value = "Select TOP 5 u.HOVATEN, COUNT(c.IDHANHKHACH) AS TOTAL from HANHKHACH u INNER JOIN DANHGIAHANHKHACH c ON u.IDHANHKHACH = C.IDHANHKHACH Group by u.HOVATEN ORDER BY TOTAL DESC", nativeQuery = true)
  List<Object> Top5();

  //Tuyến - điểm TB tuyến
  @Query(value = "SELECT t.TENTUYEN, AVG(DANHGIA) FROM DANHGIAHANHKHACH d  inner join tuyen t  on d.IDTUYEN = t.IDTUYEN Group by t.TENTUYEN", nativeQuery = true)
  List<Object> AVGTuyen();
  
  //Tuyến - điểm,bình luận, người đánh giá
  @Query(value = "select h.HOVATEN, d.DANHGIA, d.BINHLUAN from DANHGIAHANHKHACH d inner join HANHKHACH h ON  d.IDHANHKHACH = h.IDHANHKHACH Where D.IDTUYEN=? ", nativeQuery = true)
  List<Object> danhGiaTuyen(Integer id);


}
