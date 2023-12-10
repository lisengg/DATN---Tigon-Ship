package com.tigon.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.tigon.model.Tuyen;
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
import com.tigon.service.TuyenTauService;

@Controller
public class DatVeController {
	@Autowired
	TuyenDAO dao;
	@Autowired
	HoaDonDAO hddao;
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
	HoaDonService hoadonService;

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

	@RequestMapping("/datve/timtuyen")
	public String tuyentau(Model model) {
		if (session.getAttribute("user") == null) {
			session.setAttribute("user", "hanhkhachmoi");
		}
		List<Tuyen> list = ttservice.findAll();
		model.addAttribute("items", list);
		session.setAttribute("tongtien", "0");
		hktdao.deleteAll();
		hktamdao.deleteAll();
		return "/user/TuyenTau";
	}

	@RequestMapping("/datve/tuyentau")
	public String table(Model model, @RequestParam("NoOfPassenger") String songuoi) {
		session.setAttribute("songuoi", songuoi);
		String TenTuyen = servletRequest.getParameter("TENTUYEN");

		Tuyen searchResults = ttservice.findByTuyen(TenTuyen);
		int idtuyen = searchResults.getIDTUYEN();
		session.setAttribute("idtuyen", idtuyen);

		String NgayDi = servletRequest.getParameter("NgayDi");
		session.setAttribute("NgayDi", NgayDi);

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
		}
		System.out.println("Loai ve: " + session.getAttribute("loaive").toString());

		GiaVe giave = giaveService.findByIdTuyenIdLoaiVe(idtuyen, loaive);

		GiaVe giavetr = giaveService.findByIdTuyenIdLoaiVeTreEm(idtuyen, loaive);

		// Định dạng lại số với DecimalFormat để bỏ hết số 0 sau dấu chấm
		DecimalFormat df = new DecimalFormat("###,###.##");
		String tongtien = df.format(giave.getGIA()) + "VND";
		String tientre = df.format(giavetr.getGIA()) + "VND";

		model.addAttribute("gia", tongtien);

		model.addAttribute("giatr", tientre);

		String TENTUYEN = servletRequest.getParameter("TENTUYEN");
		session.setAttribute("TENTUYEN", TENTUYEN);

		LichTauChay search = ltservice.findByLichTau(idtuyen);
		int idlichtau = search.getIDLICHTAU();
		int idtau = search.getTAU().getIDTAU();

		String gioxuatphat = search.getGIOXUATPHAT();
		String giodennoi = search.getGIODENNOI();
		session.setAttribute("gioxuatphat", gioxuatphat);
		session.setAttribute("giodennoi", giodennoi);
		session.setAttribute("idtau", idtau);

		session.setAttribute("idlichtau", search.getIDLICHTAU());

		String searchResults2 = session.getAttribute("TENTUYEN").toString();
		model.addAttribute("Ten", searchResults2);
		model.addAttribute("items", search);

