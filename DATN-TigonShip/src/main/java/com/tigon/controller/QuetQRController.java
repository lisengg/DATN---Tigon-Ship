package com.tigon.controller;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.model.DatGhe;
import com.tigon.model.HoaDon;
import com.tigon.model.NguoiDiCung;
import com.tigon.service.DatGheService;
import com.tigon.service.HoaDonService;
import com.tigon.service.NguoiDiCungService;

@Controller
public class QuetQRController {

	@Autowired
	HttpSession session;

	@Autowired
	HoaDonService hoaDonService;
	
	@Autowired
	DatGheService datGheService;
	
	@Autowired
	NguoiDiCungService NDCService;

	@RequestMapping("/quetqr")
	public String quetQR() {
		return "/user/datve/quetqr";
	}

	@GetMapping("/thongtinhoadonqr")
	public String showQRInfo(@RequestParam(name = "qrData", required = false) String qrData, Model model,
			HttpServletRequest request) {
		// tìm hóa đơn theo mã QR code
		HoaDon hoadon = hoaDonService.findById(Integer.parseInt(qrData));

		// set thông tin hóa đơn
		model.addAttribute("mahoadon", hoadon.getMAHD());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ngayDatFormatted = sdf.format(hoadon.getNGAYLAP());
		model.addAttribute("ngaydat", ngayDatFormatted);
		if (hoadon.getDATVE().getHANHKHACH() != null) {
			model.addAttribute("hoten", hoadon.getDATVE().getHANHKHACH().getHOVATEN());
		} else {
			model.addAttribute("hoten", hoadon.getDATVE().getTAIKHOAN().getHOVATEN());
		}
		System.out.println("ok");
		model.addAttribute("tuyen", hoadon.getDATVE().getLICHTAUCHAY().getTUYEN().getTENTUYEN());
		model.addAttribute("loaive", hoadon.getDATVE().getLOAIVE().getLOAIVE());
		model.addAttribute("soluongkhach", hoadon.getDATVE().getSOGHE());
		NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		String giaTienMoiFormatted = vndFormat.format(hoadon.getTONGTIEN());
		model.addAttribute("tongtien", giaTienMoiFormatted);
		model.addAttribute("loaithanhtoan", hoadon.getLOAITHANHTOAN());

		// set thông tin chuyến đi
		model.addAttribute("tentau", hoadon.getDATVE().getLICHTAUCHAY().getTAU().getTENTAU());
		String ngayDiFormatted = sdf.format(hoadon.getDATVE().getNGAYDI());
		model.addAttribute("ngaydi", ngayDiFormatted);
		model.addAttribute("giodi", hoadon.getDATVE().getLICHTAUCHAY().getGIOXUATPHAT());
		model.addAttribute("gioden", hoadon.getDATVE().getLICHTAUCHAY().getGIODENNOI());

		// set hành khách chính
		List<DatGhe> datghe = datGheService.layDanhSachGheTheoMaDatVe(hoadon.getDATVE().getMADATVE());
		if (hoadon.getDATVE().getHANHKHACH() != null) {
		model.addAttribute("hotenhkchinh", hoadon.getDATVE().getHANHKHACH().getHOVATEN());
		}else {
			model.addAttribute("hotenhkchinh", hoadon.getDATVE().getTAIKHOAN().getHOVATEN());
		}
		model.addAttribute("ghengoihkchinh", datghe.get(0).getGHENGOI().getTENGHE());
		System.out.println("Kích thước của datghe: " + datghe.size());

		List<String> danhSachTenGhe = new ArrayList<>();
		if (datghe.size() >= 2) {
			for (int i = 1; i < datghe.size(); i++) {
				String tenGhe = datghe.get(i).getGHENGOI().getTENGHE();
				danhSachTenGhe.add(tenGhe);
			}
		} else {
			System.out.println("Danh sách datghe không đủ phần tử để thực hiện vòng lặp.");
		}

		if (datghe.size() > 1) {
			model.addAttribute("danhSachTenGhe", danhSachTenGhe);
		}
		List<NguoiDiCung> listNDC = NDCService.ListNguoiDiCungByiddatve(hoadon.getDATVE().getMADATVE());
		model.addAttribute("nguoidicung", listNDC);
		model.addAttribute("loaivehkchinh", "Người lớn");
		return "/user/datve/thongtinhoadon";
	}

}
