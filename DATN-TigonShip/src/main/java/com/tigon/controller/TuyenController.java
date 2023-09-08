//package com.tigon.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.tigon.model.Tuyen;
//import com.tigon.service.TuyenService;
//
//@Controller
//@RequestMapping("/admin")
//public class TuyenController {
//	  @Autowired
//	  private TuyenService tuyenService;
//	  
//	  @GetMapping("/tuyentau")
//	    public String tuyentau(Model model,@Param("keyword") String keyword,@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo) {
//		  Page<Tuyen> list = this.tuyenService.getAll(pageNo);
//		  
//		  model.addAttribute("totalPage", list.getTotalPages());
//		  model.addAttribute("currentPage", pageNo);
//		    model.addAttribute("list", list);
//	        return "admin/tuyentau/tuyentau";
//	    }
//}
