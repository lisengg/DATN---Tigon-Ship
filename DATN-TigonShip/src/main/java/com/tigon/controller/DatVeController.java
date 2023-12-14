package com.tigon.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.DatGheDAO;
import com.tigon.dao.DatVeDAO;
import com.tigon.dao.GheNgoiDAO;
import com.tigon.dao.HanhKhachDAO;
import com.tigon.dao.HanhKhachTamDAO;
import com.tigon.dao.HoaDonDAO;
import com.tigon.dao.LichTauChayDAO;
import com.tigon.dao.LoaiVeDAO;
import com.tigon.dao.NguoiDiCungDAO;
import com.tigon.dao.NguoiDiCungTamDAO;
import com.tigon.dao.TauDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.DatGhe;
import com.tigon.model.DatVe;
import com.tigon.model.GheNgoi;
import com.tigon.model.GiaVe;
import com.tigon.model.HanhKhach;
import com.tigon.model.HanhKhachTam;
import com.tigon.model.HoaDon;
import com.tigon.model.LichTauChay;
import com.tigon.model.LoaiHanhKhach;
import com.tigon.model.LoaiVe;
import com.tigon.model.NguoiDiCung;
import com.tigon.model.NguoiDiCungTam;
import com.tigon.model.TaiKhoan;
import com.tigon.model.Tau;
import com.tigon.model.Tuyen;
import com.tigon.service.DatGheService;
import com.tigon.service.DatVeService;
import com.tigon.service.GheNgoiService;
import com.tigon.service.GiaVeService;
import com.tigon.service.HanhKhachTamService;
import com.tigon.service.HoaDonService;
import com.tigon.service.LichTauService;
import com.tigon.service.LoaiHanhKhachService;
import com.tigon.service.LoaiVeService;
import com.tigon.service.NguoiDiCungTamService;
import com.tigon.service.TaiKhoanService;
import com.tigon.service.TauService;
import com.tigon.service.TuyenTauService;

@Controller
public class DatVeController {
	@Autowired
	TuyenDAO dao;
	@Autowired
	LichTauService lichTauService;
	@Autowired
	HoaDonDAO hddao;

	@Autowired
	HoaDonService hoaDonService;

	@Autowired
	TauService tauservice;

	@Autowired
	HanhKhachTamDAO hktamdao;

	@Autowired
	TuyenTauService ttservice;

	@Autowired
	TaiKhoanService tksv;

	@Autowired
	LoaiHanhKhachService lhksertvice;

	@Autowired
	GheNgoiDAO ghdao;

	@Autowired
	GheNgoiService ghservice;

	@Autowired
	DatGheDAO dgdao;

	@Autowired
	LichTauChayDAO daoLT;

	@Autowired
	DatVeDAO dvdao;

	@Autowired
	DatVeService dvservice;

	@Autowired
	NguoiDiCungTamService ndctsevice;

	@Autowired
	NguoiDiCungTamDAO hktdao;

	@Autowired
	LoaiVeDAO lvdao;

	@Autowired
	GheNgoiDAO ghndao;

	@Autowired
	NguoiDiCungDAO gdcdao;

	@Autowired
	HanhKhachTamService hktservice;

	@Autowired
	LichTauService ltservice;

	@Autowired
	HttpServletRequest servletRequest;

	@Autowired
	HttpSession session;

	@Autowired
	GheNgoiService gheService;

	@Autowired
	LoaiVeService loaiveService;

	@Autowired
	TuyenTauService tuyenService;

	@Autowired
	GiaVeService giaveService;

	@Autowired
	HanhKhachDAO hanhkdao;

	@Autowired
	DatGheService datGheService;

	@RequestMapping("/datve/timtuyen")
	public String tuyentau(Model model) {
		if (session.getAttribute("user") == null) {
			session.setAttribute("user", "hanhkhachmoi");
		}
		List<Tuyen> list = ttservice.findAll();
		model.addAttribute("items", list);
		session.setAttribute("tongtien", "0");
		hktdao.deleteAll();
		return "/user/TuyenTau";
	}

