package com.tigon.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.TaiKhoanDAO;
import com.tigon.dao.LoaiHanhKhachDAO;
import com.tigon.model.DatVe;
import com.tigon.model.TaiKhoan;
import com.tigon.model.LoaiHanhKhach;
import com.tigon.service.DatVeService;
import com.tigon.service.TaiKhoanService;

@Controller
public class TaiKhoanController {

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

	@RequestMapping("/thongtintaikhoan")
	public String thongtintaikhoan(Model model) {
		Integer user = Integer.parseInt(session.getAttribute("user").toString());
		TaiKhoan taikhoan = taiKhoanService.findById(user);
		List<DatVe> lichsuve = datveService.ListDatVeByIdTaiKhoan(user);
		DatVe datve = datveService.getNgayDatMoiNhat(user);

		if (datve != null) {
			model.addAttribute("lichsu", lichsuve);
			model.addAttribute("user", taikhoan);
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
		String phuong = "Phường";
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

		if (!diachi.isEmpty()) {
			if (!thanhPho.isEmpty() || !quanHuyen.isEmpty() || !phuongXa.isEmpty()) {
				diaChi = diachi + ", " + phuongXa + ", " + quanHuyen + ", " + thanhPho;
			} else {
				diaChi = diachi;
			}
		} else if (diachi.isEmpty() && thanhPho.isEmpty()) {
			diaChi = diachi_old;
		} else {
			diaChi = diachi_old;
		}

		if (hovaten.isEmpty() || sdt.isEmpty() || cccd.isEmpty() || diachi.isEmpty()) {

			model.addAttribute("thongbaoerror", "...");
			model.addAttribute("ndungtbao", "Thông báo: Vui lòng điền đầy đủ thông tin!");
			model.addAttribute("user", taikhoan);
			return "/user/chinhsuataikhoan";
		} else {
			taikhoan.setHOVATEN(hovaten);
			taikhoan.setDIACHI(diaChi);
			taikhoan.setSDT(sdt);
			taikhoan.setCCCD(cccd);
			dao.save(taikhoan);

			TaiKhoan taikhoan_updated = taiKhoanService.findById(user);
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

	public void getData(Model model, @RequestParam String hovaten, @RequestParam String sdt, @RequestParam String cccd,
			@RequestParam String diachi) {
		// Lấy idhanhkhach bằng session
		Integer user = Integer.parseInt(session.getAttribute("user").toString());

		TaiKhoan taikhoan = taiKhoanService.findById(user);

		String thanhPho = request.getParameter("city");
		String quanHuyen = request.getParameter("district");
		String phuongXa = request.getParameter("ward");
		String diaChi = null;
		String diachi_old = taikhoan.getDIACHI();

		List<DatVe> lichsuve = datveService.ListDatVeByIdTaiKhoan(user);
		DatVe datve = datveService.getNgayDatMoiNhat(user);
		TaiKhoan taikhoan_updated = taiKhoanService.findById(user);

		model.addAttribute("lichsu", lichsuve);
		model.addAttribute("user", taikhoan_updated);
		model.addAttribute("ngaydat", datve.getNGAYDAT());
		model.addAttribute("chuyengannhat", datve.getLICHTAUCHAY().getTUYEN().getTENTUYEN());
		model.addAttribute("datve", datve);
		model.addAttribute("user", taikhoan);
	}

}
