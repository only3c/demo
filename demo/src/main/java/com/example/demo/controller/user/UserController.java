package com.example.demo.controller.user;

import java.util.List;

import org.codehaus.groovy.util.Finalizable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.cyxl.SerializedField;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/user")
@Api(value="用户操作",tags= {"UserController"})
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@ApiOperation(value= "用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public User getUser(@PathVariable("id") Long id) {
		
		return userRepository.findOne(id);
	}
	
	@ApiOperation(value="创建用户")
	@ApiImplicitParam(name = "user" ,value="创建用户",required = true ,dataType = "User")
	@RequestMapping(value = "",method=RequestMethod.POST)
	public void postUser(@RequestBody User user) {
		userRepository.save(user);
	}
	
	@RequestMapping("/s/{id}")
    @SerializedField(includes = {"id", "email"}, encode = false)
    public User findUserById(@PathVariable("id")Long id){
        return userRepository.findOne(id);
    }

    @RequestMapping("/s/all")
    @SerializedField(excludes = {"id","loginPassword"})
    public List<User> findAllUser(){
        return userRepository.findAll();
    }
    
    @RequestMapping(value="/page",method=RequestMethod.POST)
    public Page<User> page(final Pageable pageable) {
    	return userRepository.findAll(pageable);
	}
}
