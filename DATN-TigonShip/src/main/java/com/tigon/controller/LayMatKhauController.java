package com.tigon.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import com.tigon.service.EmailService;

@Controller
public class LayMatKhauController {

	@Autowired
	private EmailService emailService;

	@RequestMapping("/quenmatkhau")
	public String quenmatkhau(Model model) {

		return "/user/login/quenmatkhau";
	}

	@RequestMapping("/layotp")
	public String layotp(@RequestParam("email") String email, Model model) {
		Random random = new Random();

		// Tạo số ngẫu nhiên có 4 chữ số
		int min = 100000; // Giới hạn dưới là 1000
		int max = 999999; // Giới hạn trên là 9999

		int randomNumber = random.nextInt(max - min + 1) + min;
//		emailService.sendEmail(email,"Yêu Cầu Lấy Lại Mật Khẩu",
//				"Xin chào, đây là tin nhắn từ Website đặt vé tàu online Tigon Ship.\nOTP để lấy lại mật khẩu của bạn là : "
//						+ randomNumber
//						+ ".\nVui lòng không chia sẻ mã này cho bất kỳ ai và nhập đúng mã để tạo lại mật khẩu mới.");
		Context context = new Context();
		context.setVariable("message",randomNumber);

		emailService.sendEmailWithHtmlTemplate(email, "Yêu Cầu Cấp Lại Mật Khẩu", "/user/login/emailtemplates",
				context);
		return "/user/login/layOTP";
	}

	@RequestMapping("/guihoadon")
	public String email() {
		return "/user/datve/guihoadonkemqr";
	}
}
