package com.tigon.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;

import com.tigon.dao.TaiKhoanDAO;
import com.tigon.dao.OTPDAO;
import com.tigon.model.TaiKhoan;
import com.tigon.model.OTP;
import com.tigon.service.EmailService;
import com.tigon.service.TaiKhoanService;
import com.tigon.service.OTPService;

@Controller
@EnableScheduling
public class LayMatKhauController  implements CommandLineRunner {

    private final OTPDAO otpDAO;
    
	@Autowired
	private EmailService emailService;
	
	@Autowired
	OTPService OTPService;
	
	@Autowired
	TaiKhoanService taiKhoanService;

	@Autowired
	HttpSession session;
	
	@Autowired
	TaiKhoanDAO taiKhoanDAO;
    @Autowired
    public LayMatKhauController(OTPDAO otpDAO) {
        this.otpDAO = otpDAO;
    }
    
	@RequestMapping("/quenmatkhau")
	public String quenmatkhau(Model model) {
		
		return "/user/login/quenmatkhau";
	}

	@PostMapping("/layotp")
	public String layotp(@RequestParam("email") String email, Model model) {
		TaiKhoan getAllEmail = taiKhoanService.getAllEmail(email);
		if(getAllEmail==null) {
			model.addAttribute("message","Email này chưa đăng ký tài khoản!");
			return "/user/login/quenmatkhau";
		}
		
		Random random = new Random();

		// Tạo số ngẫu nhiên có 4 chữ số
		int min = 100000; // Giới hạn dưới là 1000
		int max = 999999; // Giới hạn trên là 9999

		int randomNumber = random.nextInt(max - min + 1) + min;

		Context context = new Context();
		context.setVariable("message",randomNumber);

		emailService.sendEmailWithHtmlTemplateAndAttachment(email, "Yêu Cầu Cấp Lại Mật Khẩu", "/user/login/emailtemplates",
				context,"");
		
		//Lưu otp
		TaiKhoan taikhoan = taiKhoanService.findIdByEmailOrPhone(email);
		OTP otp = new OTP();
		otp.setMAOTP(String.valueOf(randomNumber));
		otp.setTAIKHOAN(taikhoan);
		otpDAO.save(otp);
		System.out.println("Created OTP");
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        // Gọi hàm sau 5 phút
        executorService.schedule(() -> {
        	otpDAO.deleteAll();
            // Lấy giờ hiện tại
               LocalDateTime currentTime = LocalDateTime.now();

               // Định dạng giờ
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
               String formattedTime = currentTime.format(formatter);
               System.out.println("Xóa tất cả OTP lúc : "+formattedTime);
        }, 5, TimeUnit.MINUTES);

        // Đóng executorService sau khi đã sử dụng
        executorService.shutdown();
        session.setAttribute("iddoimk", getAllEmail.getIDTAIKHOAN());
    
        model.addAttribute("hoten",getAllEmail.getHOVATEN());
		return "/user/login/layOTP";
	}
	
	@PostMapping("/xacthucma")
	public String xacthucma(@RequestParam("otp") String otp, Model model) {
		List<OTP> findMaOTPTrung = OTPService.findByMaOTP(otp);
		if(findMaOTPTrung.isEmpty()) {
			model.addAttribute("message","OTP này không đúng!");
			return "/user/login/layOTP";
		}
		if(session.getAttribute("createdpass")==null) {
			return "/user/login/matkhaumoi";
		}
		if(session.getAttribute("email")!=null) {
			TaiKhoan taiKhoan = taiKhoanService.findById(Integer.parseInt(session.getAttribute("user").toString()));
			taiKhoan.setEMAIL(session.getAttribute("email").toString());
			taiKhoanDAO.save(taiKhoan);
		}
		TaiKhoan taiKhoan = taiKhoanService.findById(Integer.parseInt(session.getAttribute("user").toString()));
		taiKhoan.setMATKHAU(session.getAttribute("pass").toString());
		taiKhoanDAO.save(taiKhoan);
		return "/user/login/doimatkhauthanhcong";
	}
	
	@PostMapping("/doimatkhau")
	public String doimatkhau(@RequestParam("matkhaumoi") String matkhaumoi,@RequestParam("matkhaumoi2") String matkhaumoi2, Model model) {
		if(!matkhaumoi.equals(matkhaumoi2)) {
			model.addAttribute("message","Mật khẩu xác nhận không khớp!");
			return "/user/login/matkhaumoi";
		}
		TaiKhoan taiKhoan = taiKhoanService.findById(Integer.parseInt(session.getAttribute("iddoimk").toString()));
		taiKhoan.setMATKHAU(matkhaumoi);
		taiKhoanDAO.save(taiKhoan);
		return "/user/login/doimatkhauthanhcong";
	}
	
@RequestMapping("/doimatkhauthanhcong")
	public String doimatkhauthanhcong() {
	return "/user/login/doimatkhauthanhcong";
}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
