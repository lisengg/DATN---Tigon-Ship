package com.tigon.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tigon.dao.HangTauDAO;
import com.tigon.dao.HanhKhachDAO;


@Controller
public class QuanlyController {

	@Autowired
	HangTauDAO hangTauDAO;
	  
	@Autowired
	HanhKhachDAO hanhKhachDAO;
	@GetMapping("/admin")
	public String quanly(Model model) {
		System.out.println(hangTauDAO.findAll().get(0).getDIACHI());
		return "admin/index";
	}
	
	@GetMapping("/admin/authority1")
	public String authority(Model model) {
		return "admin/authority1/index1";
	}
	
	@GetMapping("/admin/tau")
	public String tau(Model model) {
		return "admin/tau/tau";
	}
	@GetMapping("/admin/ghengoi")
	public String ghengoi(Model model) {
		return "admin/ghengoi/ghengoi";
	}
	@GetMapping("/admin/tuyentau")
	public String tuyentau(Model model) {
		return "admin/tuyentau/tuyentau";
	}

	@GetMapping("/admin/hangtau")
	public String hangtau(Model model) {
		return "admin/hangtau/hangtau";
	}

	@GetMapping("/admin/hanhkhach")
	public String hanhkhach(Model model) {
		
		return "admin/hanhkhach/hanhkhach";
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
	@GetMapping("/admin/hoadon")
	public String hoadon(Model model) {
	
		return "admin/hoadon/hoadon";
	}
	@GetMapping("/admin/doanhthu")
	public String doanhthu(Model model) {
	
		return "admin/doanhthu/doanhthu";
	}
//	@GetMapping("/admin/profile")
//	public String Profile(Model model) {
//	
//		return "admin/profile/profile";
//	}
	
}
