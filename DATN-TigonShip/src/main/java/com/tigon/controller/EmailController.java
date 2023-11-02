package com.tigon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tigon.model.EmailDetails;
import com.tigon.service.EmailService;

@RestController
//Class
public class EmailController {

	@Autowired
	private EmailService emailService;

	// Sending a simple Email
	@GetMapping("/sendMail")
	public String sendMail(EmailDetails details) {
		String status = emailService.sendSimpleMail(details);
		return status;
	}

	// Sending email with attachment
	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetails details) {
		String status = emailService.sendMailWithAttachment(details);

		return status;
	}
}
