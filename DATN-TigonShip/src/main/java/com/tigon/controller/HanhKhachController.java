package com.tigon.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.HanhKhachDAO;
import com.tigon.model.HanhKhach;
import com.tigon.service.HanhKhachService;

@Controller
public class HanhKhachController {

	@Autowired
	HttpSession session;

	@Autowired
	HanhKhachService hanhKhachService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired 
	HanhKhachDAO dao;

	@RequestMapping("/thongtintaikhoan")
	public String thongtintaikhoan(Model model) {

		Integer user = Integer.parseInt(session.getAttribute("user").toString());
		HanhKhach hanhkhach = hanhKhachService.findById(user);
		model.addAttribute("user", hanhkhach);
		HanhKhachDAO dao;
		
		return "/user/thongtintaikhoan";
	}
	
	@PostMapping("/capnhattaikhoan")
	public String capnhattaikhoan(
			Model model,
			@RequestParam String hovaten,
			@RequestParam String sdt,
			@RequestParam String cccd,
			@RequestParam String diachi
			) {
		
		Integer user = Integer.parseInt(session.getAttribute("user").toString());
		HanhKhach hanhkhach = hanhKhachService.findById(user);
		
		String thanhPho = request.getParameter("city");
		String quanHuyen = request.getParameter("district");
		String phuongXa = request.getParameter("ward");
		String diaChi = null;
		if(!diachi.isEmpty()) {
			diaChi = phuongXa + " " + quanHuyen + " " + thanhPho;
		}else {
			diaChi = diachi + " " + phuongXa + " " + quanHuyen + " " + thanhPho;
		}
		
		if(!hovaten.isEmpty() || !sdt.isEmpty() || !cccd.isEmpty() || !diaChi.isEmpty()) {
			hanhkhach.setHOVATEN(hovaten);
			hanhkhach.setSDT(sdt);
			hanhkhach.setCCCD(cccd);
			hanhkhach.setDIACHI(diaChi);
			dao.updateHanhKhach(hovaten, sdt, cccd, diaChi, user);
			model.addAttribute("thongbao","Cập nhật thông tin thành công!");
			return "/user/thongtintaikhoan";
			}
		return "/user/thongtintaikhoan";
	}
}
