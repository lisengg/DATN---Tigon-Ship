package com.tigon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class QuanlyController {
	@GetMapping("/quanly")
	public String quanly(Model model) {
		return "admin/index";
	}

}
