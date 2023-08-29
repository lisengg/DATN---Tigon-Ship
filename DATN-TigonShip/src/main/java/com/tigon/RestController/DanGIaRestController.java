package com.tigon.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.DanhGiaDAO;
import com.tigon.dao.HanhKhachDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.DanhGia;

@CrossOrigin("*")
@RestController
public class DanGIaRestController {

	@Autowired
	TuyenDAO tuyenDAO;

	@Autowired
	HanhKhachDAO hanhKhachDAO;

	@Autowired
	DanhGiaDAO danhGiaDAO;

	@GetMapping("/rest/danhgia")
	public Map<String, Object> getAll() {
		Map<String, Object> map = new HashMap<>();
		map.put("danhgia", danhGiaDAO.findAll());
		map.put("tuyen", tuyenDAO.findAll());
		map.put("hanhkhach", hanhKhachDAO.findAll());
		return map;
	}

	@GetMapping("/rest/danhgia/{id}")
	public DanhGia getOne(@PathVariable("id") Integer id) {
		return danhGiaDAO.findById(id).get();
	}

}
