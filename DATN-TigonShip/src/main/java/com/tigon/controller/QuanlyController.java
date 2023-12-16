package com.tigon.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tigon.dao.BangtinDAO;
import com.tigon.dao.DanhGiaDAO;
import com.tigon.dao.GheNgoiDAO;
import com.tigon.dao.HangTauDAO;
import com.tigon.dao.HoaDonDAO;
import com.tigon.dao.LichTauChayDAO;
import com.tigon.dao.TaiKhoanDAO;
import com.tigon.dao.TauDAO;
import com.tigon.dao.TuyenDAO;

@Controller
public class QuanlyController {

	@Autowired
	HangTauDAO hangTauDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	
	@Autowired
	LichTauChayDAO lichTauChayDAO;
	
	@Autowired
	HoaDonDAO hoaDonDAO;
	
	@Autowired
	TauDAO tauDAO;
	
	@Autowired
	TuyenDAO tuyenDAO;
	
	@Autowired
	DanhGiaDAO danhGiaDAO;
	
	@Autowired
	BangtinDAO bangtinDAO;
	
	@Autowired
	GheNgoiDAO gheNgoiDAO;
	
	@GetMapping("/admin")
	public String quanly(Model model) {
		System.out.println(hangTauDAO.findAll().get(0).getDIACHI());
		 long tongTaiKhoan = taiKhoanDAO.countByVaiTro("KHACHHANG");
	     model.addAttribute("tongTaiKhoan", tongTaiKhoan);
	     
	     long tongTaiKhoannv = taiKhoanDAO.countByVaiTro1("STAFF");
	     model.addAttribute("tongTaiKhoannv", tongTaiKhoannv);
	     
	     long tongLichTau = lichTauChayDAO.countAllLichTau();
	     model.addAttribute("tongLichTau", tongLichTau);
	     
	     long tongTau = tauDAO.countAllTau();
	     model.addAttribute("tongTau", tongTau);
	     
	     long tongHangTau = hangTauDAO.countAllHangTau();
	     model.addAttribute("tongHangTau", tongHangTau);
	     
	     long tongHoaDon = hoaDonDAO.countAllHoaDon();
	     model.addAttribute("tongHoaDon", tongHoaDon);
	     
	     Double tongTienHoaDon = hoaDonDAO.sumTongTienOfAllHoaDon();
	     model.addAttribute("tongTienHoaDon", tongTienHoaDon);
	     
	     long tongTuyen = tuyenDAO.countAllTuyen();
	     model.addAttribute("tongTuyen", tongTuyen);
	     
	     long tongDanhGia = danhGiaDAO.countAllDanhGiaTot();
	     model.addAttribute("tongDanhGia", tongDanhGia);
	     
	     long tongDanhGiaK = danhGiaDAO.countAllDanhGiaKem();
	     model.addAttribute("tongDanhGiaK", tongDanhGiaK);
	     
	     long tongBangTin = bangtinDAO.countAllBangTin();
	     model.addAttribute("tongBangTin", tongBangTin);
	     
	     long tongGheNgoi = gheNgoiDAO.countByTrangThai("Đang hoạt động");
	     model.addAttribute("tongGheNgoi", tongGheNgoi);
		return "admin/index";
	}

	@GetMapping("/admin/authority1")
	public String authority(Model model) {
		return "admin/authority1/index1";
	}

	@GetMapping("/admin/tau")
	public String tau(Model model) {
		return "admin/tau/tau";
	}

	@GetMapping("/admin/ghengoi")
	public String ghengoi(Model model) {
		return "admin/ghengoi/ghe";
	}

	@GetMapping("/admin/tuyentau")
	public String tuyentau(Model model) {
		return "admin/tuyentau/tuyentau";
	}

	@GetMapping("/admin/hangtau")
	public String hangtau(Model model) {
		return "admin/hangtau/hangtau";
	}

	@GetMapping("/admin/taikhoan")
	public String hanhkhach(Model model) {

		return "admin/taikhoan/taikhoan";
	}

	@GetMapping("/admin/giave")
	public String giave(Model model) {

		return "admin/giave/giave";
	}

	@GetMapping("/admin/lichtau")
	public String lichtau(Model model) {

		return "admin/lichtau/lichtau";
	}

	@GetMapping("/admin/danhgia")
	public String danhgia(Model model) {

		return "admin/danhgia/danhgia";
	}

	@GetMapping("/admin/danhgia/chitiet")
	public String danhgiachitiet(Model model) {
	
		return "admin/danhgia/chitiet";
	}
	@GetMapping("/admin/hoadon")
	public String hoadon(Model model) {

		return "admin/hoadon/hoadon";
	}

	@GetMapping("/admin/doanhthu")
	public String doanhthu(Model model) {
		return "admin/doanhthu/doanhthu";
	}
	@GetMapping("/admin/datve")
	public String datve(Model model) {
		return "admin/datve/datve";
	}


	@GetMapping("/admin/doanhthu/theongay")
	public String doanhthuTheoNgay(Model model) {
		return "admin/doanhthu/theongay";
	}
	
	@GetMapping("/admin/dangkynv")
	public String dangkynv(Model model) {
		return "admin/authority1/dangkynv";
	}
	
	@GetMapping("/admin/tintuc")
	public String bangtin(Model model) {
		return "admin/tintuc/dangbangtin";
	}
	// @GetMapping("/admin/profile")
	// public String Profile(Model model) {
	//
	// return "admin/profile/profile";
	// }

}
