package com.tigon.RestController;

import java.util.HashMap;
import java.util.List;
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

import com.tigon.dao.GheNgoiDAO;
import com.tigon.dao.HangTauDAO;
import com.tigon.dao.LichSuTauDAO;
import com.tigon.dao.TauDAO;
import com.tigon.model.GheNgoi;
import com.tigon.model.HanhKhach;
import com.tigon.model.LichSuTau;
import com.tigon.model.Tau;
import com.tigon.service.GheNgoiService;
import com.tigon.service.HanhKhachService;

@CrossOrigin("*")
@RestController
public class TauRestController {
    @Autowired
    TauDAO tauDAO;
    @Autowired
    HangTauDAO hangTauDAO;
    @Autowired 
    LichSuTauDAO lichsuDAO;
    @Autowired 
    GheNgoiDAO gheNgoiDAO;
    @Autowired
    HanhKhachService hanhKhachService;
    
    @GetMapping("/rest/tau")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("tau", tauDAO.findAll());
        map.put("hangtau", hangTauDAO.findAll());
        map.put("lichsu", lichsuDAO.findAll());
        map.put("ghengoi",gheNgoiDAO.findAll());

        return map;
    }
    @GetMapping("/rest/tau/{id}")
    public Tau getOne(@PathVariable("id") Integer id) {
        return tauDAO.findById(id).get();
    }
   
    @PostMapping("/rest/tau/save")
    public Tau save(@RequestBody Tau tau) {
        return tauDAO.save(tau);
    }

     // Lưu 160 ghế khi thêm tàu.
    /*  @PostMapping("/rest/tau/ghengoi/saveAll")
     public List<GheNgoi> saveAll(@RequestBody GheNgoiService gheNgoiService) {
         List<GheNgoi> danhSachGheNgoi = gheNgoiService.getDanhSachGheNgoi();
         return gheNgoiDAO.saveAll(danhSachGheNgoi);
     } */
     @PostMapping("/rest/tau/ghengoi/saveAll")
     public List<GheNgoi> saveAll(@RequestBody List<GheNgoi> danhSachGheNgoi) {
         return gheNgoiDAO.saveAll(danhSachGheNgoi);
     }
 
    @PostMapping("/rest/tau/lichsu/save")
    public LichSuTau saveLichSu(@RequestBody LichSuTau lichSu,HttpSession session) {
      Integer user = Integer.parseInt(session.getAttribute("user").toString());
      HanhKhach hanhkhach = hanhKhachService.findById(user);
      lichSu.setTEN(hanhkhach.getHOVATEN());
        return lichsuDAO.save(lichSu);
    }
    @PutMapping("/rest/tau/{id}")
    public Tau update(@PathVariable("id") Integer id, @RequestBody Tau tau) {
        return tauDAO.save(tau);
    }
    @DeleteMapping("/rest/tau/{id}")
    public void delete(@PathVariable("id") Integer id) {
      tauDAO.deleteById(id);
    }

}
