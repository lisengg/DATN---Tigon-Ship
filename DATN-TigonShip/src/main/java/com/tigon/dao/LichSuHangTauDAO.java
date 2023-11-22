package com.tigon.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tigon.model.LichSuHangTau;
public interface LichSuHangTauDAO extends JpaRepository<LichSuHangTau,Integer> {
    @Query(value = "SELECT * FROM LICHSUHANGTAU ORDER BY THOIGIAN DESC", nativeQuery = true)
    List<Object> lichsucapnhat();
}
