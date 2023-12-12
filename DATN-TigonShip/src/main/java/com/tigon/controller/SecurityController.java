package com.tigon.controller;

import java.io.Console;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tigon.dao.TaiKhoanDAO;
import com.tigon.model.LoaiHanhKhach;
import com.tigon.model.TaiKhoan;
import com.tigon.service.LoaiHanhKhachService;
import com.tigon.service.TaiKhoanService;

@CrossOrigin("*")
@Controller
public class SecurityController {
	@Autowired
	TaiKhoanService taiKhoanService;

	@Autowired
	HttpSession session;
	
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	
	@Autowired
	LoaiHanhKhachService loaiHanhKhachService;

	@RequestMapping("/security/login/form")
	public String loginForm(Model model) {
		model.addAttribute("message", "Vui lòng đăng nhập!");
		return "user/login/index";
	}

	@RequestMapping("/security/login/success")
	public String loginSuccess(Model model) {

		model.addAttribute("message", "Đăng nhập thành công!");
		return "user/index";
	}

	@RequestMapping("/security/login/error")
	public String loginError(Model model) {
		model.addAttribute("messageError", "Sai thông tin đăng nhập!");
		return "user/login/index";
	}

	@RequestMapping("/security/unauthoritied")
	public String unauthoritied(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất!");
		return "user/login/401page";
	}

	@RequestMapping("/security/logoff/success")
	public String logoffSuccess(Model model) {
		model.addAttribute("message", "Bạn đã đăng xuất!");
		session.removeAttribute("user");
		session.removeAttribute("role");
		return "user/index";
	}

	// Oauth2
	@GetMapping("/oauth2/login/form")
	public String form() {
		return "login/index";
	}

	@GetMapping("/oauth2/login/success")
	public String success(OAuth2AuthenticationToken oauth, Model model) {
		OAuth2User oauthUser = ((OAuth2AuthenticationToken) oauth).getPrincipal();
		// Kiểm tra xem người dùng đã đăng nhập trước đó chưa
		String googleId = oauthUser.getAttribute("sub");
		System.out.println(googleId);
		TaiKhoan findGoogleId = taiKhoanService.findByGoogleId(googleId);
		String fullName = oauthUser.getAttribute("name");
	    String email = oauthUser.getAttribute("email");
		
		if (findGoogleId == null) {
			LoaiHanhKhach loaihk = loaiHanhKhachService.findByid(1);
			TaiKhoan taikhoanmoi = new TaiKhoan();
			taikhoanmoi.setGOOGLEID(googleId);
			taikhoanmoi.setEMAIL(email);
			taikhoanmoi.setHOVATEN(fullName);
			taikhoanmoi.setLOAIHK(loaihk);
			taiKhoanDAO.save(taikhoanmoi);
		
			TaiKhoan tkGooglemoitao = taiKhoanService.findByGoogleId(googleId);
			session.setAttribute("user", tkGooglemoitao.getIDTAIKHOAN());
			return "user/index";
			
		}
		session.setAttribute("user", findGoogleId.getIDTAIKHOAN());
		String hoten = findGoogleId.getHOVATEN();
		String pass = findGoogleId.getMATKHAU();
		String role = findGoogleId.getVAITRO();
		User.withUsername(hoten).password(pass).roles(role).build();

		return "user/index";
	}

	@GetMapping("/oauth2/login/error")
	public String error(Model model) {
		return "user/login/index";
	}
	
	@GetMapping("/login/facebook")
    public String loginWithFacebook() {
        return "redirect:/oauth2/authorization/facebook";
    }

}
