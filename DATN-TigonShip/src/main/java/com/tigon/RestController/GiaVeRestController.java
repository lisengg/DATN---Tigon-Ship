package com.tigon.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.GiaVeDAO;
import com.tigon.dao.LoaiVeDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.GiaVe;
import com.tigon.model.Tuyen;


@CrossOrigin("*")
@RestController
//@RequestMapping("/rest/giaves")
public class GiaVeRestController {
	
	@Autowired
	LoaiVeDAO loaiVeDAO;
	
	@Autowired
	TuyenDAO tuyenDAO;
	
    @Autowired
    GiaVeDAO giaVeDAO;

    @GetMapping("/rest/giave")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("giave", giaVeDAO.findAll());
        map.put("tuyen", tuyenDAO.findAll());
        map.put("loaive", loaiVeDAO.findAll());
        return map;
    }
    @GetMapping("/rest/giave/{id}")
    public GiaVe getOne(@PathVariable("id") Integer id) {
        return giaVeDAO.findById(id).get();
    }
    @PostMapping("/rest/giave/save")
    public GiaVe create(@RequestBody GiaVe giave) {
        return giaVeDAO.save(giave);
    }
    @PutMapping("/rest/giave/{id}")
    public GiaVe update(@PathVariable("id") Integer id, @RequestBody GiaVe giave) {
        return giaVeDAO.save(giave);
    }
}
