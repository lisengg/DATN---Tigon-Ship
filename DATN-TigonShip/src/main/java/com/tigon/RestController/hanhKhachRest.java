package com.tigon.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.HanhKhachDAO;
import com.tigon.model.HanhKhach;

@CrossOrigin("*")
@RestController
public class hanhKhachRest {
    @Autowired
    HanhKhachDAO hanhKhachDAO;

    @GetMapping("/rest/hanhkhach")
    public List<HanhKhach> getALL() {
        List<HanhKhach> list = hanhKhachDAO.findAll();
        return list;
    }

}
