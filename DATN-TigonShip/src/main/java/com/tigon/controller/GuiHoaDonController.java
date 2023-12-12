package com.tigon.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@Autowired
	HttpSession session;

	@RequestMapping("/guihoadon")
	public String guihoadon() throws IOException {
		String mahoadon = session.getAttribute("mahoadon").toString();
		String link = "localhost:8080/mahoadon/id";
//		model.addAttribute("qrCodeText", link);
		Context context = new Context();
		context.setVariable("qrCodeText", link);
		context.setVariable("servletContext", servletContext);
		qrService.generateQRCode(link,mahoadon);
		emailService.sendEmailWithHtmlTemplateAndAttachment("nlsangnlpc04364@gmail.com", "Thông Tin Đặt Vé Tàu Tigon Ship",
				"/user/datve/guihoadonkemqr", context,"src/main/resources/static/images/qr/mahoadon"+mahoadon+".png");
		System.out.println("da gui ma hoa don");
		return "/user/index";
	}
	
	@RequestMapping("/a")
	public String a() {
		return "/user/datve/hoadon";
	}
}
