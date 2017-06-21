package com.example.demo.util.time;

//import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

//import org.springframework.scheduling.annotation.EnableScheduling;

/**  定时任务实现方式 2 
 * 在具体时间点执行任务，不过这个任务需要在Application 启动，也可以在其他定时任务里启动这个类   目前TimerTask类中启动
 * @author server  
 */
public class TimerDemo {
	public void dosomething(int second) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, 10); // 时
//		calendar.set(Calendar.MINUTE, 14);// 分
//		calendar.set(Calendar.SECOND, 00); // 秒
//		Date date = calendar.getTime(); // 第一次执行定时任务的时间
//		// 如果第一次执行定时任务的时间 小于当前的时间
//		// 此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
//		if (date.before(new Date())) {
//			calendar.add(Calendar.DAY_OF_MONTH, 1);
//			date = TimerDemo.addDay(date, 1);
//		}
//		System.out.println("exe Time:" + new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(date));
		Timer timer = new Timer();
		TimerTask task = new TimerTask();// 具体的任务类
		timer.schedule(task, second*1000);// 任务类，第一次执行时间点，延迟时间
	}
	// 增加或减少天数
	public static Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}
}
