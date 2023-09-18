package com.tigon.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.DanhGia;

public interface DanhGiaDAO extends JpaRepository<DanhGia, Integer> {

    // điểm trung bình tất cả các tuyến
    @Query(value = "SELECT AVG(DANHGIA) FROM DANHGIAKHACHHANG ", nativeQuery = true)
    Integer AVGdanhgia();

    // lấy tất cả thông tin đanh giá thông qua id tuyen
    @Query(value = "SELECT * FROM DANHGIAKHACHHANG WHERE IDTUYEN = ?", nativeQuery = true)
    List<DanhGia> danhGiaByidTuyen(Integer idtuyen);


    //5 hành khách có lượt tương tác nhiều nhất
  /*   @Query("SELECT u.hovaten, COUNT(c.idhanhkhach) AS total " +
            "FROM HanhKhach u " +
            "JOIN u.danhgiahanhkhachs c " +
            "GROUP BY u.hovaten " +
            "ORDER BY total DESC")
    List<Object[]> findTop5HanhKhachWithMostDanhGia(); */

}
