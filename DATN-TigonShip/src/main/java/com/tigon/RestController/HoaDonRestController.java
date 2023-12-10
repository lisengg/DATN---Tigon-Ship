package com.tigon.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.DatVeDAO;
import com.tigon.dao.HoaDonDAO;
import com.tigon.model.HoaDon;

@CrossOrigin("*")
@RestController
public class HoaDonRestController {
    @Autowired
    HoaDonDAO hoaDonDAO;
    @Autowired
    DatVeDAO datVeDAO;
    
    @GetMapping("/rest/hoadon")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("hoadon", hoaDonDAO.findAll());
        map.put("datve", datVeDAO.findAll());
        return map;
    }
    @GetMapping("/rest/hoadon/{id}")
    public HoaDon getOne(@PathVariable("id") Integer id) {
        return hoaDonDAO.findById(id).get();
    }
}
