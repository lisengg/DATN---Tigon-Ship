package com.tigon.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.DatVeDAO;
import com.tigon.dao.HanhKhachDAO;
import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.dao.LoaiVeDAO;
import com.tigon.model.DatVe;
@CrossOrigin("*")
@RestController
public class hanhKhachRestController {
    @Autowired
    HanhKhachDAO hanhKhachDAO;
    @Autowired 
    DatVeDAO datVeDAO;
    @Autowired
    LoaiHanhKhachDAO loaiHanhKhachDAO;
    @Autowired 
    LoaiVeDAO loaiVeDAO;

    @Autowired
    LoaiHanhKhachDAO loaiHKDAO;
    @GetMapping("/rest/hanhkhach")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("datve", datVeDAO.findAll());
        map.put("hanhkhach", hanhKhachDAO.findAll());
        map.put("loaive",loaiVeDAO.findAll());
        map.put("loaihk",loaiHKDAO.findAll());
        return map;
    }
    @GetMapping("/rest/hanhkhach/{id}") // lấy danh sách ghế ngồi theo id tàu
    public List<DatVe> getAllGheNgoi(@PathVariable("id") Integer id) {
        return datVeDAO.ListDatVeByidKhach(id);
    }


}
