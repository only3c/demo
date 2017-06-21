package com.example.demo.util.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务 实现方式1
 * 
 * @author server
 *
 */

   //每隔5秒执行一次：*/5 * * * * ?
   //每隔1分钟执行一次：0 */1 * * * ?
   //每天23点执行一次：0 0 23 * * ?
   //每天凌晨1点执行一次：0 0 1 * * ?
   //每月1号凌晨1点执行一次：0 0 1 1 * ?
   //每月最后一天23点执行一次：0 0 23 L * ?
   //每周星期天凌晨1点实行一次：0 0 1 ? * L
   //在26分、29分、33分执行一次：0 26,29,33 * * * ?
   //每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
   //每隔5分钟执行一次：0 0/5 * * * ?
@Configuration 
@EnableScheduling // 标注启动定时任务  这里是：关闭状态，拿下注释就开启了
public class SchudelTime {
	@Autowired
	TimerTask timerTask;
	/**
	 * cron内容分别是:秒，分，时，天，月，星期，年(可以省略年)
	 */
	@Scheduled(cron = "0/20 * * * * ?") // 每隔20秒执行一次
	public void doTask() {
		//System.out.println("-----每隔20秒执行一次 at:" + new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(new Date()));
	}

	@Scheduled(fixedRate = 1000 * 300) // 每隔300秒执行一次
	public void reportCurrentTime() {
		//System.out.println("每隔300秒执行一次: The time is now " + new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(new Date()));
	}

	@Scheduled(fixedRate = 1000 * 60, initialDelay = 1000) // 每隔1分钟执行一次，延迟1秒执行
	public void updatePayRecords() {
		System.out.println("每隔1分钟执行一次，延迟1秒执行: The time is now " + new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(new Date()));
	}
	
	@Scheduled(fixedRate = 1000 * 60) // 每隔60秒执行一次
	public void addUser() {
		//timerTask.addUser();
	}
}
