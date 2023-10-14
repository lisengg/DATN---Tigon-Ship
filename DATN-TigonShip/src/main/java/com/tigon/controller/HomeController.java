package com.tigon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HomeController {


	@RequestMapping("/")
	public String index() {
		return "user/index";
	}
	@RequestMapping("/lienhe")
	public String lienhe() {
		return "user/Lienhe";
	}
	@RequestMapping("/gioithieu")
	public String gioithieu() {
		return "user/GioiThieu";
	}
	@RequestMapping("/gopy")
	public String gopy() {
		return "user/GopY";
	}
	@RequestMapping("/dangnhap")
	public String dangnhap() {
		return "user/login/index";
	}
	@GetMapping("/dangki")
	public String dangki(Model model) {
		return "user/login/register";
	}
	@RequestMapping("/user/luc")
	public String tuyentau() {
		return "/user/VeTau";
	}
	
	@RequestMapping("/xacminh")
	public String xacminh(Model model, String sdt) {
		model.addAttribute("message",sdt);
		
		return "/user/login/verifyPage";
	}
	
	
}
