package com.tigon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.HanhKhachDAO;
import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.model.HanhKhach;
import com.tigon.model.LoaiHanhKhach;

@Controller
public class DangKyController {
	
	@Autowired
	HanhKhachDAO dao;
	
	@Autowired
	LoaiHanhKhachDAO loaiHKDao;
	
	
	@PostMapping("/processRegister")
	public String processRegister(Model model,
			HanhKhach hanhkhach,
			@RequestParam String username,
			@RequestParam String password,
			@RequestParam String password2,
			@RequestParam String email) {

			
			//Kiem tra email dang ky co ton tai hay chua
			boolean emptyEmail = true;
			if(dao.getAllEmail(email)!=null) {
				HanhKhach getEmail = dao.getAllEmail(email);
				emptyEmail = true;
				if(getEmail.getEMAIL()==null) {
					emptyEmail = false;
				}
			}
				
		if(username.isEmpty() || password.isEmpty() || password2.isEmpty() || email.isEmpty()) {
			model.addAttribute("error","Vui lòng điền đầy đủ thông tin");
			return "user/login/register";
		}else if(password.length() < 6) {
			model.addAttribute("errorPass","Mật khẩu phải lớn hơn 6 kí tự");
			return "user/login/register";
		}else if(!isValidEmail(email)) {
			model.addAttribute("errorEmail","Vui lòng nhập đúng định dạng email");
			return "user/login/register";
		}else if(!password.matches(password2)) {
			model.addAttribute("errorPass","Mật khẩu xác nhận phải trùng khớp với mật khẩu");
			return "user/login/register";
		
		}else if(emptyEmail==false) {
			model.addAttribute("errorPass","Email này đã được đăng ký");
			return "user/login/register";
		}
		else {
			LoaiHanhKhach loaiHanhKhach = loaiHKDao.getIdNguoiLon();
			hanhkhach.setLOAIHANHKHACH(loaiHanhKhach);
			hanhkhach.setHOVATEN(username);
			hanhkhach.setEMAIL(email);
			hanhkhach.setMATKHAU(password);
			hanhkhach.setQUYEN("USER");
			hanhkhach.setSOHDDADAT(0);
			dao.save(hanhkhach);
			return "user/index";
		}
		
		
		
	}
	
	private boolean isValidEmail(String email) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
			        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if(email.matches(regexPattern)) {
			return true;
		}
		return false;
	}
	
	private boolean isMobileValid(String sdt) {
		String ptrn = "^([0-9][1-9]{9})|([0][1-9][0-9]{9})$";
		if(ptrn.matches(sdt)) {
			return true;
		}else {
			return false;
		}
	}
}
