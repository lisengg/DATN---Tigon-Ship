package com.tigon.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.dao.HangTauDAO;
import com.tigon.model.HangTau;


@CrossOrigin("*")
@RestController
//@RequestMapping("/rest/hangtau")
public class HangTauRestController {
	  @Autowired
	    HangTauDAO hangTauDAO;

	    @GetMapping("/rest/hangtau")
	    public List<HangTau> getAllLoaiBanh() {
	        List<HangTau> list = hangTauDAO.findAll();
	        return list;
	    }
	
}
