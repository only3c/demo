package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


@Entity
public class User implements Serializable{

	private static final long serialVersionUID = -4000582270233628443L;

	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Length(max=50)
	@Column(length=50,name="login_name")
	private String loginName;
	
	@NotNull
	@Length(max=50,min=6)
	@Column(length=50,name="login_password")
	private String loginPassword;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", loginPassword=" + loginPassword + "]";
	}
	
}
