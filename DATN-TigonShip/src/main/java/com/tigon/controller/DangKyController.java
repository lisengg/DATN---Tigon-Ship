package com.tigon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.TaiKhoanDAO;
import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.model.TaiKhoan;
import com.tigon.model.LoaiHanhKhach;

@Controller
public class DangKyController {

	@Autowired
	TaiKhoanDAO dao;

	@Autowired
	LoaiHanhKhachDAO loaiHKDao;

	@PostMapping("/processRegister")
	public String processRegister(Model model, TaiKhoan taikhoan, @RequestParam String username,
			@RequestParam String password, @RequestParam String password2, @RequestParam String email) {

		// Kiem tra email dang ky co ton tai hay chua
		boolean emptyEmail = true;
		if (dao.getAllEmail(email) != null) {
			TaiKhoan getEmail = dao.getAllEmail(email);
			System.out.println(getEmail.getEMAIL());
			if (getEmail.getEMAIL() != null) {
				emptyEmail = false;
			}
		}

		if (username.isEmpty() || password.isEmpty() || password2.isEmpty() || email.isEmpty()) {
			model.addAttribute("error", "Vui lòng điền đầy đủ thông tin");
			return "user/login/register";
		} else if (password.length() < 6) {
			model.addAttribute("errorPass", "Mật khẩu phải lớn hơn 6 kí tự");
			return "user/login/register";
		} else if (!isValidEmail(email)) {
			model.addAttribute("errorEmail", "Vui lòng nhập đúng định dạng email");
			return "user/login/register";
		} else if (!password.matches(password2)) {
			model.addAttribute("errorPass", "Mật khẩu xác nhận phải trùng khớp với mật khẩu");
			return "user/login/register";

		} else if (emptyEmail == false) {
			model.addAttribute("errorPass", "Email này đã được đăng ký");
			return "user/login/register";
		} else {
			LoaiHanhKhach loaiHanhKhach = loaiHKDao.getIdNguoiLon();
			taikhoan.setLOAIHK(loaiHanhKhach);
			taikhoan.setHOVATEN(username);
			taikhoan.setEMAIL(email);
			taikhoan.setMATKHAU(password);
			taikhoan.setVAITRO("HANHKHACH");
			dao.save(taikhoan);
			return "user/index";
		}

	}
	

	private boolean isValidEmail(String email) {
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if (email.matches(regexPattern)) {
			return true;
		}
		return false;
	}

	private boolean isMobileValid(String sdt) {
		String ptrn = "^([0-9][1-9]{9})|([0][1-9][0-9]{9})$";
		if (ptrn.matches(sdt)) {
			return true;
		} else {
			return false;
		}
	}
}