	@RequestMapping("/datve/tau")
	public String ttau(Model model, @RequestParam("NoOfPassenger") String songuoi) throws ParseException {
		session.setAttribute("songuoi", songuoi);
		// set tên tuyến + giờ đi, giờ về
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date ngayDi = dateFormat.parse(servletRequest.getParameter("NgayDi"));
		SimpleDateFormat dateFormatOutput = new SimpleDateFormat("dd/MM/yyyy");
		String ngayDiFormatted = dateFormatOutput.format(ngayDi);
		session.setAttribute("NgayDi", ngayDiFormatted);

		session.setAttribute("songuoi", songuoi);
		String TenTuyen = servletRequest.getParameter("TENTUYEN");
		session.setAttribute("TENTUYEN", TenTuyen);
		Tuyen timIdTuyen = tuyenService.findByTuyen(TenTuyen);
		session.setAttribute("idtuyen", timIdTuyen.getIDTUYEN());
		String[] parts = TenTuyen.split("\\s*-\\s*");
		String TenTuyenKhuHoi = parts[1] + " - " + parts[0];

		List<LichTauChay> lichtau = lichTauService.findByLichTau(timIdTuyen.getIDTUYEN());

		// kiểm tra loại vé, set session ngày về, loại vé
		int loaive;
		String NgayVe = servletRequest.getParameter("NgayVe");
		if (NgayVe == null) {
			session.setAttribute("NgayVe", "ko");
			loaive = 1;
			session.setAttribute("loaive", loaive);
		} else {
			loaive = 2;
			session.setAttribute("loaive", loaive);
			session.setAttribute("NgayVe", NgayVe);
			Tuyen timIdTuyenKhuhoi = tuyenService.findByTuyen(TenTuyenKhuHoi);
			session.setAttribute("idtuyenkhuhoi", timIdTuyenKhuhoi.getIDTUYEN());
		}

		// set giá vé
		GiaVe giave = giaveService.findByIdTuyenIdLoaiVe(timIdTuyen.getIDTUYEN(), loaive);

		GiaVe giavetr = giaveService.findByIdTuyenIdLoaiVeTreEm(timIdTuyen.getIDTUYEN(), loaive);

		// Định dạng lại số với DecimalFormat để bỏ hết số 0 sau dấu chấm
		DecimalFormat df = new DecimalFormat("###,###.##");
		String gia = df.format(giave.getGIA()) + "VND";
		String gia_treem = df.format(giavetr.getGIA()) + "VND";

		model.addAttribute("gia", gia);
		model.addAttribute("giatreem", gia_treem);

		Tau soluongGhe = tauservice.findById(lichtau.get(0).getTAU().getIDTAU());

		// set số lượng ghế đã đặt còn lại
		List<Integer> listSoLuongGhe = new ArrayList<>();
		List<Integer> listSoLuongGheDaDat = new ArrayList<>();
		List<Integer> listSoLuongGheConLai = new ArrayList<>();

		for (int i = 0; i < lichtau.size(); i++) {
			int soLuongGhe = soluongGhe.getSOGHE();

			listSoLuongGhe.add(i, soLuongGhe);

			int soLuongGheDaDat = datGheService.countDatGheTimTuyen(ngayDi, lichtau.get(i).getTAU().getIDTAU(),
					lichtau.get(i).getTUYEN().getIDTUYEN());
			listSoLuongGheDaDat.add(i, soLuongGheDaDat);

			int soLuongGheConLai = soLuongGhe - soLuongGheDaDat;
			listSoLuongGheConLai.add(i, soLuongGheConLai);
		}

		// set session giờ xuất phát, giờ đến nơi, id lịch tàu, id tàu
		for (int i = 0; i < lichtau.size(); i++) {
			session.setAttribute("gioxuatphat" + i, lichtau.get(i).getGIOXUATPHAT());
			session.setAttribute("giodennoi" + i, lichtau.get(i).getGIODENNOI());
			session.setAttribute("idlichtau" + i, lichtau.get(i).getIDLICHTAU());
			session.setAttribute("idtau" + i, lichtau.get(i).getTAU().getIDTAU());
		}
		session.setAttribute("daChonNgayVe", "false");
		model.addAttribute("soLuongGhe", listSoLuongGheConLai);
		model.addAttribute("ngayDi", ngayDiFormatted);
		model.addAttribute("lichTau", lichtau);

		return "/user/Ve";
	}

