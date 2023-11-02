package com.tigon.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.tigon.dao.HangTauDAO;
import com.tigon.dao.LichSuHangTauDAO;
import com.tigon.dao.HanhKhachDAO;
import com.tigon.dao.TauDAO;
import com.tigon.model.HangTau;
import com.tigon.model.LichSuHangTau;
import com.tigon.model.HanhKhach;
import com.tigon.service.DatVeService;
import com.tigon.service.HanhKhachService;

@CrossOrigin("*")
@RestController
public class hangTauRestController {
    @Autowired
    HangTauDAO hangTauDAO;
    
    @Autowired
    TauDAO tauDAO;

    @Autowired
    LichSuHangTauDAO lichsuDAO;
    @Autowired
    HttpSession session;
  
    @Autowired
    HanhKhachService hanhKhachService;
  
    @Autowired
    HttpServletRequest request;
  
    @Autowired
    HanhKhachDAO dao;
  
    @Autowired
    DatVeService datveService;
  
    
    @GetMapping("/rest/hangtau")
    public Map<String, Object> getAll() {
		Map<String, Object> map = new HashMap<>();
		map.put("hangtau", hangTauDAO.findAll());
		map.put("tau", tauDAO.findAll());
    map.put("lichsu", lichsuDAO.findAll());
		return map;
    }
    @GetMapping("/rest/hangtau/{id}")
    public HangTau getOne(@PathVariable("id") Integer id) {
        return hangTauDAO.findById(id).get();
    }
   
    @PostMapping("/rest/hangtau/save")
    public HangTau save(@RequestBody HangTau hangTau) {
        return hangTauDAO.save(hangTau);
    }
    @PostMapping("/rest/hangtau/lichsu/save")
    public LichSuHangTau saveLichSu(@RequestBody LichSuHangTau lichSu,HttpSession session) {
      Integer user = Integer.parseInt(session.getAttribute("user").toString());
      HanhKhach hanhkhach = hanhKhachService.findById(user);
      lichSu.setTEN(hanhkhach.getHOVATEN());
        return lichsuDAO.save(lichSu);
    }
    @PutMapping("/rest/hangtau/{id}")
    public HangTau update(@PathVariable("id") Integer id, @RequestBody HangTau hangTau) {
        return hangTauDAO.save(hangTau);
    }
    
    @DeleteMapping("/rest/hangtau/{id}")
    public void delete(@PathVariable("id") Integer id) {
      hangTauDAO.deleteById(id);
    }

}
