package com.tigon.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.dao.TaiKhoanDAO;
import com.tigon.model.TaiKhoan;

@CrossOrigin("*")
@RestController
public class AuthorityRestController {
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	@Autowired
	LoaiHanhKhachDAO loaiHanhKhachDAO;
	@GetMapping("/rest/authority1")
	 public Map<String, Object> getAll() {
	        Map<String, Object> map = new HashMap<>();
	        map.put("taikhoan", taiKhoanDAO.findAll());
	        map.put("loaihk", loaiHanhKhachDAO.findAll());
	        return map;
	    }
	@GetMapping("/rest/authority1/{id}")
	public TaiKhoan getOne1(@PathVariable("id") Integer id) {
		return taiKhoanDAO.findById(id).get();
	}
	@PostMapping("/rest/authority1/save")
    public TaiKhoan create(@RequestBody TaiKhoan taikhoan) {
        return taiKhoanDAO.save(taikhoan);
    }
	 @PutMapping("/rest/authority1/{id}")
	    public TaiKhoan update(@PathVariable("id") Integer id, @RequestBody TaiKhoan taikhoan) {
	        return taiKhoanDAO.save(taikhoan);
	    }
	

}
