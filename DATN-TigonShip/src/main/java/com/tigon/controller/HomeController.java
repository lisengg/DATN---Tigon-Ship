package com.tigon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HomeController {


	@RequestMapping("/user/index")
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
		return "user/DangNhap";
	}
	@RequestMapping("/dangki")
	public String dangki() {
		return "user/DangKy";
	}
	@RequestMapping("/user/luc")
	public String tuyentau() {
		return "/user/VeTau";
	}
}
