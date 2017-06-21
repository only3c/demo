package com.example.demo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.util.ls.WordsToBase64;

@RestController
@RequestMapping("/base")
public class WordToBase64Controller {

	@RequestMapping(value="encode",method=RequestMethod.GET)
	public String encode(@RequestParam String name,HttpServletResponse response) {
		//System.out.println("1");
		//System.out.println(WordsToBase64.wordsToBase64(name,response));
		return WordsToBase64.wordsToBase64(name,response); 
	}
	
	@RequestMapping(value="decode",method=RequestMethod.GET)
	public String decode(@RequestParam String name,@RequestParam String imgStr,HttpServletResponse response) {
		//System.out.println("1");
		//System.out.println(WordsToBase64.wordsToBase64(name,response));
		WordsToBase64.deCode(name,imgStr, response);
		return "转换成功";
	}
}
