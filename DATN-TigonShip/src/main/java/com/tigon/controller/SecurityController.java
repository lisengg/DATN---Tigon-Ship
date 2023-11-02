package com.tigon.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tigon.service.HanhKhachService;

@CrossOrigin("*")
@Controller
public class SecurityController {
	
	HanhKhachService hanhKhachService;
	
	@Autowired
	HttpSession session;
	
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
		String email = oauth.getPrincipal().getAttribute("email");
		String pass = oauth.getPrincipal().getAttribute("name");

		UserDetails user = User.withUsername(email).password("").roles("USER").build();

		Authentication auth = new UsernamePasswordAuthenticationToken(email, null, user.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);
		model.addAttribute("message", "Đăng nhập MXH thành công!");
		return "user/login/main";
	}

	@GetMapping("/oauth2/login/error")
	public String error(Model model) {
		model.addAttribute("message", "Đăng nhập MXH không thành công!");
		return "user/login/main";
	}

}
