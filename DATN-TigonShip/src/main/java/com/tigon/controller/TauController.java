//package com.tigon.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.tigon.model.Tau;
//import com.tigon.service.TauService;
//@Controller
//public class TauController {
//	@Autowired
//	TauService tauService;
//	
//	
//	@RequestMapping("/product/list")
//	public String list(Model model, @RequestParam("hid") Optional<Integer> hid) {
//		if(hid.isPresent()) {
//			List<Tau> list = tauService.findByHangTauId(hid.get());
//			model.addAttribute("taus",list);
//		}
//		else {
//			List<Tau> list = tauService.findAll();
//			model.addAttribute("taus",list);
//		}
//		
//		return "product/list";
//	}
//	
//	@RequestMapping("/product/detail/{id}")
//	public String detail(Model model, @PathVariable("id") Integer id) {
//		Product item = productService.findById(id);
//		model.addAttribute("item", item);
//		return "product/detail";
//	}	
//	
//}
