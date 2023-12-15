package com.tigon.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.DatGheDAO;
import com.tigon.dao.DatVeDAO;
import com.tigon.dao.GheNgoiDAO;
import com.tigon.dao.NguoiDiCungTamDAO;
import com.tigon.dao.LichTauChayDAO;
import com.tigon.dao.LoaiVeDAO;
import com.tigon.dao.NguoiDiCungDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.DatGhe;
import com.tigon.model.DatVe;
import com.tigon.model.GheNgoi;
import com.tigon.model.NguoiDiCungTam;
import com.tigon.model.LichTauChay;
import com.tigon.model.LoaiHanhKhach;
import com.tigon.model.LoaiVe;
import com.tigon.model.NguoiDiCung;
import com.tigon.model.Tuyen;
import com.tigon.service.DatVeService;
import com.tigon.service.GheNgoiService;
import com.tigon.service.NguoiDiCungTamService;
import com.tigon.service.LichTauService;
import com.tigon.service.LoaiHanhKhachService;
import com.tigon.service.TuyenTauService;

@Controller
public class HomeController {
	@Autowired
	TuyenDAO dao;

	@Autowired
	TuyenTauService ttservice;
	
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
	NguoiDiCungTamService hktsevice;

	@Autowired
	NguoiDiCungTamDAO hktdao;

	@Autowired
	LoaiVeDAO lvdao;

	@Autowired
	GheNgoiDAO ghndao;
	
	@Autowired
	NguoiDiCungDAO gdcdao;

	@Autowired
	LichTauService ltservice;
	
	@Autowired
	NguoiDiCungTamService hktservice;

	@Autowired
	HttpServletRequest servletRequest;

	@Autowired
	HttpSession session;

	@RequestMapping("/")
	public String index() {

		return "user/index";
	}

	@RequestMapping("/lienhe")
	public String lienhe() {
		return "user/Lienhe";
	}

	@RequestMapping("/gioithieu")
	public String gioithieu() {
		return "user/GioiThieu";
	}

	@RequestMapping("/gopy")
	public String gopy() {
		return "user/GopY";
	}

	@RequestMapping("/dangnhap")
	public String dangnhap() {
		return "user/login/index";
	}

	@GetMapping("/dangki")
	public String dangki(Model model) {
		return "user/login/register";
	}

	@RequestMapping("/datve/timtuyen")
	public String tuyentau(Model model) {
		List<Tuyen> list = ttservice.findAll();
		model.addAttribute("items", list);
		return "/user/TuyenTau";
	}

	@RequestMapping("/datve/tuyentau")
	public String table(Model model, @RequestParam("NoOfPassenger") String songuoi) {
		session.setAttribute("songuoi", songuoi);
		String TenTuyen = servletRequest.getParameter("TENTUYEN");

		Tuyen searchResults = ttservice.findByTuyen(TenTuyen);
		int idtuyen = searchResults.getIDTUYEN();

		String NgayDi = servletRequest.getParameter("NgayDi");
		session.setAttribute("NgayDi", NgayDi);

		String NgayVe = servletRequest.getParameter("NgayVe");
		if (NgayVe == null) {
			session.setAttribute("NgayVe", "ko");
		} else {
			session.setAttribute("NgayVe", NgayVe);
		}

		String TENTUYEN = servletRequest.getParameter("TENTUYEN");
		session.setAttribute("TENTUYEN", TENTUYEN);

		LichTauChay search = ltservice.findByLichTau(idtuyen);
		int idlichtau = search.getIDLICHTAU();
		int idtau = search.getTAU().getIDTAU();

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
			@RequestParam("nationality") List<String> nationality, @RequestParam("tickit") List<String> tickit) {

		String songuoistring = (String) session.getAttribute("songuoi");
		int songuoi = Integer.parseInt(songuoistring);
		int mahk;
		if (songuoistring != null) {
			try {

				System.out.println("Số người hành khách: " + songuoi);

				for (int i = 2; i <= songuoi; i++) {
					NguoiDiCungTam hkt = new NguoiDiCungTam();
					System.out.println("Hanh khach " + i);
					System.out.println(username.get(i - 2));
					System.out.println(cccd.get(i - 2));
					System.out.println(date.get(i - 2));
					System.out.println(email.get(i - 2));
					System.out.println(SDT.get(i - 2));
					System.out.println(nationality.get(i - 2));
					if (tickit.get(i - 2).equals("Trẻ Em")) {
						mahk = 2;
						System.out.println(mahk);
					} else {
						mahk = 1;
						System.out.println(mahk);
					}
					hkt.setHOVATEN(username.get(i - 2));
					hkt.setCCCD(cccd.get(i - 2));
					hkt.setSDT(SDT.get(i - 2));
					hkt.setIDLOAIKH(mahk);
					
					hktdao.save(hkt);

				}
			} catch (NumberFormatException e) {
				System.err.println("Lỗi chuyển đổi kiểu int: " + e.getMessage());
			}
		} else {
			System.err.println("Giá trị 'songuoi' từ session là null");
		}
		model.addAttribute("songuoi", songuoi);
		List<GheNgoi> listduoi = ghndao.findByghekhoangduoi();
		model.addAttribute("items1", listduoi);
		List<GheNgoi> listtren = ghndao.findByghekhoangtren();
		model.addAttribute("items2", listtren);
		return "/user/Teste";
	}

