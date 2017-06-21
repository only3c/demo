package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.SendMessageBean;
import com.example.demo.util.SendMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/mail")
@Api(value="邮件",tags={"MailController"})
public class MailController {

	@Autowired
	private SendMessage sendMessage;
	
	@ApiOperation(value= "普通邮件")
    @ApiImplicitParam(name = "bean", value = "邮件基本信息", required = true, dataType = "SendMessageBean")
	@RequestMapping(value="/send",method=RequestMethod.POST)
	public String send(@RequestBody SendMessageBean bean) {
		this.sendMessage.send(bean.getToUser(), bean.getCc(), bean.getSubject(), bean.getText());
		return "发送成功";
	}
	
	@ApiOperation(value= "HTMl邮件")
    @ApiImplicitParam(name = "bean", value = "邮件基本信息", required = true, dataType = "SendMessageBean")
	@RequestMapping(value="/send/html",method=RequestMethod.POST)
	public String sendHtml(@RequestBody SendMessageBean bean) {
		this.sendMessage.sendHtml(bean.getToUser(), bean.getCc(), bean.getSubject(), bean.getText());
		return "发送成功";
	}
}
