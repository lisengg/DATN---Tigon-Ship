package com.tigon.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
public class EmailService {

	private final JavaMailSender javaMailSender;

	private final TemplateEngine templateEngine;

	@Autowired
	public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
		this.javaMailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	public void sendEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);

		javaMailSender.send(message);
	}

	public void sendEmailWithHtmlTemplate(String to, String subject, String templateName, Context context) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

		try {
			helper.setTo(to);
			helper.setSubject(subject);
			String htmlContent = templateEngine.process(templateName, context);
			helper.setText(htmlContent, true);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			System.out.println(e);
		}
	}
}