	@RequestMapping("datve/datgheTest")
	public String ghengoichinh(Model model) {

		List<GheNgoi> listduoi = ghndao.findByghekhoangduoi();
		model.addAttribute("items1", listduoi);
		List<GheNgoi> listtren = ghndao.findByghekhoangtren();
		model.addAttribute("items2", listtren);
		return "/user/ghengoi";
	}

	@RequestMapping("/datve/HanhKhachDiCung")
	public String Hanhkhach() {
		String songuoi = (String) session.getAttribute("songuoi");
		System.out.println("So nguoi " + songuoi);
		if (songuoi.equals("1")) {
			return "redirect:/datve/datghechinh";
		} else {
			return "/user/HanhkhachDiCung";
		}
	}

	@RequestMapping("/xacminh")
	public String xacminh(Model model, String sdt) {
		model.addAttribute("message", sdt);

		return "/user/login/verifyPage";
	}

	@RequestMapping("datve/hanhkhach")
	public String hanhkhach() {
		String songuoi = (String) session.getAttribute("songuoi");
		System.out.println(songuoi);

		return "/user/ThongTinDatVe";
	}

	@RequestMapping("datve/datghechinh")
	public String ghengoitest(Model model) {

		String songuoistring = (String) session.getAttribute("songuoi");
		int songuoi = Integer.parseInt(songuoistring);
		model.addAttribute("songuoi", songuoi);

		List<GheNgoi> listduoi = ghndao.findByghekhoangduoi();
		model.addAttribute("items1", listduoi);
		List<GheNgoi> listtren = ghndao.findByghekhoangtren();
		model.addAttribute("items2", listtren);
		return "/user/Teste";
	}

