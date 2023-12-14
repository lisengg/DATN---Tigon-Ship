package com.tigon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

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

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	        helper.setTo(to);
	        helper.setSubject(subject);

	        // Process and set HTML content from Thymeleaf template
	        String htmlContent = templateEngine.process(templateName, context);
	        helper.setText(htmlContent, true);

	        javaMailSender.send(mimeMessage);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}

	 public void sendEmailWithHtmlTemplateAndAttachment(String to, String subject, String templateName, Context context, String imagePath) {
		 MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		    
		    try {
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		        helper.setTo(to);
		        helper.setSubject(subject);

	            // Attach Image
	            Path path = Paths.get(imagePath);
	            byte[] imageBytes = Files.readAllBytes(path);
	            Resource imageResource = new ByteArrayResource(imageBytes);

	            // Add the image as an attachment
	            helper.addAttachment("QR_HoaDonDatVe.png", imageResource);

	            // Process and set HTML content from Thymeleaf template
	            String htmlContent = templateEngine.process(templateName, context);
	            helper.setText(htmlContent, true);

	            javaMailSender.send(mimeMessage);
	        } catch (MessagingException | IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	
}
