package com.tigon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class home {
	@RequestMapping("/index")
	public String index(Model model) {
		return "/admin/index";
	}
}
