package com.tigon.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.BangtinDAO;
import com.tigon.model.Bangtin;
import com.tigon.model.TaiKhoan;
import com.tigon.service.BangtinService;
import com.tigon.service.TaiKhoanService;

@Controller
public class BangtinController {

	@Autowired
	BangtinDAO dao;
	@Autowired
	BangtinService service;
	@Autowired
	TaiKhoanService tksv;
	@Autowired
	HttpSession session;

	@RequestMapping("/admin/bangtin")
	public String bangtin(Model model) {
		List<Bangtin> list = dao.findAll();
		model.addAttribute("items", list);
		return "/admin/tintuc/dangbangtin";
	}

	@RequestMapping("/add")
	public String create(@RequestParam String tieu_de, @RequestParam String noi_dung, @RequestParam String hinh_anh,
			Bangtin bt) {
		TaiKhoan tk = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));
		bt.setTIEUDE(tieu_de);
		bt.setNOIDUNG(noi_dung);
		bt.setHINHANH(hinh_anh);
		bt.setTAIKHOAN(tk);
		dao.save(bt);
		return "redirect:/admin/bangtin";
	}

	@RequestMapping("/delete")
	public String delete(Model model, @RequestParam String tieu_de, @RequestParam String noi_dung, Bangtin bt) {

		System.out.println(tieu_de);
		dao.deletetieu_de(tieu_de);
		List<Bangtin> list = dao.findAll();
		model.addAttribute("items", list);

		/* dao.deleteById(id); */
		return "redirect:/admin/bangtin";
	}

	@RequestMapping("/baidang/update/{id}")
	public String update(Model model, @PathVariable("id") String id) {
		Bangtin bt = service.findById(Integer.parseInt(id));

		model.addAttribute("tieu_de", bt.getTIEUDE());
		model.addAttribute("noi_dung", bt.getNOIDUNG());
		model.addAttribute("hinh_anh", bt.getHINHANH()); 	
		System.out.println(bt.getHINHANH());
		List<Bangtin> list = dao.findAll();
		model.addAttribute("items", list);

		return "/admin/tintuc/dangbangtin";
	}

	@RequestMapping("/update")
	public String luu(Model model, @RequestParam int id, @RequestParam String tieu_de, @RequestParam String noi_dung,
			@RequestParam String hinh_anh, Bangtin bt) {
		TaiKhoan tk = tksv.findById(Integer.parseInt(session.getAttribute("user").toString()));
		Bangtin bangtin = service.findBytieude(tieu_de);
System.out.println(tieu_de);
		String HINHANH = null;
		String hinhanh_old = bangtin.getHINHANH();
		System.out.println(id);
		if(hinh_anh==null) {
			hinh_anh=hinhanh_old;
		}
		bt.setIDBANGTIN(id);
		bt.setTIEUDE(tieu_de);
		bt.setNOIDUNG(noi_dung);
		bt.setHINHANH(hinh_anh);
		bt.setTAIKHOAN(tk);
		dao.save(bt);
		List<Bangtin> list = dao.findAll();
		model.addAttribute("items", list);
		return "/admin/tintuc/dangbangtin";
	}

}
