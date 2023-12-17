package com.tigon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tigon.dao.DanhGiaDAO;
import com.tigon.model.DanhGia;

import com.tigon.model.TaiKhoan;
import com.tigon.model.Tuyen;
import com.tigon.service.DanhGiaService;
import com.tigon.service.DanhGiaService2;
import com.tigon.service.TaiKhoanService;
import com.tigon.service.TuyenTauService;

@Controller
public class DanhGiaController {

	
	@Autowired
	TaiKhoanService tksv;

	@Autowired
	DanhGiaService2 dgsv;
	@Autowired
	HttpSession session;

	@Autowired
	TuyenTauService ttservice;

	@Autowired
	DanhGiaDAO danhGiaDAO;

	@GetMapping("/danhgia")
	public String getDanhGia(Model model) {
		// Lấy danh sách đánh giá từ cơ sở dữ liệu
		
		List<Tuyen> list = ttservice.findAll();
		model.addAttribute("items", list);
		List<DanhGia> danhGiaList = danhGiaDAO.findAll();

		List<String> saoDanhGia = new ArrayList<>();

		for (int i = 0; i < danhGiaList.size(); i++) {
			Integer sao = danhGiaList.get(i).getDANHGIA();
			String chuoiSao = "&#9733;";
			for (int y = 1; y < sao; y++) {
				chuoiSao = chuoiSao + "&#9733;";
			}
			saoDanhGia.add(chuoiSao);
		}

		System.out.println(saoDanhGia.get(0));
		model.addAttribute("saoDanhGia", saoDanhGia);
		model.addAttribute("danhGiaList", danhGiaList);
		return "user/danhgia";
	}

	@PostMapping("danhgia/add")
	public String submitDanhGia(DanhGia dg, @RequestParam int DANHGIA, @RequestParam String BINHLUAN,
			@RequestParam String TENTUYEN, RedirectAttributes redirectAttributes) {

		try {
			TaiKhoan tk = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));
			Tuyen tuyen = ttservice.findByTuyen(TENTUYEN);
			dg.setTAIKHOAN(tk);
			dg.setTUYEN(tuyen);
			dg.setDANHGIA(DANHGIA);
			dg.setBINHLUAN(BINHLUAN);

			redirectAttributes.addFlashAttribute("thongbao", "Đánh giá của bạn đã được gửi thành công");
			danhGiaDAO.save(dg);

		} catch (Exception e) {
			// Xử lý ngoại lệ, ghi log hoặc hiển thị thông báo lỗi
			e.printStackTrace(); // Thay thế bằng xử lý lỗi phù hợp
			redirectAttributes.addFlashAttribute("loi", "Đã xảy ra lỗi khi gửi đánh giá");
		}

		return "redirect:/danhgia";
	}

	@GetMapping("/getiddanhgia/{id}")
	public ResponseEntity<DanhGia> getDanhGia(@PathVariable String id) {
		DanhGia danhGia = dgsv.findById(Integer.parseInt(id));
		danhGiaDAO.delete(danhGia);
		return ResponseEntity.ok(danhGia);
	}

	// Các phương thức khác...
}
