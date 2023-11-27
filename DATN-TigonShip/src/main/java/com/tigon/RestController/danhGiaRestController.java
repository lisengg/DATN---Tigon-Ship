package com.tigon.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.DanhGiaDAO;
import com.tigon.dao.HanhKhachDAO;
import com.tigon.dao.TuyenDAO;

@CrossOrigin("*")
@RestController
public class danhGiaRestController {
    @Autowired
    DanhGiaDAO danhGiaDAO;
    @Autowired 
    TuyenDAO tuyenDAO;
    @Autowired
    HanhKhachDAO hanhKhachDAO;


    @GetMapping("/rest/danhgia")// lấy thông tin all
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("danhgia", danhGiaDAO.findAll());
        map.put("tuyen", tuyenDAO.findAll());
        map.put("hanhkhach", hanhKhachDAO.findAll());
        return map;
    }

    @GetMapping("/rest/danhgia/tuyen/{id}") //tên ng đánh giá- điểm - bình luận của một tuyến -> id
    public List<Object> getDanhGiaTuyen(@PathVariable("id") Integer id) {
        return danhGiaDAO.danhGiaTuyen(id);
    }

    @GetMapping("/rest/danhgia/tuyen") // tính điểm TB của tất cả tuyến
    public Integer getAVGTB() {
        return danhGiaDAO.AVGall();
    }

    @GetMapping("/rest/danhgia/top5")//Các tuyến được đi nhiều theo thứ tự giảm dần
    public List<Object> getTop5(){
        return danhGiaDAO.Top5();
    }
    @GetMapping("/rest/danhgia/diemGiamDan")//Các tuyến được đánh giá theo điểm giảm dần
    public List<Object> getdiemGiamDan(){
        return danhGiaDAO.diemGiamDan();
    }

    @GetMapping("/rest/danhgia/tuyen/avg/honson")// HÒN SƠN
    public List<Object> AVGHonSon(){
        return danhGiaDAO.AVGHonSon();
    }

    @GetMapping("/rest/danhgia/tuyen/avg/kiengiang")// KIÊN GIANG
    public List<Object> AVGKienGiang(){
        return danhGiaDAO.AVGKienGiang();
    }

    @GetMapping("/rest/danhgia/tuyen/avg/namdu")// NAM DU
    public List<Object> AVGNamDu(){
        return danhGiaDAO.AVGNamDu();
    }

    @GetMapping("/rest/danhgia/tuyen/avg/phuquoc")// PHÚ QUỐC
    public List<Object> AVGPhuQuoc(){
        return danhGiaDAO.AVGPhuQuoc();
    }


    




    
    
}
