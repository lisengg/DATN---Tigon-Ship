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

import com.tigon.dao.LichSuTuyenDAO;
import com.tigon.dao.LichTauChayDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.TaiKhoan;
import com.tigon.model.LichSuTuyen;
import com.tigon.model.Tuyen;
import com.tigon.service.TaiKhoanService;

@CrossOrigin("*")
@RestController
//@RequestMapping("/rest/tuyen")
public class TuyenRestController {
	@Autowired
	TuyenDAO tuyenDAO;
	@Autowired
	LichTauChayDAO lichTauChayDAO;
	@Autowired
    TaiKhoanService taiKhoanService;
	@Autowired 
	LichSuTuyenDAO lichSuTuyenDAO;

	@GetMapping("/rest/tuyen")
	public Map<String, Object> getAll() {
		Map<String, Object> map = new HashMap<>();
		map.put("lichtau", lichTauChayDAO.findAll());
		map.put("tuyen", tuyenDAO.findAll());
		map.put("lichsu",lichSuTuyenDAO.findAll());
		return map;
	}

	@GetMapping("/rest/tuyen/{id}")
	public Tuyen getOne(@PathVariable("id") Integer id) {
		return tuyenDAO.findById(id).get();
	}

	@PostMapping("/rest/tuyen/save")
	public Tuyen create(@RequestBody Tuyen tuyen) {
		return tuyenDAO.save(tuyen);
	}
	@PostMapping("/rest/tuyen/lichsu/save")
    public LichSuTuyen saveLichSu(@RequestBody LichSuTuyen lichSu,HttpSession session) {
      Integer user = Integer.parseInt(session.getAttribute("user").toString());
      TaiKhoan taikhoan = taiKhoanService.findById(user);
      lichSu.setTEN(taikhoan.getHOVATEN());
        return lichSuTuyenDAO.save(lichSu);
    }

	@PutMapping("/rest/tuyen/{id}")
	public Tuyen update(@PathVariable("id") Integer id, @RequestBody Tuyen tuyen) {
		return tuyenDAO.save(tuyen);
	}

	@DeleteMapping("/rest/tuyen/{id}")
	public void delete(@PathVariable("id") Integer id) {
		tuyenDAO.deleteById(id);
	}

}