	@PostMapping("user/dat")
	public String dat(Model model, @RequestParam(name = "selectedSeats") String selectedSeats, DatVe datve) throws ParseException {
		String songuoistring = (String) session.getAttribute("songuoi");
		int songuoi = Integer.parseInt(songuoistring);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		int idlichtau = Integer.parseInt(session.getAttribute("idlichtau").toString());

		String ngaydi = session.getAttribute("NgayDi").toString();
		Date datedi = formatter.parse(ngaydi);

		String ngayve = session.getAttribute("NgayVe").toString();
		boolean flag = true;
		if (ngayve.equals("ko")) {
			flag = false;

			LichTauChay licht = ltservice.findByid(idlichtau);

			int loaive;

			if (flag == false) {
				loaive = 1;
			} else {
				loaive = 2;
			}

			LoaiVe lv = lvdao.findById(loaive).get();

			datve.setHANHKHACH(null);
			datve.setSOGHE(songuoi);
			datve.setLICHTAUCHAY(licht);
			datve.setNGAYDI(datedi);
			datve.setNGAYVE(null);
			// datve.setNGAYDAT(ngaydat);
			datve.setLOAIVE(lv);

			dvdao.save(datve);
			System.out.println("succes");
			
			DatVe MADATVEMAX = dvservice.FINDIDMAX();
			
			//System.out.println(MADATVEMAX.getMADATVE());
			
			String[] selectedSeatsArray = selectedSeats.split(", ");
			
			
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
				
				dgdao.save(dg);
				
				
			}
			
			List<NguoiDiCungTam> hktam = hktservice.findAll();
			
			for(int i=0;i<songuoi && i < hktam.size();i++) {
				
				DatVe dvtam = dvservice.findById(MADATVEMAX.getMADATVE());
				LoaiHanhKhach lhk = lhksertvice.findByid(hktam.get(i).getIDLOAIKH());
				
				NguoiDiCung ngdicung = new NguoiDiCung();
				ngdicung.setHOVATEN(hktam.get(i).getHOVATEN());
				ngdicung.setCCCD(hktam.get(i).getCCCD());
				ngdicung.setSDT(hktam.get(i).getSDT());
				ngdicung.setDATVE(dvtam);
				ngdicung.setLOAIHK(lhk);
				System.out.println("----------------------");
				System.out.println(hktam.get(i).getIDLOAIKH());
				System.out.println(hktam.get(i).getHOVATEN());
				System.out.println(hktam.get(i).getCCCD());
				System.out.println(hktam.get(i).getSDT());
				System.out.println(dvtam.getMADATVE());
				System.out.println(lhk.getLOAIHK());
	
				gdcdao.save(ngdicung);
				hktdao.deleteById(hktam.get(i).getIDTAM());
				System.out.println("Thanh Cong");
			}	

		} else {
			Date dateve = formatter.parse(ngayve);
		
			LichTauChay licht = ltservice.findByid(idlichtau);

			int loaive;

			if (flag == false) {
				loaive = 1;
			} else {
				loaive = 2;
			}

			LoaiVe lv = lvdao.findById(loaive).get();

			datve.setHANHKHACH(null);
			datve.setSOGHE(songuoi);
			datve.setLICHTAUCHAY(licht);
			datve.setNGAYDI(datedi);
			datve.setNGAYVE(dateve);
			// datve.setNGAYDAT(ngaydat);
			datve.setLOAIVE(lv);

			dvdao.save(datve);
			System.out.println("succes");
			
			DatVe MADATVEMAX = dvservice.FINDIDMAX();
			
			//System.out.println(MADATVEMAX.getMADATVE());
			
			String[] selectedSeatsArray = selectedSeats.split(", ");
			
			
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
				
				dgdao.save(dg);
				
				
				
				System.out.println(dv.getMADATVE());
				System.out.println(ghe.getIDGHE());
				
			}
			
			List<NguoiDiCungTam> hktam = hktservice.findAll();
			
			for(int i=0;i<songuoi && i < hktam.size();i++) {
				
				DatVe dvtam = dvservice.findById(MADATVEMAX.getMADATVE());
				LoaiHanhKhach lhk = lhksertvice.findByid(hktam.get(i).getIDLOAIKH());
				
				NguoiDiCung ngdicung = new NguoiDiCung();
				ngdicung.setHOVATEN(hktam.get(i).getHOVATEN());
				ngdicung.setCCCD(hktam.get(i).getCCCD());
				ngdicung.setSDT(hktam.get(i).getSDT());
				ngdicung.setDATVE(dvtam);
				ngdicung.setLOAIHK(lhk);
				System.out.println("----------------------");
				System.out.println(hktam.get(i).getIDLOAIKH());
				System.out.println(hktam.get(i).getHOVATEN());
				System.out.println(hktam.get(i).getCCCD());
				System.out.println(hktam.get(i).getSDT());
				System.out.println(dvtam.getMADATVE());
				System.out.println(lhk.getLOAIHK());
	
				gdcdao.save(ngdicung);
				hktdao.deleteById(hktam.get(i).getIDTAM());
				System.out.println("Thanh Cong");
			}
		}
		
		

		return "/user/ThongTinDatVe";
	}

//	hktam = HanhKhachTamservice.findall;
//	while(hktam.id.get(0)!=null) {
//		hanhkhach hk = new hanhkhach;
//		hk.getid(hktam.id.get(0));
//		...
//		hk.save;
//		hktamdao.delete(hktam.getid.(0));
//	}
	
	
}
