package com.tigon.controller;

import java.util.Date;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tigon.dao.BangtinDAO;
import com.tigon.model.Bangtin;

@Controller

public class BangtinController {

	@Autowired
	BangtinDAO dao;

	
	@RequestMapping("/admin/bangtin")
	public String bangtin(Model model) {
		List<Bangtin> list = dao.findAll();
		model.addAttribute("items", list);
		return "/admin/tintuc/dangbangtin";
	}

	@RequestMapping("/add")
	public String create( @RequestParam String tieu_de, @RequestParam String noi_dung, Bangtin bt) {
		
		bt.setTieu_de(tieu_de);
		bt.setNoi_dung(noi_dung);

		dao.save(bt);

		
		return "redirect:/admin/bangtin";
	}

	@RequestMapping("/delete")
	public String delete(Model model, @RequestParam String tieu_de,
			@RequestParam String noi_dung, Bangtin bt) {

		
		  System.out.println(tieu_de); dao.deletetieu_de(tieu_de);
		  
		  List<Bangtin> list = dao.findAll(); model.addAttribute("items", list);
		
		/* dao.deleteById(id); */
		return "redirect:/admin/bangtin";
	}
}
