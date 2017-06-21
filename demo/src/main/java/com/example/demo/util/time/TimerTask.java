package com.example.demo.util.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
//@Transactional
public class TimerTask extends java.util.TimerTask {

	@Autowired
	UserRepository userRepository;
	@Override
	public void run() {
		// 在这里做执行的任务
		System.out.println("TimerTask at:" + new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(new Date()));
		this.addUser();
	}
	
	public void addUser() {
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
