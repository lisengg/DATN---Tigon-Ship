package com.tigon.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;

import com.tigon.dao.TaiKhoanDAO;
import com.tigon.dao.DatGheDAO;
import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.dao.NguoiDiCungDAO;
import com.tigon.dao.OTPDAO;
import com.tigon.model.DatGhe;
import com.tigon.model.DatVe;
import com.tigon.model.GheNgoi;
import com.tigon.model.HoaDon;
import com.tigon.model.TaiKhoan;
import com.tigon.model.LoaiHanhKhach;
import com.tigon.model.NguoiDiCung;
import com.tigon.model.OTP;
import com.tigon.service.DatGheService;
import com.tigon.service.DatVeService;
import com.tigon.service.EmailService;
import com.tigon.service.HoaDonService;
import com.tigon.service.NguoiDiCungService;
import com.tigon.service.OTPService;
import com.tigon.service.TaiKhoanService;

@Controller
@EnableScheduling
public class TaiKhoanController implements CommandLineRunner {

	private final OTPDAO otpDAO;

	@Autowired
	public TaiKhoanController(OTPDAO otpDAO, JdbcTemplate jdbcTemplate) {
		this.otpDAO = otpDAO;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	HttpSession session;

	@Autowired
	TaiKhoanService taiKhoanService;

	@Autowired
	HttpServletRequest request;

	@Autowired
	TaiKhoanDAO dao;

	@Autowired
	DatVeService datveService;

	@Autowired
	LoaiHanhKhachDAO loaiHKDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	OTPService OTPService;
	
	@Autowired
	HoaDonService hoaDonService;
	
	@Autowired
	DatGheDAO datGheDao;
	
	@Autowired
	NguoiDiCungService nguoiDiCungService;

	private final JdbcTemplate jdbcTemplate;

	@RequestMapping("/thongtintaikhoan")
	public String thongtintaikhoan(Model model) {
		Integer user = Integer.parseInt(session.getAttribute("user").toString());
		List<DatVe> sohd = datveService.ListDatVeByIdTaiKhoan(user);
		List<DatVe> lichsuve = datveService.ListDatVeByIdTaiKhoan(user);
		DatVe datve = datveService.getNgayDatMoiNhat(user);
		if (datve != null) {
			model.addAttribute("lichsu", lichsuve);
			model.addAttribute("sohd", sohd.size());
			model.addAttribute("ngaydat", datve.getNGAYDAT());
			model.addAttribute("chuyengannhat", datve.getLICHTAUCHAY().getTUYEN().getTENTUYEN());
		} else {
			model.addAttribute("ngaydat", "");
			model.addAttribute("chuyengannhat", "");
			model.addAttribute("chuacove", "chuacove");
		}

		model.addAttribute("datve", datve);

		return "/user/thongtintaikhoan";
	}

	@RequestMapping("/user/chinhsuataikhoan/form")
	public String chinhsuatk(Model model) {
		Integer user = Integer.parseInt(session.getAttribute("user").toString());
		TaiKhoan taikhoan = taiKhoanService.findById(user);
		String xa = "Xã";
		String thitran = "Thị trấn";
		String phuong = "Phường";
		if(taikhoan.getDIACHI()!=null) {
		String chuoi = taikhoan.getDIACHI();
			if (chuoi.contains(phuong)) {
				// Tìm vị trí của chuỗi ", phường"
				int viTriChuoi = chuoi.indexOf(", Phường");
				String ketQua = chuoi.substring(0, viTriChuoi).trim();
				model.addAttribute("diachi", ketQua);
			} else if (chuoi.contains(xa)) {
				// Tìm vị trí của chuỗi ", Xã"
				int viTriChuoi = chuoi.indexOf(", Xã");
				String ketQua = chuoi.substring(0, viTriChuoi).trim();
				model.addAttribute("diachi", ketQua);
			} else if (chuoi.contains(thitran)) {
				// Tìm vị trí của chuỗi ", Thị Xã"
				int viTriChuoi = chuoi.indexOf(", Thị trấn");
				String ketQua = chuoi.substring(0, viTriChuoi).trim();
				model.addAttribute("diachi", ketQua);
			} else {
				model.addAttribute("diachi", taikhoan.getDIACHI());
			}
		}else {
			model.addAttribute("diachi", "");
		}
		

		model.addAttribute("user", taikhoan);

		return "/user/chinhsuataikhoan";
	}

	@PostMapping("/user/chinhsuataikhoan")
	public String capnhattaikhoan(Model model, @RequestParam String hovaten, @RequestParam String sdt,
			@RequestParam String cccd, @RequestParam String diachi) {

		// Lấy idhanhkhach bằng session
		Integer user = Integer.parseInt(session.getAttribute("user").toString());

		TaiKhoan taikhoan = taiKhoanService.findById(user);

		String thanhPho = request.getParameter("city");
		String quanHuyen = request.getParameter("district");
		String phuongXa = request.getParameter("ward");
		String diaChi = null;
		String diachi_old = taikhoan.getDIACHI();
		String thitran = "Thị trấn";
		String xa = "Xã";
		String phuong = "Phường";
		String chuoi = taikhoan.getDIACHI();
		String chuoichicodiachi = taikhoan.getDIACHI();
		String[] parts = chuoichicodiachi.split(", ");
		String chuoichicodiachi_dacat = "";

		int commaIndex = chuoichicodiachi.indexOf(", ");

		// Kiểm tra xem có vị trí của ", " không
		if (commaIndex != -1) {
			// Cắt chuỗi sau ", "
			chuoichicodiachi_dacat = chuoichicodiachi.substring(commaIndex + 2);

		} else {
			System.out.println("Không có chuỗi sau ', '");
		}
		if (hovaten.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || diachi.isEmpty()) {

			model.addAttribute("thongbaoerror", "...");
			model.addAttribute("ndungtbao", "Thông báo: Vui lòng điền đầy đủ thông tin!");
			model.addAttribute("user", taikhoan);
			return "/user/chinhsuataikhoan";
		} else {
			if (!diachi.isEmpty()) {
				if (thanhPho.isEmpty() || quanHuyen.isEmpty() || phuongXa.isEmpty()) {
					diaChi = diachi + ", " + chuoichicodiachi_dacat;
				} else {
					diaChi = diachi + ", " + phuongXa + ", " + quanHuyen + ", " + thanhPho;
				}
			} else if (diachi.isEmpty() && thanhPho.isEmpty()) {
				diaChi = diachi_old;
			} else {
				diaChi = diachi_old;
			}
			taikhoan.setHOVATEN(hovaten);
			taikhoan.setDIACHI(diaChi);
			taikhoan.setSDT(sdt);
			taikhoan.setCCCD(cccd);
			dao.save(taikhoan);

			TaiKhoan taikhoan_updated = taiKhoanService.findById(user);
			chuoi = taikhoan_updated.getDIACHI();
			System.out.println(chuoi);
			if (chuoi.contains(phuong)) {
				// Tìm vị trí của chuỗi ", phường"
				int viTriChuoi = chuoi.indexOf(", Phường");
				String ketQua = chuoi.substring(0, viTriChuoi).trim();
				model.addAttribute("diachi", ketQua);
			} else if (chuoi.contains(xa)) {
				// Tìm vị trí của chuỗi ", Xã"
				int viTriChuoi = chuoi.indexOf(", Xã");
				String ketQua = chuoi.substring(0, viTriChuoi).trim();
				model.addAttribute("diachi", ketQua);
			} else if (chuoi.contains(thitran)) {
				// Tìm vị trí của chuỗi ", Thị Xã"
				int viTriChuoi = chuoi.indexOf(", Thị trấn");
				String ketQua = chuoi.substring(0, viTriChuoi).trim();
				model.addAttribute("diachi", ketQua);
			} else {
				// Tìm vị trí của chuỗi ", "
				int viTriChuoi = chuoi.indexOf(", ");
				String ketQua = chuoi.substring(0, viTriChuoi).trim();
				model.addAttribute("diachi", ketQua);
			}

			model.addAttribute("thongbao", "Cập nhật thông tin thành công!");
			model.addAttribute("ndungtbao", "Thông báo: Thông tin của bạn đã được cập nhật!");

			model.addAttribute("user", taikhoan_updated);

			return "/user/chinhsuataikhoan";
		}

	}

	@RequestMapping("/admin/profile/form")
	public String chinhSuaTKQL(Model model) {
		Integer user = Integer.parseInt(session.getAttribute("user").toString());
		TaiKhoan taikhoan = taiKhoanService.findById(user);
		model.addAttribute("user", taikhoan);

		return "/admin/profile/profile";
	}

	@PostMapping("/admin/profile/form")
	public String updateTKQL(Model model, @RequestParam String hovaten, @RequestParam String sdt,
			@RequestParam String cccd, @RequestParam String diachi) {

		// Lấy idhanhkhach bằng session
		Integer user = Integer.parseInt(session.getAttribute("user").toString());

		TaiKhoan taikhoan = taiKhoanService.findById(user);

		String thanhPho = request.getParameter("city");
		String quanHuyen = request.getParameter("district");
		String phuongXa = request.getParameter("ward");
		String diaChi = null;
		String diachi_old = taikhoan.getDIACHI();

		if (!diachi.isEmpty()) {
			diaChi = diachi + ", " + phuongXa + ", " + quanHuyen + ", " + thanhPho;
		} else if (diachi.isEmpty() && thanhPho.isEmpty()) {
			diaChi = diachi_old;
		} else if (diachi.isEmpty() && thanhPho.isEmpty() && !phuongXa.isEmpty()) {
			diaChi = null;
		} else if (diachi.isEmpty() && thanhPho.isEmpty() && !quanHuyen.isEmpty()) {
			diaChi = null;
		} else {

			diaChi = phuongXa + ", " + quanHuyen + ", " + thanhPho;
		}

		if (hovaten.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || diaChi.isEmpty()) {
			model.addAttribute("ndungtbao1", "Vui lòng điền đầy đủ thông tin!");
			model.addAttribute("user", taikhoan);
			return "/admin/profile/profile";
		}
		try {
			taikhoan.setHOVATEN(hovaten);
			// hanhkhach.setEMAIL(kh.getEmail());
			taikhoan.setCCCD(cccd);
			taikhoan.setSDT(sdt);
			taikhoan.setDIACHI(diaChi);
			dao.save(taikhoan);

			// Lấy lại thông tin sau khi cập nhật
			TaiKhoan updatedTaiKhoan = taiKhoanService.findById(user);

			// Trả về trang profile kèm thông báo
			model.addAttribute("ndungtbao", "Cập nhật thành công!");
			model.addAttribute("user", updatedTaiKhoan);

			return "/admin/profile/profile";

		} catch (Exception e) {
			// Xử lý ngoại lệ
			model.addAttribute("message", "Có lỗi xảy ra, vui lòng thử lại!");
			return "/admin/profile/profile";
		}

	}

	@RequestMapping("/baomat")
	public String baomat(Model model) {
		return "/user/baomat";
	}

	@PostMapping("/xacthucmatkhau")
	public String xacthucbaomat(Model model, @RequestParam String matkhauhientai, @RequestParam String matkhaumoi,
			@RequestParam String matkhaumoi2) {
		TaiKhoan taikhoan = taiKhoanService.findById(Integer.parseInt(session.getAttribute("user").toString()));
		if (matkhauhientai.equals(matkhaumoi)) {
			model.addAttribute("error", "Mật khẩu mới không được trùng với mật khẩu cũ");
			return "/user/baomat";
		} else if (matkhaumoi.length() < 5) {
			model.addAttribute("error", "Mật khẩu mới phải lớn hơn 6 kí tự");
			return "/user/baomat";
		} else if (!matkhaumoi.equals(matkhaumoi2)) {
			model.addAttribute("error", "Mật khẩu xác nhận không đúng");
			return "/user/baomat";
		} else if (!taikhoan.getMATKHAU().equals(matkhauhientai)) {
			model.addAttribute("error", "Mật khẩu hiện tại không đúng");
			return "/user/baomat";
		} else {
			Random random = new Random();

			// Tạo số ngẫu nhiên có 4 chữ số
			int min = 100000; // Giới hạn dưới là 1000
			int max = 999999; // Giới hạn trên là 9999

			int randomNumber = random.nextInt(max - min + 1) + min;

			Context context = new Context();
			context.setVariable("message", randomNumber);

			emailService.sendEmailWithHtmlTemplate(taikhoan.getEMAIL(), "Yêu Cầu Cấp Lại Mật Khẩu",
					"/user/login/emailtemplates", context);

			// Lưu otp
			TaiKhoan taikhoanemail = taiKhoanService.findIdByEmailOrPhone(taikhoan.getEMAIL());
			OTP otp = new OTP();
			otp.setMAOTP(String.valueOf(randomNumber));
			otp.setTAIKHOAN(taikhoanemail);
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
				System.out.println("Xóa tất cả OTP lúc : " + formattedTime);
			}, 5, TimeUnit.MINUTES);

			// Đóng executorService sau khi đã sử dụng
			executorService.shutdown();
			session.setAttribute("iddoimk", taikhoan.getIDTAIKHOAN());
			session.setAttribute("createdpass", "true");
			session.setAttribute("pass", matkhaumoi);
			model.addAttribute("hoten", taikhoan.getHOVATEN());
			return "/user/login/layOTP";
		}
	}

	@PostMapping("/doiemail")
	public String doiemail(@RequestParam String email, Model model) {
		TaiKhoan taikhoan = taiKhoanService.findById(Integer.parseInt(session.getAttribute("user").toString()));
		if (taikhoan.getEMAIL().equals(email)) {
			model.addAttribute("erroremail", "Email mới không được trùng với email cũ");
			return "/user/baomat"; 
		}
		Random random = new Random();

		// Tạo số ngẫu nhiên có 4 chữ số
		int min = 100000; // Giới hạn dưới là 1000
		int max = 999999; // Giới hạn trên là 9999

		int randomNumber = random.nextInt(max - min + 1) + min;

		Context context = new Context();
		context.setVariable("message", randomNumber);

		emailService.sendEmailWithHtmlTemplate(taikhoan.getEMAIL(), "Yêu Cầu Cấp Lại Mật Khẩu",
				"/user/login/emailtemplates", context);

		// Lưu otp
		TaiKhoan taikhoanemail = taiKhoanService.findIdByEmailOrPhone(taikhoan.getEMAIL());
		OTP otp = new OTP();
		otp.setMAOTP(String.valueOf(randomNumber));
		otp.setTAIKHOAN(taikhoanemail);
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
			System.out.println("Xóa tất cả OTP lúc : " + formattedTime);
		}, 5, TimeUnit.MINUTES);

		// Đóng executorService sau khi đã sử dụng
		executorService.shutdown();
		session.setAttribute("createdpass", "true");
		session.setAttribute("email", email);
		model.addAttribute("hoten", taikhoan.getHOVATEN());
		return "/user/login/layOTP";
	}

	@GetMapping("/layDanhSachGheTheoMaDatVe/{maDatVe}")
	public ResponseEntity<List<DatGhe>> layDanhSachGheTheoMaDatVe(@PathVariable Integer maDatVe) {
		List<DatGhe> danhSachGhe = datGheDao.layDanhSachGheTheoMaDatVe(maDatVe);
		return ResponseEntity.ok(danhSachGhe);
	}
	
	@GetMapping("/layDanhSachNDCTheoMaDatVe/{maDatVe}")
	public ResponseEntity<List<NguoiDiCung>> layDanhSachNDCTheoMaDatVe(@PathVariable Integer maDatVe) {
		List<NguoiDiCung> nguoiDiCung = nguoiDiCungService.ListNguoiDiCungByiddatve(maDatVe);
		return ResponseEntity.ok(nguoiDiCung);
	}
	
	@GetMapping("/layTongTienVe/{maDatVe}")
	public ResponseEntity<HoaDon> layTongTienVe(@PathVariable Integer maDatVe) {
		HoaDon hoadon = hoaDonService.findByMaDatVe(maDatVe);
		return ResponseEntity.ok(hoadon);
	}
	


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}
}
