package com.tigon.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tigon.dao.TaiKhoanDAO;
import com.tigon.model.TaiKhoan;

@CrossOrigin("*")
@RestController
public class AuthorityRestController {
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	
	@GetMapping("/rest/authority1")
	public List<TaiKhoan> getAllHanhKhach1() {
		List<TaiKhoan> list = taiKhoanDAO.findAll();

		return list;
	}
	@GetMapping("/rest/authority1/{id}")
	public TaiKhoan getOne1(@PathVariable("id") Integer id) {
		return taiKhoanDAO.findById(id).get();
	}

	 @PutMapping("/rest/authority1/{id}")
	    public TaiKhoan update(@PathVariable("id") Integer id, @RequestBody TaiKhoan taikhoan) {
	        return taiKhoanDAO.save(taikhoan);
	    }
	

}
