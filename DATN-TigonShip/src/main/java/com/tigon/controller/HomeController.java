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

import com.tigon.dao.BangtinDAO;
import com.tigon.dao.DatGheDAO;
import com.tigon.dao.DatVeDAO;
import com.tigon.dao.GheNgoiDAO;
import com.tigon.dao.HanhKhachTamDAO;
import com.tigon.dao.NguoiDiCungTamDAO;
import com.tigon.dao.LichTauChayDAO;
import com.tigon.dao.LoaiVeDAO;
import com.tigon.dao.NguoiDiCungDAO;
import com.tigon.dao.TuyenDAO;
import com.tigon.model.Bangtin;
import com.tigon.model.DatGhe;
import com.tigon.model.DatVe;
import com.tigon.model.GheNgoi;
import com.tigon.model.HanhKhachTam;
import com.tigon.model.NguoiDiCungTam;
import com.tigon.model.LichTauChay;
import com.tigon.model.LoaiHanhKhach;
import com.tigon.model.LoaiVe;
import com.tigon.model.NguoiDiCung;
import com.tigon.model.Tuyen;
import com.tigon.service.BangtinService;
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
	HanhKhachTamDAO hktamdao;

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
	@Autowired
	BangtinService service;
	@Autowired
	BangtinDAO bangtindao;

	@RequestMapping("/")
	public String index(Model model) {
		List<Bangtin> list = bangtindao.findAll();
		model.addAttribute("items", list);
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

}
