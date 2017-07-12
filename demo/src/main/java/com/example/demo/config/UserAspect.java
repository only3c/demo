package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * aspect
 * @author caocc
 * 利用aop对请求进行拦截 进行记录log
 */
@Aspect
@Component //java 放进容器中
public class UserAspect {
	
	private final static Logger logger = LoggerFactory.getLogger(UserAspect.class);
	
	@Pointcut("execution(public * com.example.demo.controller.user.UserController.*(..))")
	public void log() {
		
	}
	
	@Before("log()")
	public void before(JoinPoint joinPoint) {
		//获取请求信息
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		logger.info("url={}",request.getRequestURL());
		logger.info("method={}",request.getMethod());
		logger.info("ip={}",request.getRemoteAddr());
		
		logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
		logger.info("args={}",joinPoint.getArgs());
		//获取类名
		//joinPoint.getSignature().getDeclaringTypeName();
		//获取方法名
		//joinPoint.getSignature().getName();
		//System.out.println("before"+"11111");
	}
	
	@After("log()")
	public void after() {
		System.out.println("after"+"22222");
	}
	
	@AfterReturning(returning="object",pointcut="log()")
	public void afterReturning(Object object) {
		logger.info("response={}",object);
	}
}
