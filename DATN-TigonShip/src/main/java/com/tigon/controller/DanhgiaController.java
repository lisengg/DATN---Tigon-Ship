package com.tigon.controller;

import javax.mail.search.DateTerm;
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
		@RequestMapping("/add/danhgia")
		public String Create( @RequestParam String binhluan, @RequestParam int danhgia,@RequestParam int tuyen,
				DanhGia dg) {
			
			dg.setBINHLUAN(binhluan);
			dg.setDANHGIA(danhgia);
			
			dao.save(dg);
			return" redirect: user/DanhGia";
		}
}