		return "/user/VeTau";
	}

	@RequestMapping("datve/datghe")
	public String ghengoi(Model model, @RequestParam("email") List<String> email,
			@RequestParam("cccd") List<String> cccd, @RequestParam("username") List<String> username,
			@RequestParam("date") List<String> date, @RequestParam("SDT") List<String> SDT,
			@RequestParam("nationality") List<String> nationality, @RequestParam("tickit") List<String> tickit)
			throws ParseException {

		String songuoistring = (String) session.getAttribute("songuoi");
		int songuoi = Integer.parseInt(songuoistring);
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
		int idtau = (int) session.getAttribute("idtau");
		model.addAttribute("songuoi", songuoi);
		List<GheNgoi> listduoi = ghndao.findByghekhoangduoi(idtau);
		model.addAttribute("items1", listduoi);
		List<GheNgoi> listtren = ghndao.findByghekhoangtren(idtau);
		model.addAttribute("items2", listtren);

		// Truy vấn danh sách IDGHE đã được đặt từ bảng DATGHE
		List<Integer> bookedSeats = dgdao.findBookedSeats(); // Đổi tên phương thức và lớp DAO của bạn

		// Gửi danh sách IDGHE đã được đặt đến view
		model.addAttribute("bookedSeats", bookedSeats);
		return "/user/Teste";
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
			session.setAttribute("tongtien", giave.getGIA());
		} else {
			TaiKhoan tk = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));
			// session.setAttribute("idtaikhoan", tk.getIDTAIKHOAN());
			// )
			// model.addAttribute("username", tk.getHOVATEN());
			session.setAttribute("idtaikhoan", tk.getIDTAIKHOAN());
			GiaVe giave = giaveService
					.findByIdTuyenIdLoaiVe(Integer.parseInt(session.getAttribute("idtuyen").toString()), 1);
			session.setAttribute("tongtien", giave.getGIA());
		}

		String songuoi = (String) session.getAttribute("songuoi");
		System.out.println("So nguoi " + songuoi);
		if (songuoi.equals("1")) {
			return "redirect:/datve/datghechinh";
		} else {
			return "/user/HanhkhachDiCung";
		}
	}

	@RequestMapping("datve/hanhkhach")
	public String hanhkhach(Model model) {
		if (session.getAttribute("user").toString().equals("hanhkhachmoi")) {

			System.out.println("ko có id");
		} else {
			TaiKhoan tk = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));

			//
			model.addAttribute("username", tk.getHOVATEN());
			model.addAttribute("cccd", tk.getCCCD());
			model.addAttribute("email", tk.getEMAIL());
			model.addAttribute("SDT", tk.getSDT());
			model.addAttribute("date", tk.getNGAYSINH());

			System.out.println("id :" + tk.getIDTAIKHOAN());
		}

		return "/user/ThongTinDatVe";
	}

	@RequestMapping("datve/datghechinh")
	public String ghengoitest(Model model) {

		String songuoistring = (String) session.getAttribute("songuoi");
		int songuoi = Integer.parseInt(songuoistring);
		model.addAttribute("songuoi", songuoi);

		int idtau = (int) session.getAttribute("idtau");

		List<GheNgoi> listduoi = ghndao.findByghekhoangduoi(idtau);
		model.addAttribute("items1", listduoi);
		List<GheNgoi> listtren = ghndao.findByghekhoangtren(idtau);
		model.addAttribute("items2", listtren);

		// Truy vấn danh sách IDGHE đã được đặt từ bảng DATGHE
		List<Integer> bookedSeats = dgdao.findBookedSeats(); // Đổi tên phương thức và lớp DAO của bạn

		// Gửi danh sách IDGHE đã được đặt đến view
		model.addAttribute("bookedSeats", bookedSeats);

		return "/user/Teste";
	}

	@PostMapping("user/dat")
	public String dat(Model model, @RequestParam(name = "selectedSeats") String selectedSeats, DatVe datve)
			throws ParseException {
		String songuoistring = (String) session.getAttribute("songuoi");
		int songuoi = Integer.parseInt(songuoistring);
		DatVe MADATVEMAX = dvservice.FINDIDMAX();

		String[] selectedSeatsArray = selectedSeats.split(", ");

		// Duyệt qua mỗi ghế và in ra thông tin
		for (int i = 0; i < selectedSeatsArray.length; i++) {
			DatVe dv = dvservice.findById(MADATVEMAX.getMADATVE());
			String GHENGOI = selectedSeatsArray[i].trim();
			DatGhe dg = new DatGhe();
			// Chuyển đổi chuỗi thành int
			int intValue = Integer.parseInt(GHENGOI);

			GheNgoi ghe = ghservice.findByid(intValue);
			session.setAttribute("ghengoi" + (i + 1), ghe.getIDGHE());

			Integer ghengoi = Integer.parseInt(session.getAttribute("ghengoi" + (i + 1)).toString());
			System.out.println("Ghe: " + ghengoi);

		}

		// Duyệt qua mỗi ghế và in ra thông tin
		for (int i = 0; i < selectedSeatsArray.length; i++) {
			DatVe dv = dvservice.findById(MADATVEMAX.getMADATVE());
			String GHENGOI = selectedSeatsArray[i].trim();
			DatGhe dg = new DatGhe();
			// Chuyển đổi chuỗi thành int
			int intValue = Integer.parseInt(GHENGOI);

			GheNgoi ghe = ghservice.findByid(intValue);

			dg.setDATVE(dv);
			dg.setGHENGOI(ghe);

			// dgdao.save(dg);

			System.out.println(dv.getMADATVE());
			System.out.println(ghe.getIDGHE());

		}

		return "/user/ThongTinDatVe";
	}

	@RequestMapping("/thanhtoan")
	public String thanhtoan(Model model) {
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
		System.out.println(session.getAttribute("loaive").toString());
		model.addAttribute("giodennoi", session.getAttribute("giodennoi"));
		model.addAttribute("gioxuatphat", session.getAttribute("gioxuatphat"));
		model.addAttribute("loaive", loaive.getLOAIVE());
		model.addAttribute("soghe", listtenghe);
		LocalDateTime now = LocalDateTime.now(); // Lấy thời gian hiện tại
		model.addAttribute("temporal", now);
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
	public String thanhcong(Model model, DatVe dv) throws ParseException {

		String soghe = session.getAttribute("ghengoi1").toString();
		GheNgoi ghe = gheService.findByid(Integer.parseInt(soghe));
		String listtenghe = ghe.getTENGHE();
		if (Integer.parseInt(session.getAttribute("songuoi").toString()) > 1) {
			for (int i = 2; i <= Integer.parseInt(session.getAttribute("songuoi").toString()); i++) {
				ghe = gheService.findByid(Integer.parseInt(session.getAttribute("ghengoi" + i).toString()));
				listtenghe = listtenghe + "," + ghe.getTENGHE();
			}
		}
		System.out.println(listtenghe);
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
			System.out.println("Ngay ve: " + ngayve);
			if (ngayve.equals("ko")) {
				List<HanhKhachTam> hktam = hktservice.findAll();

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

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				int idlichtau = Integer.parseInt(session.getAttribute("idlichtau").toString());

				String ngaydi = session.getAttribute("NgayDi").toString();
				Date datedi = formatter.parse(ngaydi);

				LichTauChay lt = ltservice.findByid(idlichtau);

				Integer loaiv = (Integer) session.getAttribute("loaive");

				LoaiVe idlv = loaiveService.findById(loaiv);

				Integer songuoi = Integer.parseInt(session.getAttribute("songuoi").toString());

				Date ngaydat = new Date();

				dv.setHANHKHACH(idkh);
				dv.setLICHTAUCHAY(lt);
				dv.setNGAYDI(datedi);
				dv.setNGAYVE(null);
				dv.setNGAYDAT(ngaydat);
				dv.setLOAIVE(idlv);
				dv.setSOGHE(songuoi);

				dvdao.save(dv);
				LocalDateTime now = LocalDateTime.now();

				for (int i = 1; i <= songuoi; i++) {
					DatGhe dg = new DatGhe();

					int intValue = Integer.parseInt(session.getAttribute("ghengoi" + i).toString());
					GheNgoi gh = gheService.findByid(intValue);

					DatVe datve = dvservice.FINDIDMAX();

					dg.setIDTUYEN(session.getAttribute("idtuyen").toString());
					dg.setDATVE(datve);
					dg.setGHENGOI(gh);
					dg.setTHOIGIAN(now.toString());
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
				String tongTienStr = session.getAttribute("tongtien").toString();
				BigDecimal tongTien = new BigDecimal(tongTienStr);
				HoaDon hd = new HoaDon();
				hd.setDATVE(datve);
				hd.setTONGTIEN(tongTien);
				hd.setNGAYLAP(ngaydat);
				hd.setTRANGTHAI("Đã thanh toán");
				if(session.getAttribute("vnpay").toString()==null) {
					hd.setLOAITHANHTOAN("Xác thực");
				}else {
					hd.setLOAITHANHTOAN("VN Pay");
				}	
				hddao.save(hd);
				HoaDon hdmax = hoadonService.findMaxDatVe();
				session.setAttribute("mahoadon", hdmax.getMAHD());
			} else {
				List<HanhKhachTam> hktam = hktservice.findAll();

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

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				int idlichtau = Integer.parseInt(session.getAttribute("idlichtau").toString());

				String ngaydi = session.getAttribute("NgayDi").toString();
				Date datedi = formatter.parse(ngaydi);
				Date dateve = formatter.parse(ngayve);

				LichTauChay lt = ltservice.findByid(idlichtau);

				Integer loaiv = (Integer) session.getAttribute("loaive");

				LoaiVe idlv = loaiveService.findById(loaiv);

				Integer songuoi = Integer.parseInt(session.getAttribute("songuoi").toString());

				Date ngaydat = new Date();

				dv.setHANHKHACH(idkh);
				dv.setLICHTAUCHAY(lt);
				dv.setNGAYDI(datedi);
				dv.setNGAYVE(dateve);
				dv.setNGAYDAT(ngaydat);
				dv.setLOAIVE(idlv);
				dv.setSOGHE(songuoi);

				dvdao.save(dv);
				LocalDateTime now = LocalDateTime.now();

				for (int i = 1; i <= songuoi; i++) {
					DatGhe dg = new DatGhe();

					int intValue = Integer.parseInt(session.getAttribute("ghengoi" + i).toString());
					GheNgoi gh = gheService.findByid(intValue);

					DatVe datve = dvservice.FINDIDMAX();

					dg.setIDTUYEN(session.getAttribute("idtuyen").toString());
					dg.setDATVE(datve);
					dg.setGHENGOI(gh);
					dg.setTHOIGIAN(now.toString());
					// dg.setTHOIGIAN(now.toString());)
					dgdao.save(dg);

					System.out.println("ghe thanh cong:");

				}
				DatVe datve = dvservice.FINDIDMAX();
				for (int i = 1; i <= songuoi; i++) {
					DatGhe dg = new DatGhe();

					int intValue = Integer.parseInt(session.getAttribute("ghengoi" + i).toString());
					GheNgoi gh = gheService.findByid(intValue);

					dg.setIDTUYEN(session.getAttribute("idtuyen").toString());
					dg.setDATVE(datve);
					dg.setGHENGOI(gh);
					dg.setTHOIGIAN(now.toString());
					// dg.setTHOIGIAN(now.toString());
					dgdao.save(dg);

					System.out.println("ghe thanh cong:");
				}
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
					hktdao.deleteById(ndtam.get(i).getIDTAM());
					System.out.println("Thanh Cong");

				}
				String tongTienStr = session.getAttribute("tongtien").toString();
				BigDecimal tongTien = new BigDecimal(tongTienStr);
				HoaDon hd = new HoaDon();
				hd.setDATVE(datve);
				hd.setTONGTIEN(tongTien);
				hd.setNGAYLAP(ngaydat);
				hd.setTRANGTHAI("Đã thanh toán");
				if(session.getAttribute("vnpay").toString()==null) {
					hd.setLOAITHANHTOAN("Xác thực");
				}else {
					hd.setLOAITHANHTOAN("VN Pay");
				}		
				hddao.save(hd);
				HoaDon hdmax = hoadonService.findMaxDatVe();
				session.setAttribute("mahoadon", hdmax.getMAHD());
			}
		} else {

			// Lưu tài khoản
			TaiKhoan tk = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));
			model.addAttribute("hoten", "Xin chào " + tk.getHOVATEN() + ",");
			String ngayve = session.getAttribute("NgayVe").toString();
			System.out.println("Ngay ve : " + ngayve);
			if (ngayve.equals("ko")) {

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				int idlichtau = Integer.parseInt(session.getAttribute("idlichtau").toString());

				String ngaydi = session.getAttribute("NgayDi").toString();
				Date datedi = formatter.parse(ngaydi);

				LichTauChay lt = ltservice.findByid(idlichtau);

				Integer loaiv = (Integer) session.getAttribute("loaive");

				LoaiVe idlv = loaiveService.findById(loaiv);

				Integer songuoi = Integer.parseInt(session.getAttribute("songuoi").toString());

				Date ngaydat = new Date();

				dv.setTAIKHOAN(tk);
				dv.setLICHTAUCHAY(lt);
				dv.setNGAYDI(datedi);
				dv.setNGAYVE(null);
				dv.setNGAYDAT(ngaydat);
				dv.setLOAIVE(idlv);
				dv.setSOGHE(songuoi);

				dvdao.save(dv);
				LocalDateTime now = LocalDateTime.now();

				for (int i = 1; i <= songuoi; i++) {
					DatGhe dg = new DatGhe();

					int intValue = Integer.parseInt(session.getAttribute("ghengoi" + i).toString());
					GheNgoi gh = gheService.findByid(intValue);

					DatVe datve = dvservice.FINDIDMAX();

					dg.setIDTUYEN(session.getAttribute("idtuyen").toString());
					dg.setDATVE(datve);
					dg.setGHENGOI(gh);
					dg.setTHOIGIAN(now.toString());
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
				String tongTienStr = session.getAttribute("tongtien").toString();
				BigDecimal tongTien = new BigDecimal(tongTienStr);
				HoaDon hd = new HoaDon();
				hd.setDATVE(datve);
				hd.setTONGTIEN(tongTien);
				hd.setNGAYLAP(ngaydat);
				hd.setTRANGTHAI("Đã thanh toán");
				if(session.getAttribute("vnpay").toString()==null) {
					hd.setLOAITHANHTOAN("Xác thực");
				}else {
					hd.setLOAITHANHTOAN("VN Pay");
				}	
				hddao.save(hd);
				HoaDon hdmax = hoadonService.findMaxDatVe();
				session.setAttribute("mahoadon", hdmax.getMAHD());
			} else {

				HanhKhach idkh = hanhkdao.FINDIDHKMAX();

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				int idlichtau = Integer.parseInt(session.getAttribute("idlichtau").toString());

				String ngaydi = session.getAttribute("NgayDi").toString();
				Date datedi = formatter.parse(ngaydi);
				Date dateve = formatter.parse(ngayve);

				LichTauChay lt = ltservice.findByid(idlichtau);

				Integer loaiv = (Integer) session.getAttribute("loaive");

				LoaiVe idlv = loaiveService.findById(loaiv);

				Integer songuoi = Integer.parseInt(session.getAttribute("songuoi").toString());

				Date ngaydat = new Date();

				dv.setTAIKHOAN(tk);
				dv.setLICHTAUCHAY(lt);
				dv.setNGAYDI(datedi);
				dv.setNGAYVE(dateve);
				dv.setNGAYDAT(ngaydat);
				dv.setLOAIVE(idlv);
				dv.setSOGHE(songuoi);

				dvdao.save(dv);
				LocalDateTime now = LocalDateTime.now();

				for (int i = 1; i <= songuoi; i++) {
					DatGhe dg = new DatGhe();

					int intValue = Integer.parseInt(session.getAttribute("ghengoi" + i).toString());
					GheNgoi gh = gheService.findByid(intValue);

					DatVe datve = dvservice.FINDIDMAX();

					dg.setIDTUYEN(session.getAttribute("idtuyen").toString());
					dg.setDATVE(datve);
					dg.setGHENGOI(gh);
					dg.setTHOIGIAN(now.toString());
					// dg.setTHOIGIAN(now.toString());)
					dgdao.save(dg);

					System.out.println("ghe thanh cong:");
				}
				DatVe datve = dvservice.FINDIDMAX();
				for (int i = 1; i <= songuoi; i++) {
					DatGhe dg = new DatGhe();

					int intValue = Integer.parseInt(session.getAttribute("ghengoi" + i).toString());
					GheNgoi gh = gheService.findByid(intValue);

					dg.setIDTUYEN(session.getAttribute("idtuyen").toString());
					dg.setDATVE(datve);
					dg.setGHENGOI(gh);
					dg.setTHOIGIAN(now.toString());
					// dg.setTHOIGIAN(now.toString());
					dgdao.save(dg);

					System.out.println("ghe thanh cong:");
				}
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
				String tongTienStr = session.getAttribute("tongtien").toString();
				BigDecimal tongTien = new BigDecimal(tongTienStr);
				HoaDon hd = new HoaDon();
				hd.setDATVE(datve);
				hd.setTONGTIEN(tongTien);
				hd.setNGAYLAP(ngaydat);
				hd.setTRANGTHAI("Đã thanh toán");
				System.out.println(session.getAttribute("vnpay").toString());
				if(session.getAttribute("vnpay").toString()==null) {
					hd.setLOAITHANHTOAN("Xác thực");
				}else {
					hd.setLOAITHANHTOAN("VN Pay");
				}				
				hddao.save(hd);
				HoaDon hdmax = hoadonService.findMaxDatVe();
				session.setAttribute("mahoadon", hdmax.getMAHD());
				session.setAttribute("tongtien", "0");
			}
		}

		return "/user/datve/chuyenvetrangchu";
	}
}
