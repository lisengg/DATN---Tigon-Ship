package com.tigon.controller;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;

import com.tigon.service.*;

@Controller
public class GuiHoaDonController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private GenerateQRService qrService;
	
	@Autowired
	private ServletContext servletContext;

	@GetMapping(value = "guihoadon")
	public String guihoadon(Model model) throws IOException {
		String link = "facebook.com";
		model.addAttribute("qrCodeText", link);
		Context context = new Context();
		context.setVariable("qrCodeText", link);
		context.setVariable("servletContext", servletContext);
		qrService.generateQRCode(link);
		emailService.sendEmailWithHtmlTemplateAndAttachment("nlsangnlpc04364@gmail.com", "Thông Tin Đặt Vé Tàu Tigon Ship",
				"/user/datve/guihoadonkemqr", context,"src/main/resources/static/images/qr/mahoadon.png");
		
		return "/user/datve/guihoadonkemqr";
	}
}
