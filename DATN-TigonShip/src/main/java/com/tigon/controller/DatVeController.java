package com.tigon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DatVeController {
@RequestMapping("/thanhtoan")
public String thanhtoan() {
	return "/user/datve/thanhtoan";
}

@RequestMapping("/thanhtoanthanhcong")
public String thanhcong() {
	return "/user/datve/chuyenvetrangchu";
}
}
