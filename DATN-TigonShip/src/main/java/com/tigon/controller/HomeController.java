package com.tigon.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tigon.dao.DatVeDAO;
import com.tigon.dao.GheNgoiDAO;
import com.tigon.dao.LichTauChayDAO;
import com.tigon.dao.LoaiVeDAO;
import com.tigon.dao.TuyenDAO;

import com.tigon.model.DatVe;
import com.tigon.model.GheNgoi;
import com.tigon.model.HanhKhach;
import com.tigon.model.LichTauChay;
import com.tigon.model.LoaiVe;
import com.tigon.model.Tuyen;
import com.tigon.service.LichTauService;
import com.tigon.service.TuyenTauService;

@Controller
public class HomeController {
	@Autowired
	TuyenDAO dao;
	
	@Autowired
	TuyenTauService ttservice;
	
	@Autowired
	LichTauChayDAO daoLT;
	
	@Autowired
	DatVeDAO dvdao;
	
	@Autowired
	LoaiVeDAO lvdao;
	
	@Autowired
	GheNgoiDAO ghndao;
	
	@Autowired
	LichTauService ltservice;
	
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
	@RequestMapping("/user/luc")
	public String tuyentau(Model model) {
		List<Tuyen> list = ttservice.findAll();
		model.addAttribute("items", list);
		return "/user/TuyenTau";
	}
	
	@RequestMapping("/user/tuyentau")
	public String table(Model model) {
		
		String TenTuyen = servletRequest.getParameter("TENTUYEN");
		
		Tuyen searchResults = ttservice.findByTuyen(TenTuyen);
		int idtuyen = searchResults.getIDTUYEN();
		
		String NgayDi = servletRequest.getParameter("NgayDi");
		session.setAttribute("NgayDi", NgayDi);
		
		String NgayVe = servletRequest.getParameter("NgayVe");
		if(NgayVe==null) {
			session.setAttribute("NgayVe", "ko");
		}else {
			session.setAttribute("NgayVe", NgayVe);
		}
		
		
		String TENTUYEN = servletRequest.getParameter("TENTUYEN");
		session.setAttribute("TENTUYEN", TENTUYEN);
		
		LichTauChay search = ltservice.findByLichTau(idtuyen);
		int idlichtau = search.getIDLICHTAU();
		
		session.setAttribute("idlichtau", search.getIDLICHTAU());
		
		String searchResults2 = session.getAttribute("TENTUYEN").toString();
		model.addAttribute("Ten",searchResults2);
		
		//session.setAttribute("items", searchResults.getIDTUYEN());
		//session.setAttribute("fullname", searchResults.getTENTUYEN());
		System.out.println("Id Tuyến: " +idtuyen);
		System.out.println("Tên Tuyến: " +TenTuyen);
		System.out.println("Id lịch tàu: " +idlichtau);
		System.out.println("Ngày Đi: " +NgayDi);
		System.out.println("Ngày Về: " +NgayVe);
		
		model.addAttribute("items", search);

		return "/user/VeTau";
	}
	
	@RequestMapping("user/datghe")
	public String ghengoi(Model model) {
		List<GheNgoi> listduoi = ghndao.findByghekhoangduoi();
		model.addAttribute("items1", listduoi);
		List<GheNgoi> listtren = ghndao.findByghekhoangtren();
		model.addAttribute("items2", listtren);
		return "/user/ghengoi";
	}
	
	@RequestMapping("/user/xem")
	public String xem(@RequestBody Map<String, String> requestBody, Model model, DatVe datve) throws ParseException{
		String selectedSeatNumber = requestBody.get("selectedSeatNumber");
	
		String ngaydatString = requestBody.get("ngaydat");

	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		int idlichtau = Integer.parseInt(session.getAttribute("idlichtau").toString());
		
		String ngaydi = session.getAttribute("NgayDi").toString();
		Date datedi = formatter.parse(ngaydi);
		
		String ngayve = session.getAttribute("NgayVe").toString();
		boolean flag = true;
		if(ngayve.equals("ko")) {
			flag = false;
			Date ngaydat = formatter.parse(ngaydatString);
			
			LichTauChay licht = ltservice.findByid(idlichtau);
			
			int loaive;
			
			if(flag==false) {
				loaive = 1;
			}else {
				loaive = 2;
			}
			
			LoaiVe lv = lvdao.findById(loaive).get();
			
			GheNgoi gn = ghndao.findByghe(selectedSeatNumber);
			
			int idghe = gn.getIDGHE();
			
			datve.setHANHKHACH(null);
			datve.setSOGHE(idghe);
			datve.setLICHTAUCHAY(licht);
			datve.setNGAYDI(datedi);
			datve.setNGAYVE(null);
			datve.setNGAYDAT(ngaydat);
			datve.setLOAIVE(lv); 
			
			dvdao.save(datve);
		}else {
			Date dateve = formatter.parse(ngayve);
			Date ngaydat = formatter.parse(ngaydatString);
			
			LichTauChay licht = ltservice.findByid(idlichtau);
			
			int loaive;
			
			if(flag==false) {
				loaive = 1;
			}else {
				loaive = 2;
			}
			
			LoaiVe lv = lvdao.findById(loaive).get();
			
			GheNgoi gn = ghndao.findByghe(selectedSeatNumber);
			
			int idghe = gn.getIDGHE();
			
			datve.setHANHKHACH(null);
			datve.setSOGHE(idghe);
			datve.setLICHTAUCHAY(licht);
			datve.setNGAYDI(datedi);
			datve.setNGAYVE(dateve);
			datve.setNGAYDAT(ngaydat);
			datve.setLOAIVE(lv); 
			
			dvdao.save(datve);
		}

	    return "/user/ThongTinDatVe";
		
	}
	
	@RequestMapping("/xacminh")
	public String xacminh(Model model, String sdt) {
		model.addAttribute("message",sdt);
		
		return "/user/login/verifyPage";
	}	
	
	@RequestMapping("user/hanhkhach")
	public String hanhkhach() {
		
		return "/user/ThongTinDatVe";
	}
	
	
	
	
}
