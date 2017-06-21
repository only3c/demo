package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 仅仅是为了返回登陆页面
 * @author server
 *
 */
@Controller
public class LoginController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String helloWorld() {
		return "login";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	
}
