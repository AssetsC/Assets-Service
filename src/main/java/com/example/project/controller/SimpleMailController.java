package com.example.project.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleMailController {
	@Autowired
	private JavaMailSender sender;

	@CrossOrigin
	@RequestMapping("/sendMail")
	public boolean sendMail(@RequestParam String email , @RequestParam String name , @RequestParam String subject , @RequestParam String content , @RequestParam String toEmail) throws UnsupportedEncodingException, MessagingException {
		var message = sender.createMimeMessage();
		var helper = new MimeMessageHelper(message);
		
		message.setFrom(new InternetAddress(email,name));
		
		try {
			helper.setTo(toEmail);
			message.setContent(content, "text/html;charset=UTF-8");
			helper.setSubject(subject);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		sender.send(message);
		return true;
	}

//	@RequestMapping("/sendMailAtt")
//	public String sendMailAttachment() throws MessagingException {
//		MimeMessage message = sender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message,true);
//		try {
//			helper.setTo("demo@gmail.com");
//			helper.setText("Greetings :)\n Please find the attached docuemnt for your reference.");
//			helper.setSubject("Mail From Spring Boot");
//			ClassPathResource file = new ClassPathResource("document.PNG");
//			helper.addAttachment("document.PNG", file);
//		} catch (MessagingException e) {
//			e.printStackTrace();
//			return "Error while sending mail ..";
//		}
//		sender.send(message);
//		return "Mail Sent Success!";
//	}

}
