package com.tigon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuanlyController {

	@GetMapping("/admin")
	public String quanly(Model model) {
		return "admin/index";
	}
	
	@GetMapping("/admin/authority/index")
	public String index(Model model) {
		return "admin/authority/index";
	}
	
	@GetMapping("/admin/tau")
	public String tau(Model model) {
		return "admin/tau/tau";
	}
	@GetMapping("/admin/ghengoi")
	public String ghengoi(Model model) {
		return "admin/ghengoi/ghengoi";
	}
	@GetMapping("/admin/nguoidung")
	public String nguoidung(Model model) {
		return "admin/hanhkhach/hanhkhach";
	}

	@GetMapping("/admin/hangtau")
	public String hangtau(Model model) {
		return "admin/hangtau/hangtau";
	}
	@GetMapping("/admin/tuyentau")
	public String tuyentau(Model model) {
		return "admin/tuyentau";
	}
	@GetMapping("/admin/giave")
	public String giave(Model model) {
		return "admin/giave/giave";
	}
	@GetMapping("/admin/lichtau")
	public String lichtau(Model model) {
		return "admin/lichtau/lichtau";
	}
	@GetMapping("/admin/danhgia")
	public String danhgia(Model model) {
		return "admin/danhgia/danhgia";
	}
}
