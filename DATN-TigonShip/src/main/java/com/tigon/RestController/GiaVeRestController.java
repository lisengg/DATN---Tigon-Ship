package com.tigon.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.GiaVeDAO;
import com.tigon.dao.LichSuGiaVeDAO;
import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.dao.LoaiVeDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.GiaVe;
import com.tigon.model.TaiKhoan;
import com.tigon.model.LichSuGiaVe;
import com.tigon.service.TaiKhoanService;


@CrossOrigin("*")
@RestController
public class GiaVeRestController {
	
	@Autowired
	LoaiVeDAO loaiVeDAO;
	
	@Autowired
	LoaiHanhKhachDAO loaiHanhKhachDAO;
	@Autowired
	TuyenDAO tuyenDAO;
	
    @Autowired
    GiaVeDAO giaVeDAO;

    @Autowired
    LichSuGiaVeDAO lichsuDAO;
    @Autowired
    HttpSession session;
  
    @Autowired
    TaiKhoanService taiKhoanService;

    @GetMapping("/rest/giave")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("giave", giaVeDAO.findAll());
        map.put("loaihk", loaiHanhKhachDAO.findAll());
        map.put("tuyen", tuyenDAO.findAll());
        map.put("loaive", loaiVeDAO.findAll());
        map.put("lichsu", lichsuDAO.findAll());
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

    @PostMapping("/rest/giave/lichsu/save")
    public LichSuGiaVe saveLichSu(@RequestBody LichSuGiaVe lichSu,HttpSession session) {
      Integer user = Integer.parseInt(session.getAttribute("user").toString());
      TaiKhoan taikhoan = taiKhoanService.findById(user);
      lichSu.setTEN(taikhoan.getHOVATEN());
        return lichsuDAO.save(lichSu);
    }
    @PutMapping("/rest/giave/{id}")
    public GiaVe update(@PathVariable("id") Integer id, @RequestBody GiaVe giave) {
        return giaVeDAO.save(giave);
    }
    @DeleteMapping("/rest/giave/{id}")
    public void delete(@PathVariable("id") Integer id) {
    	giaVeDAO.deleteById(id);
    }
}
