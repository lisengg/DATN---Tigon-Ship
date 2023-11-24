package com.tigon.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.HanhKhachDAO;
import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.model.DatVe;
import com.tigon.model.HanhKhach;
import com.tigon.model.LoaiHanhKhach;
import com.tigon.service.DatVeService;
import com.tigon.service.HanhKhachService;

@Controller
public class HanhKhachController {

	@Autowired
	HttpSession session;

	@Autowired
	HanhKhachService hanhKhachService;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HanhKhachDAO dao;

	@Autowired
	DatVeService datveService;

	@Autowired
	LoaiHanhKhachDAO loaiHKDao;

	@RequestMapping("/thongtintaikhoan")
	public String thongtintaikhoan(Model model) {
		Integer user = Integer.parseInt(session.getAttribute("user").toString());
		HanhKhach hanhkhach = hanhKhachService.findById(user);
		List<DatVe> lichsuve = datveService.ListDatVeByidKhach(user);
		DatVe datve = datveService.getNgayDatMoiNhat(user);

		if (datve != null) {
			model.addAttribute("lichsu", lichsuve);
			model.addAttribute("user", hanhkhach);
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
		HanhKhach hanhkhach = hanhKhachService.findById(user);
		model.addAttribute("user", hanhkhach);

		return "/user/chinhsuataikhoan";
	}

	@PostMapping("/user/chinhsuataikhoan")
	public String capnhattaikhoan(Model model, @RequestParam String hovaten, @RequestParam String sdt,
			@RequestParam String cccd, @RequestParam String diachi) {

		// Lấy idhanhkhach bằng session
		Integer user = Integer.parseInt(session.getAttribute("user").toString());

		HanhKhach hanhkhach = hanhKhachService.findById(user);

		String thanhPho = request.getParameter("city");
		String quanHuyen = request.getParameter("district");
		String phuongXa = request.getParameter("ward");
		String diaChi = null;
		String diachi_old = hanhkhach.getDIACHI();

		if (!diachi.isEmpty()) {
			if (!thanhPho.isEmpty() || !quanHuyen.isEmpty() || !phuongXa.isEmpty()) {
				diaChi = diachi + ", " + phuongXa + ", " + quanHuyen + ", " + thanhPho;
			}else {
				diaChi = diachi;
			}
		} else if (diachi.isEmpty() && thanhPho.isEmpty()) {
			diaChi = diachi_old;
		} else {
			diaChi = diachi_old;
		}

		if (hovaten.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || diachi.isEmpty()) {
			List<DatVe> lichsuve = datveService.ListDatVeByidKhach(user);
			DatVe datve = datveService.getNgayDatMoiNhat(user);

			model.addAttribute("user", hanhkhach);
			model.addAttribute("lichsu", lichsuve);
			model.addAttribute("ngaydat", datve.getNGAYDAT());
			model.addAttribute("chuyengannhat", datve.getLICHTAUCHAY().getTUYEN().getTENTUYEN());
			model.addAttribute("datve", datve);
			model.addAttribute("user", hanhkhach);

			model.addAttribute("thongbao", "...");
			model.addAttribute("ndungtbao", "Thông báo: Vui lòng điền đầy đủ thông tin!");
			model.addAttribute("user", hanhkhach);

			return "/user/chinhsuataikhoan";
		} else {
			hanhkhach.setHOVATEN(hovaten);
			hanhkhach.setDIACHI(diaChi);
			hanhkhach.setSDT(sdt);
			hanhkhach.setCCCD(cccd);
			dao.save(hanhkhach);

			HanhKhach hanhkhach_updated = hanhKhachService.findById(user);
			model.addAttribute("thongbao", "Cập nhật thông tin thành công!");
			model.addAttribute("ndungtbao", "Thông báo: Thông tin của bạn đã được cập nhật!");

			List<DatVe> lichsuve = datveService.ListDatVeByidKhach(user);
			DatVe datve = datveService.getNgayDatMoiNhat(user);

			model.addAttribute("lichsu", lichsuve);
			model.addAttribute("user", hanhkhach_updated);
			model.addAttribute("ngaydat", datve.getNGAYDAT());
			model.addAttribute("chuyengannhat", datve.getLICHTAUCHAY().getTUYEN().getTENTUYEN());
			model.addAttribute("datve", datve);
			model.addAttribute("user", hanhkhach);

			return "/user/thongtintaikhoan";
		}

	}

	public void getData(Model model, @RequestParam String hovaten, @RequestParam String sdt, @RequestParam String cccd,
			@RequestParam String diachi) {
		// Lấy idhanhkhach bằng session
		Integer user = Integer.parseInt(session.getAttribute("user").toString());

		HanhKhach hanhkhach = hanhKhachService.findById(user);

		String thanhPho = request.getParameter("city");
		String quanHuyen = request.getParameter("district");
		String phuongXa = request.getParameter("ward");
		String diaChi = null;
		String diachi_old = hanhkhach.getDIACHI();
		
		List<DatVe> lichsuve = datveService.ListDatVeByidKhach(user);
		DatVe datve = datveService.getNgayDatMoiNhat(user);
		HanhKhach hanhkhach_updated = hanhKhachService.findById(user);
		
		model.addAttribute("lichsu", lichsuve);
		model.addAttribute("user", hanhkhach_updated);
		model.addAttribute("ngaydat", datve.getNGAYDAT());
		model.addAttribute("chuyengannhat", datve.getLICHTAUCHAY().getTUYEN().getTENTUYEN());
		model.addAttribute("datve", datve);
		model.addAttribute("user", hanhkhach);
	}

}
