package com.tigon.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/rest/danhgia")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("danhgia", danhGiaDAO.findAll());
        map.put("tuyen", tuyenDAO.findAll());
        map.put("hanhkhach", hanhKhachDAO.findAll());
        return map;
    }
    
}
