package com.tigon.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;

import com.tigon.service.EmailService;

@Controller
public class GuiHoaDonController {

	@Autowired
	private EmailService emailService;
	@Autowired
	private ServletContext servletContext;

	@GetMapping(value = "guihoadon")
	public String email1(Model model) {
		String link = "facebook.com";
		model.addAttribute("qrCodeText", link);
		Context context = new Context();
		context.setVariable("qrCodeText", link);
		context.setVariable("servletContext", servletContext);

		emailService.sendEmailWithHtmlTemplate("nlsangnlpc04364@gmail.com", "Thông Tin Đặt Vé Tàu Tigon Ship",
				"/user/datve/guihoadonkemqr", context);
		return "/user/datve/guihoadonkemqr";
	}
}
