package com.example.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	UserRepository userRepository;
	@Test
	public void contextLoads() {
	}

	@Test
	public void addUsera() {
		List<User> list = userRepository.findAllByLoginNameContainingOrderByIdDesc("TimerTask");
		User user = new User();
		if(list!=null && list.size()>0) {
			Long ong = Long.valueOf(list.get(0).getLoginName().substring(9))+1;
			user.setLoginName("TimerTask"+ong);
		}else {
			user.setLoginName("TimerTask1");
		}
		user.setLoginPassword("123456");
		userRepository.save(user);
		
		System.out.println("用户："+user.getLoginName()+"插入成功");
	}
}
