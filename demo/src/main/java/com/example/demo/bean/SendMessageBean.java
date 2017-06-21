package com.example.demo.bean;

import javax.validation.constraints.NotNull;

/**
 * 发送邮件 内容Bean
 * @author Administrator
 *
 */
public class SendMessageBean {

	@NotNull
	private String[] toUser;
	private String[] cc;
	private String subject;
	private String text;
	public String[] getToUser() {
		return toUser;
	}
	public void setToUser(String[] toUser) {
		this.toUser = toUser;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
