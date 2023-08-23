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
	@GetMapping("/admin/tau")
	public String tau(Model model) {
		return "admin/tau";
	}
}
