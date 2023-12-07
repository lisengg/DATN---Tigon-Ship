package com.tigon.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.DanhGiaDAO;
import com.tigon.model.DanhGia;

@Controller
public class DanhgiaController {
	@Autowired
	DanhGiaDAO dao;
	@Autowired
	HttpSession session;
 
		@RequestMapping("user/danhgia")
		public String danhgia() {
			return "user/DanhGia";
		}
		@RequestMapping("/add/DanhGia")
		public String Create( @RequestParam String noidung, DanhGia dg) {
			dg.setBINHLUAN(noidung);
			dao.save(dg);
			return"redirect:/user/DanhGia";
		}
}
