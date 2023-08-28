package com.tigon.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.TuyenDAO;
import com.tigon.model.Tuyen;


@CrossOrigin("*")
@RestController
//@RequestMapping("/rest/tuyen")
public class TuyenRestController {
	  @Autowired
	    TuyenDAO tuyenDAO;

	    @GetMapping("/rest/tuyen")
	    public List<Tuyen> getAllTuyen() {
	        List<Tuyen> list = tuyenDAO.findAll();
	        return list;
	    }
	    
	    @GetMapping("/rest/tuyen/{id}")
	    public Tuyen getOne(@PathVariable("id") Integer id) {
	        return tuyenDAO.findById(id).get();
	    }
	    @PostMapping("/rest/tuyen/save")
	    public Tuyen create(@RequestBody Tuyen tuyen) {
	        return tuyenDAO.save(tuyen);
	    }
	    @PutMapping("/rest/tuyen/{id}")
	    public Tuyen update(@PathVariable("id") Integer id, @RequestBody Tuyen tuyen) {
	        return tuyenDAO.save(tuyen);
	    }
	    
//	    @DeleteMapping("/rest/tuyen/{id}")
//	    public void delete(@PathVariable("id") Integer id) {
//	    	tuyenDAO.deleteById(id);
//	    }
	
}
