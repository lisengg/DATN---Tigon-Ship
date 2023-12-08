package com.tigon.controller;

import java.util.Date;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.BangtinDAO;
import com.tigon.model.Bangtin;
import com.tigon.service.BangtinService;

@Controller
@Service
public class BangtinController {

	@Autowired
	BangtinDAO dao;
	@Autowired
	BangtinService service;

	@RequestMapping("/admin/bangtin")
	public String bangtin(Model model) {
		List<Bangtin> list = dao.findAll();
		model.addAttribute("items", list);
		return "/admin/tintuc/dangbangtin";
	}

	@RequestMapping("/add")
	public String create(@RequestParam String tieu_de, @RequestParam String noi_dung, Bangtin bt) {

		bt.setTIEUDE(tieu_de);
		bt.setNOIDUNG(noi_dung);

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
		model.addAttribute("tieude", bt.getTIEUDE());
		model.addAttribute("noidung", bt.getNOIDUNG());
		model.addAttribute("hinhanh", bt.getHINHANH());
		System.out.println(bt.getTIEUDE());
		System.out.println(bt.getTIEUDE());
		System.out.println(bt.getIDBANGTIN());
		List<Bangtin> list = dao.findAll();
		model.addAttribute("items", list);
		
		return "/admin/tintuc/dangbangtin";
	}

	@RequestMapping("/update")
	public String luu(Model model, @RequestParam int id, @RequestParam String tieu_de, @RequestParam String noi_dung, Bangtin bt) {
		
		
		bt.setIDBANGTIN(id);
		bt.setTIEUDE(tieu_de);
		bt.setNOIDUNG(noi_dung);

		dao.save(bt);
		return "redirect:/admin/bangtin";
	}

}
