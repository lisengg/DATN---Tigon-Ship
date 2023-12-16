package com.tigon.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;

import com.tigon.model.DatGhe;
import com.tigon.model.HoaDon;
import com.tigon.model.NguoiDiCung;
import com.tigon.service.*;

@Controller
public class GuiHoaDonController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private GenerateQRService qrService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	HttpSession session;

	@Autowired
	HoaDonService hoaDonService;

	@Autowired
	DatGheService datGheService;

	@Autowired
	NguoiDiCungService NDCService;

	@RequestMapping("/guihoadon")
	public String guihoadon() throws IOException {
		String mahoadon = session.getAttribute("mahoadon").toString();
		HoaDon hoaDon = hoaDonService.findById(Integer.parseInt(mahoadon));
		String link = mahoadon;
		System.out.println("Ma hoa don : " + mahoadon);
		// set dữ liệu vào templates
		if (session.getAttribute("user").toString().equals("hanhkhachmoi")) {
			Context context = new Context();
			context.setVariable("servletContext", servletContext);
			context.setVariable("hoten", hoaDon.getDATVE().getHANHKHACH().getHOVATEN());
			context.setVariable("tuyen", hoaDon.getDATVE().getLICHTAUCHAY().getTUYEN().getTENTUYEN());
			context.setVariable("loaive", hoaDon.getDATVE().getLOAIVE().getLOAIVE());
			context.setVariable("soluongkhach", hoaDon.getDATVE().getSOGHE());

			if (!session.getAttribute("NgayVe").equals("ko")) {
				BigDecimal giaTien = hoaDon.getTONGTIEN();
				BigDecimal hai = new BigDecimal("2");
				BigDecimal giaTienMoi = giaTien.multiply(hai);
				// Định dạng giaTienMoi thành định dạng tiền VND
				NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				String giaTienMoiFormatted = vndFormat.format(giaTienMoi);
				context.setVariable("tongtien", giaTienMoiFormatted);
			} else {
				NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				String giaTienMoiFormatted = vndFormat.format(hoaDon.getTONGTIEN());
				context.setVariable("tongtien", giaTienMoiFormatted);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String ngayDatFormatted = sdf.format(hoaDon.getNGAYLAP());

			context.setVariable("ngaydat", ngayDatFormatted);
			context.setVariable("tentau", hoaDon.getDATVE().getLICHTAUCHAY().getTAU().getTENTAU());
			String ngayDiFormatted = sdf.format(hoaDon.getDATVE().getNGAYDI());
			context.setVariable("ngaydi", ngayDiFormatted);
			context.setVariable("giodi", hoaDon.getDATVE().getLICHTAUCHAY().getGIOXUATPHAT());
			context.setVariable("gioden", hoaDon.getDATVE().getLICHTAUCHAY().getGIODENNOI());

			// set hành khách chính
			List<DatGhe> datghe = datGheService.layDanhSachGheTheoMaDatVe(hoaDon.getDATVE().getMADATVE());
			context.setVariable("hotenhkchinh", hoaDon.getDATVE().getHANHKHACH().getHOVATEN());
			context.setVariable("ghengoihkchinh", datghe.get(0).getGHENGOI().getTENGHE());
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
				context.setVariable("danhSachTenGhe", danhSachTenGhe);
			}

			context.setVariable("loaivehkchinh", "Người lớn");
			List<NguoiDiCung> listNDC = NDCService.ListNguoiDiCungByiddatve(hoaDon.getDATVE().getMADATVE());
			context.setVariable("nguoidicung", listNDC);
			qrService.generateQRCode(link, mahoadon);
			emailService.sendEmailWithHtmlTemplateAndAttachment(hoaDon.getDATVE().getHANHKHACH().getEMAIL(),
					"Thông Tin Đặt Vé Tàu Tigon Ship", "/user/datve/hoadon", context,
					"src/main/resources/static/images/qr/mahoadon" + mahoadon + ".png");
			System.out.println("da gui ma hoa don");

			// có tài khoản
		} else {
			Context context = new Context();
			context.setVariable("servletContext", servletContext);
			context.setVariable("hoten", hoaDon.getDATVE().getTAIKHOAN().getHOVATEN());
			context.setVariable("tuyen", hoaDon.getDATVE().getLICHTAUCHAY().getTUYEN().getTENTUYEN());
			context.setVariable("loaive", hoaDon.getDATVE().getLOAIVE().getLOAIVE());
			context.setVariable("soluongkhach", hoaDon.getDATVE().getSOGHE());
			if (!session.getAttribute("NgayVe").equals("ko")) {
				BigDecimal giaTien = hoaDon.getTONGTIEN();
				BigDecimal hai = new BigDecimal("2");
				BigDecimal giaTienMoi = giaTien.multiply(hai);
				// Định dạng giaTienMoi thành định dạng tiền VND
				NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				String giaTienMoiFormatted = vndFormat.format(giaTienMoi);
				context.setVariable("tongtien", giaTienMoiFormatted);

			} else {
				NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				String giaTienMoiFormatted = vndFormat.format(hoaDon.getTONGTIEN());
				context.setVariable("tongtien", giaTienMoiFormatted);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String ngayDatFormatted = sdf.format(hoaDon.getNGAYLAP());

			context.setVariable("ngaydat", ngayDatFormatted);
			context.setVariable("tentau", hoaDon.getDATVE().getLICHTAUCHAY().getTAU().getTENTAU());
			String ngayDiFormatted = sdf.format(hoaDon.getDATVE().getNGAYDI());
			context.setVariable("ngaydi", ngayDiFormatted);
			context.setVariable("giodi", hoaDon.getDATVE().getLICHTAUCHAY().getGIOXUATPHAT());
			context.setVariable("gioden", hoaDon.getDATVE().getLICHTAUCHAY().getGIODENNOI());

			// set hành khách chính
			List<DatGhe> datghe = datGheService.layDanhSachGheTheoMaDatVe(hoaDon.getDATVE().getMADATVE());
			context.setVariable("hotenhkchinh", hoaDon.getDATVE().getTAIKHOAN().getHOVATEN());

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
				context.setVariable("danhSachTenGhe", danhSachTenGhe);
			}

			context.setVariable("ghengoihkchinh", datghe.get(0).getGHENGOI().getTENGHE());
			context.setVariable("loaivehkchinh", "Người lớn");
			List<NguoiDiCung> listNDC = NDCService.ListNguoiDiCungByiddatve(hoaDon.getDATVE().getMADATVE());
			context.setVariable("nguoidicung", listNDC);
			qrService.generateQRCode(link, mahoadon);

			emailService.sendEmailWithHtmlTemplateAndAttachment(hoaDon.getDATVE().getTAIKHOAN().getEMAIL(),
					"Thông Tin Đặt Vé Tàu Tigon Ship", "/user/datve/hoadon", context,
					"src/main/resources/static/images/qr/mahoadon" + mahoadon + ".png");
			System.out.println("da gui ma hoa don");
		}

		if (session.getAttribute("mahoadon_ve") != null) {
			return "redirect:/guihoadonve";
		}
		return "/user/index";
	}

	@RequestMapping("/guihoadonve")
	public String guihoadonve() throws IOException {
		String mahoadon = session.getAttribute("mahoadon_ve").toString();
		HoaDon hoaDon = hoaDonService.findById(Integer.parseInt(mahoadon));
		String link = mahoadon;
		System.out.println("Ma hoa don : " + mahoadon);
		// set dữ liệu vào templates
		if (session.getAttribute("user").toString().equals("hanhkhachmoi")) {
			Context context = new Context();
			context.setVariable("servletContext", servletContext);
			context.setVariable("hoten", hoaDon.getDATVE().getHANHKHACH().getHOVATEN());
			context.setVariable("tuyen", hoaDon.getDATVE().getLICHTAUCHAY().getTUYEN().getTENTUYEN());
			context.setVariable("loaive", hoaDon.getDATVE().getLOAIVE().getLOAIVE());
			context.setVariable("soluongkhach", hoaDon.getDATVE().getSOGHE());

			if (!session.getAttribute("NgayVe").equals("ko")) {
				BigDecimal giaTien = hoaDon.getTONGTIEN();
				BigDecimal hai = new BigDecimal("2");
				BigDecimal giaTienMoi = giaTien.multiply(hai);
				// Định dạng giaTienMoi thành định dạng tiền VND
				NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				String giaTienMoiFormatted = vndFormat.format(giaTienMoi);
				context.setVariable("tongtien", giaTienMoiFormatted);
			} else {
				NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				String giaTienMoiFormatted = vndFormat.format(hoaDon.getTONGTIEN());
				context.setVariable("tongtien", giaTienMoiFormatted);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String ngayDatFormatted = sdf.format(hoaDon.getNGAYLAP());

			context.setVariable("ngaydat", ngayDatFormatted);
			context.setVariable("tentau", hoaDon.getDATVE().getLICHTAUCHAY().getTAU().getTENTAU());
			String ngayDiFormatted = sdf.format(hoaDon.getDATVE().getNGAYDI());
			context.setVariable("ngaydi", ngayDiFormatted);
			context.setVariable("giodi", hoaDon.getDATVE().getLICHTAUCHAY().getGIOXUATPHAT());
			context.setVariable("gioden", hoaDon.getDATVE().getLICHTAUCHAY().getGIODENNOI());

			// set hành khách chính
			List<DatGhe> datghe = datGheService.layDanhSachGheTheoMaDatVe(hoaDon.getDATVE().getMADATVE());
			context.setVariable("hotenhkchinh", hoaDon.getDATVE().getHANHKHACH().getHOVATEN());
			context.setVariable("ghengoihkchinh", datghe.get(0).getGHENGOI().getTENGHE());
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
				context.setVariable("danhSachTenGhe", danhSachTenGhe);
			}

			context.setVariable("loaivehkchinh", "Người lớn");
			List<NguoiDiCung> listNDC = NDCService.ListNguoiDiCungByiddatve(hoaDon.getDATVE().getMADATVE());
			context.setVariable("nguoidicung", listNDC);
			qrService.generateQRCode(link, mahoadon);
			emailService.sendEmailWithHtmlTemplateAndAttachment(hoaDon.getDATVE().getHANHKHACH().getEMAIL(),
					"Thông Tin Đặt Vé Tàu Tigon Ship", "/user/datve/hoadon", context,
					"src/main/resources/static/images/qr/mahoadon" + mahoadon + ".png");
			System.out.println("da gui ma hoa don");

			// có tài khoản
		} else {
			Context context = new Context();
			context.setVariable("servletContext", servletContext);
			context.setVariable("hoten", hoaDon.getDATVE().getTAIKHOAN().getHOVATEN());
			context.setVariable("tuyen", hoaDon.getDATVE().getLICHTAUCHAY().getTUYEN().getTENTUYEN());
			context.setVariable("loaive", hoaDon.getDATVE().getLOAIVE().getLOAIVE());
			context.setVariable("soluongkhach", hoaDon.getDATVE().getSOGHE());
			if (!session.getAttribute("NgayVe").equals("ko")) {
				BigDecimal giaTien = hoaDon.getTONGTIEN();
				BigDecimal hai = new BigDecimal("2");
				BigDecimal giaTienMoi = giaTien.multiply(hai);
				// Định dạng giaTienMoi thành định dạng tiền VND
				NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				String giaTienMoiFormatted = vndFormat.format(giaTienMoi);
				context.setVariable("tongtien", giaTienMoiFormatted);

			} else {
				NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				String giaTienMoiFormatted = vndFormat.format(hoaDon.getTONGTIEN());
				context.setVariable("tongtien", giaTienMoiFormatted);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String ngayDatFormatted = sdf.format(hoaDon.getNGAYLAP());

			context.setVariable("ngaydat", ngayDatFormatted);
			context.setVariable("tentau", hoaDon.getDATVE().getLICHTAUCHAY().getTAU().getTENTAU());
			String ngayDiFormatted = sdf.format(hoaDon.getDATVE().getNGAYDI());
			context.setVariable("ngaydi", ngayDiFormatted);
			context.setVariable("giodi", hoaDon.getDATVE().getLICHTAUCHAY().getGIOXUATPHAT());
			context.setVariable("gioden", hoaDon.getDATVE().getLICHTAUCHAY().getGIODENNOI());

			// set hành khách chính
			List<DatGhe> datghe = datGheService.layDanhSachGheTheoMaDatVe(hoaDon.getDATVE().getMADATVE());
			context.setVariable("hotenhkchinh", hoaDon.getDATVE().getTAIKHOAN().getHOVATEN());

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
				context.setVariable("danhSachTenGhe", danhSachTenGhe);
			}

			context.setVariable("ghengoihkchinh", datghe.get(0).getGHENGOI().getTENGHE());
			context.setVariable("loaivehkchinh", "Người lớn");
			List<NguoiDiCung> listNDC = NDCService.ListNguoiDiCungByiddatve(hoaDon.getDATVE().getMADATVE());
			context.setVariable("nguoidicung", listNDC);
			qrService.generateQRCode(link, mahoadon);

			emailService.sendEmailWithHtmlTemplateAndAttachment(hoaDon.getDATVE().getTAIKHOAN().getEMAIL(),
					"Thông Tin Đặt Vé Tàu Tigon Ship", "/user/datve/hoadon", context,
					"src/main/resources/static/images/qr/mahoadon" + mahoadon + ".png");
			System.out.println("da gui ma hoa don");
		}

		return "/user/index";
	}

}
