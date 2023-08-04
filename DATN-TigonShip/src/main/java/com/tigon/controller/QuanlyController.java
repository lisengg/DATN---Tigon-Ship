package com.tigon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuanlyController {
	@RequestMapping("/quanly")
	public String quanly(Model model) {
		return "quanly";
	}
	@RequestMapping("/quanlylichtau")
	public String quanlylich(Model model) {
		return "quanlylichtau";
	}
	@RequestMapping("/test")
	public String test(Model model) {
		return "test";
	}
}
