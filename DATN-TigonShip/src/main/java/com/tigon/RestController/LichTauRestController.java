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

import com.tigon.dao.LichSuLichTauDAO;
import com.tigon.dao.LichTauChayDAO;
import com.tigon.dao.TauDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.HanhKhach;
import com.tigon.model.LichSuLichTauChay;
import com.tigon.model.LichTauChay;
import com.tigon.service.HanhKhachService;

@CrossOrigin("*")
@RestController
public class LichTauRestController {

	@Autowired
	TuyenDAO tuyenDAO;

	@Autowired
	TauDAO tauDAO;

	@Autowired
	LichTauChayDAO lichTauChayDAO;

	@Autowired
	LichSuLichTauDAO lichsuDAO;

	@Autowired
    HanhKhachService hanhKhachService;


	@GetMapping("/rest/lichtau")
	public Map<String, Object> getAll() {
		Map<String, Object> map = new HashMap<>();
		map.put("lichtau", lichTauChayDAO.findAll());
		map.put("tuyen", tuyenDAO.findAll());
		map.put("tau", tauDAO.findAll());
		map.put("lichsu", lichsuDAO.findAll());
		return map;
	}
	
	@GetMapping("/rest/lichtau/{id}")
	public LichTauChay getOne(@PathVariable("id") Integer id) {
		return lichTauChayDAO.findById(id).get();
	}
    @PostMapping("/rest/lichtau/save")
    public LichTauChay create(@RequestBody LichTauChay lichtauchay) {
        return lichTauChayDAO.save(lichtauchay);
    }
	@PostMapping("/rest/lichtau/lichsu/save")
    public LichSuLichTauChay saveLichSu(@RequestBody LichSuLichTauChay lichSu,HttpSession session) {
      Integer user = Integer.parseInt(session.getAttribute("user").toString());
      HanhKhach hanhkhach = hanhKhachService.findById(user);
      lichSu.setTEN(hanhkhach.getHOVATEN());
        return lichsuDAO.save(lichSu);
    }
	@PutMapping("/rest/lichtau/{id}")
	public LichTauChay update(@PathVariable("id") Integer id, @RequestBody LichTauChay lichtauchay) {
		return lichTauChayDAO.save(lichtauchay);
	}
    @DeleteMapping("/rest/lichtau/{id}")
    public void delete(@PathVariable("id") Integer id) {
    	lichTauChayDAO.deleteById(id);
    }
}
