package com.tigon.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.HangTauDAO;
import com.tigon.dao.TauDAO;
import com.tigon.model.Tau;

@CrossOrigin("*")
@RestController
public class TauRestController {
    @Autowired
    TauDAO tauDAO;
    @Autowired
    HangTauDAO hangTauDAO;
    
    @GetMapping("/rest/tau")
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("tau", tauDAO.findAll());
        map.put("hangtau", hangTauDAO.findAll());
        return map;
    }
    @GetMapping("/rest/tau/{id}")
    public Tau getOne(@PathVariable("id") Integer id) {
        return tauDAO.findById(id).get();
    }
}
