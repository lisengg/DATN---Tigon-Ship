package com.tigon.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class App1 {

	@GetMapping("/redirect-to-facebook")
	public String redirectToFacebook() {
		// Redirect to the Facebook page
		return "redirect:https://www.facebook.com";

	}
}
