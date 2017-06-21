package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value="/mav")
public class ModelAndViewController {

	@RequestMapping(value="/login")//,method=RequestMethod.POST
	public ModelAndView name() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("MavTest");
		mav.addObject("message", "message1");
		return mav;
	}
}
