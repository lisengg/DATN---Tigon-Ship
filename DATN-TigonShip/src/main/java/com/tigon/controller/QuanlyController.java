package com.tigon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/admin/authority/index")
	public String index(Model model) {
		return "admin/authority/index";
	}
	
	@GetMapping("/admin/tau/tau")
	public String tau(Model model) {
		return "admin/tau/tau";
	}

	@GetMapping("/admin/hangtau/hangtau")
	public String hangtau(Model model) {

		return "admin/hangtau/hangtau";
	}

	@GetMapping("/admin/tuyentau/tuyentau")
	public String tuyentau(Model model) {

		return "admin/tuyentau/tuyentau";
	}
	@GetMapping("/admin/giave/giave")
	public String giave(Model model) {

		return "admin/giave/giave";
	}
	@GetMapping("/admin/lichtau/lichtau")
	public String lichtau(Model model) {

		return "admin/lichtau/lichtau";
	}
	@RequestMapping("/thongke")
	public String thongke(Model model) {
		return "thongke";
	}
}
