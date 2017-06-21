package com.example.demo.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendMessage {
	
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromUser;
	
	public void send(String[] toUser,String[] cc, String subject,String text) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromUser);
		message.setTo(toUser);
		message.setCc(cc);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}
	
	public void sendHtml(String[] toUser,String[] cc, String subject,String text) {
		MimeMessage message = mailSender.createMimeMessage();
		//SimpleMailMessage message = new SimpleMailMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message,true);
			helper.setFrom(fromUser);
			helper.setTo(toUser);
			helper.setCc(cc);
			helper.setSubject(subject);
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("<h1>请假条</h1>").append("<p style='color:#F00'>申请事假1天</p>").append("<p>"+text+"</p>");
			helper.setText(sBuffer.toString(),true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mailSender.send(message);
	}
}
