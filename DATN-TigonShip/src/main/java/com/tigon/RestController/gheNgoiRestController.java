package com.tigon.RestController;

import java.util.HashMap;
import java.util.List;
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

import com.tigon.dao.GheNgoiDAO;
import com.tigon.dao.TauDAO;
import com.tigon.model.GheNgoi;

@CrossOrigin("*")
@RestController
public class gheNgoiRestController {
    @Autowired
    GheNgoiDAO gheNgoiDAO;
    @Autowired
    TauDAO tauDAO;
    
    @GetMapping("/rest/ghengoi")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("tau", tauDAO.findAll());
        map.put("ghengoi", gheNgoiDAO.findAll());
        return map;
    }
    @GetMapping("/rest/ghengoi/{id}") // lấy danh sách ghế ngồi theo id tàu
    public List<GheNgoi> getAllGheNgoi(@PathVariable("id") Integer id) {
        return gheNgoiDAO.gheNgoiByIdtau(id);
    }
    @GetMapping("/rest/ghe/{id}")// lấy 1 ghế ngồi qua id ghế
    public GheNgoi getOne(@PathVariable("id") Integer id) {
        return gheNgoiDAO.findById(id).get();
    }
    @PostMapping("/rest/ghengoi/save")
    public GheNgoi save(@RequestBody GheNgoi gheNgoi) {
        return gheNgoiDAO.save(gheNgoi);
    }
    @PutMapping("/rest/ghengoi/{id}")
    public GheNgoi update(@PathVariable("id") Integer id, @RequestBody GheNgoi gheNgoi) {
        return gheNgoiDAO.save(gheNgoi);
    }
    
    @DeleteMapping("/rest/ghengoi/{id}")
    public void delete(@PathVariable("id") Integer id) {
        gheNgoiDAO.deleteById(id);
    }
}
