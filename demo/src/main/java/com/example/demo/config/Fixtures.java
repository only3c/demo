package com.example.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.time.TimerDemo;

/**
 * 
 * @author Administrator
 * Ordered:Spring中提供了一个Ordered接口。Ordered接口，就是用来排序的。
 */
@Component
@Transactional
public class Fixtures implements Ordered,ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		//this.addUser();
		//this.timer();
	}
	
	private void addUser() {
		User user = new User();
		user.setLoginName("caocc");
		user.setLoginPassword("123456");
		userRepository.save(user);
	}

	private void timer() {
		TimerDemo timerDemo = new TimerDemo();
		timerDemo.dosomething(20);
	}
}