	@RequestMapping("datve/hanhkhach/{index}")
	public String hanhkhach(Model model, @PathVariable int index) {

		if (session.getAttribute("daChonNgayVe").toString().equals("true")) {
			session.setAttribute("index_ve", index);
			return "redirect:/datve/datgheve";
		}

		session.setAttribute("index", index);
		if (session.getAttribute("user").toString().equals("hanhkhachmoi")) {
			GiaVe giave = giaveService.findByIdTuyenIdLoaiVeTongTien(
					Integer.parseInt(session.getAttribute("idtuyen").toString()), 1,
					Integer.parseInt(session.getAttribute("loaive").toString()));
			System.out.println(giave.getGIA());
			session.setAttribute("tongtien", giave.getGIA());
		} else {
			TaiKhoan tk = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));
			model.addAttribute("username", tk.getHOVATEN());
			model.addAttribute("cccd", tk.getCCCD());
			model.addAttribute("email", tk.getEMAIL());
			model.addAttribute("SDT", tk.getSDT());
			model.addAttribute("date", tk.getNGAYSINH());
			GiaVe giave = giaveService.findByIdTuyenIdLoaiVeTongTien(
					Integer.parseInt(session.getAttribute("idtuyen").toString()), 1,
					Integer.parseInt(session.getAttribute("loaive").toString()));
			System.out.println(giave.getGIA());
			session.setAttribute("tongtien", giave.getGIA());
			System.out.println("id tai khoan:" + tk.getIDTAIKHOAN());
		}

		return "/user/ThongTinDatVe";
	}

	@RequestMapping("datve/datghechinh")
	public String ghengoitest(Model model) throws ParseException {
		String songuoistring = session.getAttribute("songuoi").toString();
		int songuoi = Integer.parseInt(songuoistring);
		model.addAttribute("songuoi", songuoi);

		int idtau = (int) session.getAttribute("idtau" + session.getAttribute("index").toString());

		List<GheNgoi> listduoi = ghndao.findByghekhoangduoi(idtau);
		model.addAttribute("items1", listduoi);
		List<GheNgoi> listtren = ghndao.findByghekhoangtren(idtau);
		model.addAttribute("items2", listtren);

		String ngayDiString = session.getAttribute("NgayDi").toString();
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date ngayDi = inputFormat.parse(ngayDiString);

		// Truy vấn danh sách IDGHE đã được đặt từ bảng DATGHE
		List<Integer> bookedSeats = dgdao.findBookedSeats(ngayDi); // Đổi tên phương thức và lớp DAO của bạn

		// Gửi danh sách IDGHE đã được đặt đến view
		model.addAttribute("bookedSeats", bookedSeats);

		return "/user/GheNgoi";
	}

	@RequestMapping("datve/datghe")
	public String ghengoi(Model model, @RequestParam("email") List<String> email,
			@RequestParam("cccd") List<String> cccd, @RequestParam("username") List<String> username,
			@RequestParam("date") List<String> date, @RequestParam("SDT") List<String> SDT,
			@RequestParam("nationality") List<String> nationality, @RequestParam("tickit") List<String> tickit)
			throws ParseException {

		String songuoistring = (String) session.getAttribute("songuoi");
		int songuoi = Integer.parseInt(songuoistring);
		model.addAttribute("songuoi", songuoi);
		int mahk;
		if (songuoistring != null) {
			try {

				System.out.println("Số người hành khách: " + songuoi);

				for (int i = 2; i <= songuoi; i++) {
					NguoiDiCungTam hkt = new NguoiDiCungTam();

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date ngaysinh = dateFormat.parse(date.get(i - 2));
					if (tickit.get(i - 2).equals("Trẻ Em")) {
						mahk = 2;

					} else {
						mahk = 1;

					}
					hkt.setHOVATEN(username.get(i - 2));
					hkt.setCCCD(cccd.get(i - 2));
					hkt.setSDT(SDT.get(i - 2));
					hkt.setIDLOAIKH(mahk);
					hkt.setQUOCTICH(nationality.get(i - 2));
					hkt.setNGAYSINH(ngaysinh);
					hktdao.save(hkt);
					GiaVe giave = giaveService.findByIdTuyenIdLoaiVeTongTien(
							Integer.parseInt(session.getAttribute("idtuyen").toString()), mahk,
							Integer.parseInt(session.getAttribute("loaive").toString()));
					try {
						Double tongtam = Double.parseDouble(giave.getGIA().toString());
						Double tongTien = Double.parseDouble(session.getAttribute("tongtien").toString());
						tongTien = tongtam + tongTien;
						System.out.println(tongtam);
						System.out.println(tongTien);
						session.setAttribute("tongtien", tongTien.toString());
					} catch (NumberFormatException e) {
						System.out.println("Lỗi tính tổng tiền");
					}

				}
			} catch (NumberFormatException e) {
				System.err.println("Lỗi chuyển đổi kiểu int: " + e.getMessage());
			}
		} else {
			System.err.println("Giá trị 'songuoi' từ session là null");
		}
		int idtau = (int) session.getAttribute("idtau" + session.getAttribute("index").toString());

		List<GheNgoi> listduoi = ghndao.findByghekhoangduoi(idtau);
		model.addAttribute("items1", listduoi);
		List<GheNgoi> listtren = ghndao.findByghekhoangtren(idtau);
		model.addAttribute("items2", listtren);

		String ngayDiString = session.getAttribute("NgayDi").toString();
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date ngayDi = inputFormat.parse(ngayDiString);

		// Truy vấn danh sách IDGHE đã được đặt từ bảng DATGHE
		List<Integer> bookedSeats = dgdao.findBookedSeats(ngayDi); // Đổi tên phương thức và lớp DAO của bạn

		// Gửi danh sách IDGHE đã được đặt đến view
		model.addAttribute("bookedSeats", bookedSeats);

		return "/user/GheNgoi";
	}

	@RequestMapping("/datve/HanhKhachDiCung")
	public String Hanhkhach(Model model, @RequestParam String username, @RequestParam String cccd,
			@RequestParam String email, @RequestParam String SDT, @RequestParam String nationality,
			@RequestParam String date, HanhKhachTam hktam) throws ParseException {
		if (session.getAttribute("user").toString().equals("hanhkhachmoi")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date ngaysinh = dateFormat.parse(date);

			System.out.println(ngaysinh);

			hktam.setHOVATEN(username);
			hktam.setCCCD(cccd);
			hktam.setSDT(SDT);
			hktam.setEMAIL(email);
			hktam.setQUOCTICH(nationality);
			hktam.setNGAYSINH(ngaysinh);

			hktamdao.save(hktam);
			GiaVe giave = giaveService
					.findByIdTuyenIdLoaiVe(Integer.parseInt(session.getAttribute("idtuyen").toString()), 1);
		} else {
			TaiKhoan tk = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));

			session.setAttribute("idtaikhoan", tk.getIDTAIKHOAN());
			GiaVe giave = giaveService
					.findByIdTuyenIdLoaiVe(Integer.parseInt(session.getAttribute("idtuyen").toString()), 1);
		}

		String songuoi = (String) session.getAttribute("songuoi");
		if (songuoi.equals("1")) {
			return "redirect:/datve/datghechinh";
		} else {
			return "/user/HanhkhachDiCung";
		}
	}

	@PostMapping("user/dat")
	public String dat(Model model, @RequestParam(name = "selectedSeats") String selectedSeats, DatVe datve)
			throws ParseException {

		String[] selectedSeatsArray = selectedSeats.split(", ");
		if (session.getAttribute("daChonNgayVe").equals("false")) {

			// Duyệt qua mỗi ghế và in ra thông tin
			for (int i = 0; i < selectedSeatsArray.length; i++) {
				String GHENGOI = selectedSeatsArray[i].trim();
				// Chuyển đổi chuỗi thành int
				int intValue = Integer.parseInt(GHENGOI);

				GheNgoi ghe = ghservice.findByid(intValue);
				session.setAttribute("ghengoi" + (i + 1), ghe.getIDGHE());

			}
		} else {
			// Duyệt qua mỗi ghế và in ra thông tin
			for (int i = 0; i < selectedSeatsArray.length; i++) {
				String GHENGOI = selectedSeatsArray[i].trim();
				// Chuyển đổi chuỗi thành int
				int intValue = Integer.parseInt(GHENGOI);

				GheNgoi ghe = ghservice.findByid(intValue);
				session.setAttribute("ghengoi_ve" + (i + 1), ghe.getIDGHE());

			}
		}

		return "/user/ThongTinDatVe";
	}

	@RequestMapping("/thanhtoan")
	public String thanhtoan(Model model) {
		if (!session.getAttribute("NgayVe").equals("ko") && session.getAttribute("daChonNgayVe").equals("false")) {
			return "redirect:/datve/tauve";
		}
		if (session.getAttribute("user").toString().equals("hanhkhachmoi")) {
			List<HanhKhachTam> listHK = hktamdao.findAll();
			model.addAttribute("hoten", listHK.get(0).getHOVATEN());
			model.addAttribute("email", listHK.get(0).getEMAIL());
			model.addAttribute("sdt", listHK.get(0).getSDT());
		} else {
			TaiKhoan taikhoan = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));
			model.addAttribute("hoten", taikhoan.getHOVATEN());
			model.addAttribute("email", taikhoan.getEMAIL());
			model.addAttribute("sdt", taikhoan.getSDT());
		}
		// Nguoi di cung

		List<NguoiDiCungTam> ndtam = ndctsevice.findAll();
		model.addAttribute("ndtam", ndtam);

		// Lấy danh sách ghế chuyến đi
		String soghe = session.getAttribute("ghengoi1").toString();
		GheNgoi ghe = gheService.findByid(Integer.parseInt(soghe));
		String listtenghe = ghe.getTENGHE();
		if (Integer.parseInt(session.getAttribute("songuoi").toString()) > 1) {
			for (int i = 2; i <= Integer.parseInt(session.getAttribute("songuoi").toString()); i++) {
				ghe = gheService.findByid(Integer.parseInt(session.getAttribute("ghengoi" + i).toString()));
				listtenghe = listtenghe + "," + ghe.getTENGHE();
			}
		}

		LoaiVe loaive = loaiveService.findById(Integer.parseInt(session.getAttribute("loaive").toString()));

		model.addAttribute("giodennoi", session.getAttribute("giodennoi" + session.getAttribute("index")));
		model.addAttribute("gioxuatphat", session.getAttribute("gioxuatphat" + session.getAttribute("index")));
		model.addAttribute("loaive", loaive.getLOAIVE());
		model.addAttribute("soghe", listtenghe);

		LocalDateTime now = LocalDateTime.now(); // Lấy thời gian hiện tại
		model.addAttribute("temporal", now);

		if (session.getAttribute("daChonNgayVe").equals("true")) {
			// Lấy danh sách ghế chuyến về
			String soghe_ve = session.getAttribute("ghengoi_ve1").toString();
			GheNgoi ghe_ve = gheService.findByid(Integer.parseInt(soghe_ve));
			String listtenghe_ve = ghe_ve.getTENGHE();

			if (Integer.parseInt(session.getAttribute("songuoi").toString()) > 1) {
				for (int i = 2; i <= Integer.parseInt(session.getAttribute("songuoi").toString()); i++) {
					ghe = gheService.findByid(Integer.parseInt(session.getAttribute("ghengoi_ve" + i).toString()));
					listtenghe_ve = listtenghe_ve + "," + ghe_ve.getTENGHE();
				}
			}
			model.addAttribute("coNgayVe", "co");
			model.addAttribute("giodennoi_ve", session.getAttribute("giodennoi_ve" + session.getAttribute("index")));
			model.addAttribute("gioxuatphat_ve",
					session.getAttribute("gioxuatphat_ve" + session.getAttribute("index")));
			model.addAttribute("loaive", loaive.getLOAIVE());
			model.addAttribute("soghe_ve", listtenghe_ve);
		}

		// Lấy chuỗi từ session
		String tongtienStr = session.getAttribute("tongtien").toString();

		// Chuyển đổi chuỗi thành số
		double tongtienDouble = Double.parseDouble(tongtienStr);

		// Định dạng lại số với DecimalFormat để bỏ hết số 0 sau dấu chấm
		DecimalFormat df = new DecimalFormat("###,###.##");
		String tongtien = df.format(tongtienDouble) + "đ";
		model.addAttribute("tuyen", session.getAttribute("TENTUYEN"));
		model.addAttribute("tongtien", tongtien);
		return "/user/datve/thanhtoan";
	}

	@RequestMapping("/thanhtoanthanhcong")
	public String thanhcong(Model model) throws ParseException {

		String soghe = session.getAttribute("ghengoi1").toString();

		GheNgoi ghe = gheService.findByid(Integer.parseInt(soghe));
		String listtenghe = ghe.getTENGHE();
		if (Integer.parseInt(session.getAttribute("songuoi").toString()) > 1) {
			for (int i = 2; i <= Integer.parseInt(session.getAttribute("songuoi").toString()); i++) {
				ghe = gheService.findByid(Integer.parseInt(session.getAttribute("ghengoi" + i).toString()));
				listtenghe = listtenghe + "," + ghe.getTENGHE();
			}
		}
		// Lấy chuỗi từ session
		String tongtienStr = session.getAttribute("tongtien").toString();

		// Chuyển đổi chuỗi thành số
		double tongtienDouble = Double.parseDouble(tongtienStr);

		// Định dạng lại số với DecimalFormat để bỏ hết số 0 sau dấu chấm
		DecimalFormat df = new DecimalFormat("###,###.##");
		String tongtien = df.format(tongtienDouble) + "đ";

		model.addAttribute("tuyen", "Tên tuyến: " + session.getAttribute("TENTUYEN"));
		model.addAttribute("soghe", "Số ghế: " + listtenghe);
		model.addAttribute("tongtien", "Tổng tiền: " + tongtien);

		// Lưu Đặt vé

		if (session.getAttribute("user").toString().equals("hanhkhachmoi")) {
			List<HanhKhachTam> listHK = hktamdao.findAll();
			model.addAttribute("hoten", "Xin chào " + listHK.get(0).getHOVATEN() + ",");
			String ngayve = session.getAttribute("NgayVe").toString();

			List<HanhKhachTam> hktam = hktservice.findAll();
		//Lưu hành khách
			HanhKhach hk = new HanhKhach();

			hk.setCCCD(hktam.get(0).getCCCD());
			hk.setEMAIL(hktam.get(0).getEMAIL());
			hk.setHOVATEN(hktam.get(0).getHOVATEN());
			hk.setNGAYSINH(hktam.get(0).getNGAYSINH());
			hk.setQUOCTICH(hktam.get(0).getQUOCTICH());
			hk.setSDT(hktam.get(0).getSDT());

			hanhkdao.save(hk);

			hktamdao.deleteById(hktam.get(0).getIDHKTAM());

			HanhKhach idkh = hanhkdao.FINDIDHKMAX();

			int idlichtau = Integer
					.parseInt(session.getAttribute("idlichtau" + session.getAttribute("index")).toString());

			String ngaydi = session.getAttribute("NgayDi").toString();
			// Đặt định dạng phù hợp với chuỗi ngày
			SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date datedi = inputFormat.parse(ngaydi);

			LichTauChay lt = ltservice.findByid(idlichtau);

			Integer loaiv = (Integer) session.getAttribute("loaive");

			LoaiVe idlv = loaiveService.findById(loaiv);

			Integer songuoi = Integer.parseInt(session.getAttribute("songuoi").toString());

			// Lưu đặt vé
			Date ngaydat = new Date();
			DatVe dv = new DatVe();

			dv.setHANHKHACH(idkh);
			dv.setLICHTAUCHAY(lt);
			dv.setNGAYDI(datedi);
			if (ngayve.equalsIgnoreCase("ko")) {
				dv.setNGAYVE(null);
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date ngayVeDate = sdf.parse(ngayve);
				dv.setNGAYVE(ngayVeDate);
			}
			dv.setNGAYDAT(ngaydat);
			dv.setLOAIVE(idlv);
			dv.setSOGHE(songuoi);

			dvdao.save(dv);

			// Lưu đặt ghế
			for (int i = 1; i <= songuoi; i++) {
				DatGhe dg = new DatGhe();

				int intValue = Integer.parseInt(session.getAttribute("ghengoi" + i).toString());
				GheNgoi gh = gheService.findByid(intValue);

				DatVe datve = dvservice.FINDIDMAX();

				dg.setIDTUYEN(session.getAttribute("idtuyen").toString());
				dg.setDATVE(datve);
				dg.setGHENGOI(gh);
				dg.setTHOIGIAN(datedi);
				dg.setIDTAU(Integer.parseInt(session.getAttribute("idtau" + session.getAttribute("index")).toString()));
				dgdao.save(dg);

				System.out.println("ghe thanh cong:");
			}

			// Lưu người đi cùng
			DatVe datve = dvservice.FINDIDMAX();
			List<NguoiDiCungTam> ndtam = ndctsevice.findAll();
			for (int i = 0; i < songuoi && i < ndtam.size(); i++) {

				DatVe dvtam = dvservice.findById(datve.getMADATVE());
				LoaiHanhKhach lhk = lhksertvice.findByid(ndtam.get(i).getIDLOAIKH());

				NguoiDiCung ngdicung = new NguoiDiCung();

				ngdicung.setHOVATEN(ndtam.get(i).getHOVATEN());
				ngdicung.setCCCD(ndtam.get(i).getCCCD());
				ngdicung.setSDT(ndtam.get(i).getSDT());
				ngdicung.setDATVE(dvtam);
				ngdicung.setLOAIHK(lhk);
				ngdicung.setQUOCTICH(ndtam.get(i).getQUOCTICH());
				ngdicung.setNGAYSINH(ndtam.get(i).getNGAYSINH());

				gdcdao.save(ngdicung);
			}

			// Lưu hóa đơn
			HoaDon hd = new HoaDon();

			Object tongTienObject = session.getAttribute("tongtien");
			BigDecimal tongTienBigDecimal = new BigDecimal(tongTienObject.toString());
			BigDecimal nuaGiaTri = tongTienBigDecimal.divide(new BigDecimal("2"));
			hd.setDATVE(datve);
			if (session.getAttribute("daChonNgayVe").equals("true")) {
				hd.setTONGTIEN(nuaGiaTri);
			} else {
				hd.setTONGTIEN(tongTienBigDecimal);
			}
			hd.setNGAYLAP(ngaydat);
			hd.setTRANGTHAI("Đã thanh toán");
			hd.setLOAITHANHTOAN("VN PAY");

			hddao.save(hd);
			HoaDon hdmax = hoaDonService.findMaxDatVe();
			session.setAttribute("mahoadon", hdmax.getMAHD());

			// Lưu đặt vé về
			if (session.getAttribute("daChonNgayVe").equals("true")) {
				LichTauChay lt_ve = ltservice
						.findByid(Integer.parseInt(session.getAttribute("idlichtau_ve"+session.getAttribute("index_ve").toString()).toString()));
				DatVe dv_ve = new DatVe();
				dv_ve.setHANHKHACH(idkh);
				dv_ve.setLICHTAUCHAY(lt_ve);
				dv_ve.setNGAYDI(datedi);
				if (ngayve.equalsIgnoreCase("ko")) {
					dv_ve.setNGAYVE(null);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date ngayVeDate = sdf.parse(ngayve);
					dv.setNGAYVE(ngayVeDate);
				}
				dv_ve.setNGAYDAT(ngaydat);
				dv_ve.setLOAIVE(idlv);
				dv_ve.setSOGHE(songuoi);

				dvdao.save(dv_ve);

				// Lưu đặt ghế
				DatVe datve_ve = dvservice.FINDIDMAX();
				for (int i = 1; i <= songuoi; i++) {
					DatGhe dg = new DatGhe();

					int intValue = Integer.parseInt(session.getAttribute("ghengoi_ve" + i).toString());
					GheNgoi gh = gheService.findByid(intValue);
					dg.setIDTUYEN(session.getAttribute("idtuyenkhuhoi").toString());
					dg.setDATVE(datve_ve);
					dg.setGHENGOI(gh);
					dg.setTHOIGIAN(datedi);
					dg.setIDTAU(Integer
							.parseInt(session.getAttribute("idtau_ve" + session.getAttribute("index")).toString()));
					dgdao.save(dg);

					System.out.println("ghe ve thanh cong:");
				}

				for (int i = 0; i < songuoi && i < ndtam.size(); i++) {

					DatVe dvtam = dvservice.findById(datve_ve.getMADATVE());
					LoaiHanhKhach lhk = lhksertvice.findByid(ndtam.get(i).getIDLOAIKH());

					NguoiDiCung ngdicung = new NguoiDiCung();

					ngdicung.setHOVATEN(ndtam.get(i).getHOVATEN());
					ngdicung.setCCCD(ndtam.get(i).getCCCD());
					ngdicung.setSDT(ndtam.get(i).getSDT());
					ngdicung.setDATVE(dvtam);
					ngdicung.setLOAIHK(lhk);
					ngdicung.setQUOCTICH(ndtam.get(i).getQUOCTICH());
					ngdicung.setNGAYSINH(ndtam.get(i).getNGAYSINH());

					gdcdao.save(ngdicung);
					
				}

				// Lưu hóa đơn
				HoaDon hd_ve = new HoaDon();
				hd_ve.setDATVE(datve_ve);
				hd_ve.setTONGTIEN(nuaGiaTri);
				hd_ve.setNGAYLAP(ngaydat);
				hd_ve.setTRANGTHAI("Đã thanh toán");
				hd_ve.setLOAITHANHTOAN("VN PAY");
				hddao.save(hd);
				
				HoaDon hdmax_ve = hoaDonService.findMaxDatVe();
				session.setAttribute("mahoadon_ve", hdmax_ve.getMAHD());
				
			}
			hktdao.deleteAll();
			System.out.println("ko có id");
		} else {

			TaiKhoan tk = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));
			model.addAttribute("hoten", "Xin chào " + tk.getHOVATEN() + ",");
			String ngayve = session.getAttribute("NgayVe").toString();
			
			int idlichtau = Integer
					.parseInt(session.getAttribute("idlichtau" + session.getAttribute("index").toString()).toString());

			String ngaydi = session.getAttribute("NgayDi").toString();

			// Đặt định dạng phù hợp với chuỗi ngày
			SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date datedi = inputFormat.parse(ngaydi);


			LichTauChay lt = ltservice.findByid(idlichtau);

			Integer loaiv = (Integer) session.getAttribute("loaive");

			LoaiVe idlv = loaiveService.findById(loaiv);

			Integer songuoi = Integer.parseInt(session.getAttribute("songuoi").toString());

			Date ngaydat = new Date();
			DatVe dv = new DatVe();
			dv.setTAIKHOAN(tk);
			dv.setLICHTAUCHAY(lt);
			dv.setNGAYDI(datedi);
			if (ngayve.equalsIgnoreCase("ko")) {
				dv.setNGAYVE(null);
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date ngayVeDate = sdf.parse(ngayve);
				dv.setNGAYVE(ngayVeDate);
			}
			dv.setNGAYDAT(ngaydat);
			dv.setLOAIVE(idlv);
			dv.setSOGHE(songuoi);

			dvdao.save(dv);

			for (int i = 1; i <= songuoi; i++) {
				DatGhe dg = new DatGhe();

				int intValue = Integer.parseInt(session.getAttribute("ghengoi" + i).toString());
				GheNgoi gh = gheService.findByid(intValue);

				DatVe datve = dvservice.FINDIDMAX();

				dg.setIDTUYEN(session.getAttribute("idtuyen").toString());
				dg.setDATVE(datve);
				dg.setGHENGOI(gh);
				dg.setTHOIGIAN(datedi);
				dg.setIDTAU(Integer.parseInt(session.getAttribute("idtau" + session.getAttribute("index")).toString()));
				// dg.setTHOIGIAN(now.toString());)
				dgdao.save(dg);

				System.out.println("ghe thanh cong:");
			}
			DatVe datve = dvservice.FINDIDMAX();
			List<NguoiDiCungTam> ndtam = ndctsevice.findAll();
			for (int i = 0; i < songuoi && i < ndtam.size(); i++) {

				DatVe dvtam = dvservice.findById(datve.getMADATVE());
				LoaiHanhKhach lhk = lhksertvice.findByid(ndtam.get(i).getIDLOAIKH());

				NguoiDiCung ngdicung = new NguoiDiCung();

				ngdicung.setHOVATEN(ndtam.get(i).getHOVATEN());
				ngdicung.setCCCD(ndtam.get(i).getCCCD());
				ngdicung.setSDT(ndtam.get(i).getSDT());
				ngdicung.setDATVE(dvtam);
				ngdicung.setLOAIHK(lhk);
				ngdicung.setQUOCTICH(ndtam.get(i).getQUOCTICH());
				ngdicung.setNGAYSINH(ndtam.get(i).getNGAYSINH());
				System.out.println("----------------------");

				gdcdao.save(ngdicung);
				hktdao.deleteById(ndtam.get(i).getIDTAM());

				System.out.println("Thanh Cong");
			}
			HoaDon hd = new HoaDon();

			Object tongTienObject = session.getAttribute("tongtien");
			BigDecimal tongTienBigDecimal = new BigDecimal(tongTienObject.toString());
			BigDecimal nuaGiaTri = tongTienBigDecimal.divide(new BigDecimal("2"));
			hd.setDATVE(datve);
			hd.setTONGTIEN(nuaGiaTri);
			hd.setNGAYLAP(ngaydat);
			hd.setTRANGTHAI("Đã thanh toán");
			hd.setLOAITHANHTOAN("Xác thực");

			hddao.save(hd);
			HoaDon hdmax = hoaDonService.findMaxDatVe();
			session.setAttribute("mahoadon", hdmax.getMAHD());
			// Lưu đặt vé về
						if (session.getAttribute("daChonNgayVe").equals("true")) {
							LichTauChay lt_ve = ltservice
									.findByid(Integer.parseInt(session.getAttribute("idlichtau_ve"+session.getAttribute("index_ve").toString()).toString()));
							DatVe dv_ve = new DatVe();
							dv_ve.setTAIKHOAN(tk);
							dv_ve.setLICHTAUCHAY(lt_ve);
							dv_ve.setNGAYDI(datedi);
							if (ngayve.equalsIgnoreCase("ko")) {
								dv_ve.setNGAYVE(null);
							} else {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date ngayVeDate = sdf.parse(ngayve);
								dv.setNGAYVE(ngayVeDate);
							}
							dv_ve.setNGAYDAT(ngaydat);
							dv_ve.setLOAIVE(idlv);
							dv_ve.setSOGHE(songuoi);

							dvdao.save(dv_ve);

							// Lưu đặt ghế
							DatVe datve_ve = dvservice.FINDIDMAX();
							for (int i = 1; i <= songuoi; i++) {
								DatGhe dg = new DatGhe();

								int intValue = Integer.parseInt(session.getAttribute("ghengoi_ve" + i).toString());
								GheNgoi gh = gheService.findByid(intValue);
								dg.setIDTUYEN(session.getAttribute("idtuyenkhuhoi").toString());
								dg.setDATVE(datve_ve);
								dg.setGHENGOI(gh);
								dg.setTHOIGIAN(datedi);
								dg.setIDTAU(Integer
										.parseInt(session.getAttribute("idtau_ve" + session.getAttribute("index")).toString()));
								dgdao.save(dg);

								System.out.println("ghe ve thanh cong:");
							}

							for (int i = 0; i < songuoi && i < ndtam.size(); i++) {

								DatVe dvtam = dvservice.findById(datve_ve.getMADATVE());
								LoaiHanhKhach lhk = lhksertvice.findByid(ndtam.get(i).getIDLOAIKH());

								NguoiDiCung ngdicung = new NguoiDiCung();

								ngdicung.setHOVATEN(ndtam.get(i).getHOVATEN());
								ngdicung.setCCCD(ndtam.get(i).getCCCD());
								ngdicung.setSDT(ndtam.get(i).getSDT());
								ngdicung.setDATVE(dvtam);
								ngdicung.setLOAIHK(lhk);
								ngdicung.setQUOCTICH(ndtam.get(i).getQUOCTICH());
								ngdicung.setNGAYSINH(ndtam.get(i).getNGAYSINH());

								gdcdao.save(ngdicung);
								
							}

							// Lưu hóa đơn
							HoaDon hd_ve = new HoaDon();
							hd_ve.setDATVE(datve_ve);
							hd_ve.setTONGTIEN(nuaGiaTri);
							hd_ve.setNGAYLAP(ngaydat);
							hd_ve.setTRANGTHAI("Đã thanh toán");
							hd_ve.setLOAITHANHTOAN("Xác thực");
							hddao.save(hd_ve);
							
							HoaDon hdmax_ve = hoaDonService.findMaxDatVe();
							session.setAttribute("mahoadon_ve", hdmax_ve.getMAHD());
						}
						
		}

		return "/user/datve/chuyenvetrangchu";
	}

	@RequestMapping("/datve/tauve")
	public String tauve(Model model) throws ParseException {
		int idtuyen = Integer.parseInt(session.getAttribute("idtuyenkhuhoi").toString());
		List<LichTauChay> lichtau = lichTauService.findByLichTau(idtuyen);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date ngayVe = dateFormat.parse(session.getAttribute("NgayDi").toString());
		String ngayVeFormatted = dateFormat.format(ngayVe);

		// set giá vé
		GiaVe giave = giaveService.findByIdTuyenIdLoaiVe(idtuyen, 1);

		GiaVe giavetr = giaveService.findByIdTuyenIdLoaiVeTreEm(idtuyen, 1);

		// Định dạng lại số với DecimalFormat để bỏ hết số 0 sau dấu chấm
		DecimalFormat df = new DecimalFormat("###,###.##");
		String gia = df.format(giave.getGIA()) + "VND";
		String gia_treem = df.format(giavetr.getGIA()) + "VND";

		model.addAttribute("gia", gia);
		model.addAttribute("giatreem", gia_treem);

		Tau soluongGhe = tauservice.findById(lichtau.get(0).getTAU().getIDTAU());

		// set số lượng ghế đã đặt còn lại
		List<Integer> listSoLuongGhe = new ArrayList<>();
		List<Integer> listSoLuongGheDaDat = new ArrayList<>();
		List<Integer> listSoLuongGheConLai = new ArrayList<>();

		for (int i = 0; i < lichtau.size(); i++) {
			int soLuongGhe = soluongGhe.getSOGHE();

			listSoLuongGhe.add(i, soLuongGhe);

			int soLuongGheDaDat = datGheService.countDatGheTimTuyen(ngayVe, lichtau.get(i).getTAU().getIDTAU(),
					lichtau.get(i).getTUYEN().getIDTUYEN());
			listSoLuongGheDaDat.add(i, soLuongGheDaDat);

			int soLuongGheConLai = soLuongGhe - soLuongGheDaDat;
			listSoLuongGheConLai.add(i, soLuongGheConLai);
		}

		// set session giờ xuất phát, giờ đến nơi, id lịch tàu, id tàu chuyến về
		for (int i = 0; i < lichtau.size(); i++) {
			session.setAttribute("gioxuatphat_ve" + i, lichtau.get(i).getGIOXUATPHAT());
			session.setAttribute("giodennoi_ve" + i, lichtau.get(i).getGIODENNOI());
			session.setAttribute("idlichtau_ve" + i, lichtau.get(i).getIDLICHTAU());
			session.setAttribute("idtau_ve" + i, lichtau.get(i).getTAU().getIDTAU());
		}
		session.setAttribute("daChonNgayVe", "true");
		
		model.addAttribute("soLuongGhe", listSoLuongGheConLai);
		model.addAttribute("ngayDi", ngayVeFormatted);
		model.addAttribute("lichTau", lichtau);
		return "/user/Ve";
	}

	@RequestMapping("datve/datgheve")
	public String ghengoive(Model model) throws ParseException {
		String songuoistring = session.getAttribute("songuoi").toString();
		int songuoi = Integer.parseInt(songuoistring);
		model.addAttribute("songuoi", songuoi);

		int idtau = (int) session.getAttribute("idtau_ve" + session.getAttribute("index_ve").toString());

		List<GheNgoi> listduoi = ghndao.findByghekhoangduoi(idtau);
		model.addAttribute("items1", listduoi);
		List<GheNgoi> listtren = ghndao.findByghekhoangtren(idtau);
		model.addAttribute("items2", listtren);

		String ngayDiString = session.getAttribute("NgayDi").toString();
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date ngayDi = inputFormat.parse(ngayDiString);

		// Truy vấn danh sách IDGHE đã được đặt từ bảng DATGHE
		List<Integer> bookedSeats = dgdao.findBookedSeats(ngayDi); // Đổi tên phương thức và lớp DAO của bạn

		// Gửi danh sách IDGHE đã được đặt đến view
		model.addAttribute("bookedSeats", bookedSeats);

		return "/user/GheNgoi";
	}
}
