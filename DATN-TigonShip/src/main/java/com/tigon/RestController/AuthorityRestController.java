package com.tigon.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tigon.dao.HanhKhachDAO;
import com.tigon.model.HanhKhach;
import com.tigon.model.Tuyen;

@CrossOrigin("*")
@RestController
public class AuthorityRestController {
	@Autowired
	HanhKhachDAO hanhKhachDAO;
	
	@GetMapping("/rest/authority1")
	public List<HanhKhach> getAllHanhKhach1() {
		List<HanhKhach> list = hanhKhachDAO.findAll();

		return list;
	}
	@GetMapping("/rest/authority1/{id}")
	public HanhKhach getOne1(@PathVariable("id") Integer id) {
		return hanhKhachDAO.findById(id).get();
	}

	 @PutMapping("/rest/authority1/{id}")
	    public HanhKhach update(@PathVariable("id") Integer id, @RequestBody HanhKhach hanhkhach) {
	        return hanhKhachDAO.save(hanhkhach);
	    }
	

}
