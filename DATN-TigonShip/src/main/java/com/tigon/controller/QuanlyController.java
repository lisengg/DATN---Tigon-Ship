package com.tigon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tigon.dao.HangTauDAO;


@Controller
public class QuanlyController {
	
	@Autowired
	HangTauDAO hangTauDAO;
	
	@GetMapping("/admin")
	public String quanly(Model model) {
		System.out.println(hangTauDAO.findAll().get(0).getDIACHI());
		return "admin/index";
	}
	@GetMapping("/admin/tau")
	public String tau(Model model) {
		return "admin/tau";
	}
	
	@GetMapping("/admin/hangtau")
	public String hangtau(Model model) {
		
		return "admin/hangtau";
	}
}
