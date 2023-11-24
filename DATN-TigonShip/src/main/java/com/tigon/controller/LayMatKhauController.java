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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;

import com.tigon.dao.HanhKhachDAO;
import com.tigon.dao.OTPDAO;
import com.tigon.model.HanhKhach;
import com.tigon.model.OTP;
import com.tigon.service.EmailService;
import com.tigon.service.HanhKhachService;
import com.tigon.service.OTPService;
import com.twilio.rest.proxy.v1.service.Session;

@Controller
@EnableScheduling
public class LayMatKhauController  implements CommandLineRunner {

    private final OTPDAO otpDAO;
    
	@Autowired
	private EmailService emailService;
	
	@Autowired
	OTPService OTPService;
	
	@Autowired
	HanhKhachService hanhKhachService;

	@Autowired
	HttpSession session;
	
	@Autowired
	HanhKhachDAO hanhKhachDAO;
    @Autowired
    public LayMatKhauController(OTPDAO otpDAO) {
        this.otpDAO = otpDAO;
    }
    
	@RequestMapping("/quenmatkhau")
	public String quenmatkhau(Model model) {
		
		return "/user/login/quenmatkhau";
	}

	@RequestMapping("/layotp")
	public String layotp(@RequestParam("email") String email, Model model) {
		HanhKhach getAllEmail = hanhKhachService.getAllEmail(email);
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

		emailService.sendEmailWithHtmlTemplate(email, "Yêu Cầu Cấp Lại Mật Khẩu", "/user/login/emailtemplates",
				context);
		
		//Lưu otp
		OTP otp = new OTP();
		otp.setMAOTP(String.valueOf(randomNumber));
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
        session.setAttribute("iddoimk", getAllEmail.getIDHANHKHACH());
    
        model.addAttribute("hoten",getAllEmail.getHOVATEN());
		return "/user/login/layOTP";
	}
	
	@RequestMapping("/xacthucma")
	public String xacthucma(@RequestParam("otp") String otp, Model model) {
		List<OTP> findMaOTPTrung = OTPService.findByMaOTP(otp);
		if(findMaOTPTrung.isEmpty()) {
			model.addAttribute("message","OTP này không đúng!");
			return "/user/login/layOTP";
		}
		return "/user/login/matkhaumoi";
	}
	
	@RequestMapping("/doimatkhau")
	public String doimatkhau(@RequestParam("matkhaumoi") String matkhaumoi, Model model) {
		HanhKhach hanhKhach = hanhKhachService.findById(Integer.parseInt(session.getAttribute("iddoimk").toString()));
		hanhKhach.setMATKHAU(matkhaumoi);
		hanhKhachDAO.save(hanhKhach);
		return "/user/index";
	}
	
	@RequestMapping("/guihoadon")
	public String email() {
		return "/user/datve/guihoadonkemqr";
	}
	
    @Scheduled(initialDelay = 300000,fixedRate = 300000) // Chạy sau mỗi 5 phút
    public void deleteAllOTP() {
        otpDAO.deleteAll();
     // Lấy giờ hiện tại
        LocalDateTime currentTime = LocalDateTime.now();

        // Định dạng giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formattedTime = currentTime.format(formatter);
        System.out.println("Xóa tất cả OTP lúc : "+formattedTime);
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
