package com.example.demo.util.inteceptor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截器配置
 * 
 * @author server
 *
 */
public class Inteceptor extends HandlerInterceptorAdapter {
	private static Set<String> path = new HashSet<String>();
	static { // 不拦截的路径
		path.add("/mutifile.html");
	}

	/**
	 * 拦截请求 前，此处可设置session,characterEncoding
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long start = System.currentTimeMillis();
		request.setAttribute("start", start);
		if (path.contains(request.getRequestURI())) {
			System.out.println("URL：" + request.getRequestURI());
			return true;
		}
		System.out.println("----redirect");
		response.sendRedirect("file");//.html
		return false;
	}

	/**
	 * 拦截请求完成后执行的操作
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long start = (long) request.getAttribute("start");
		long now = System.currentTimeMillis();
		System.out.println("-----:" + (now - start));
		request.setAttribute("time", new Date().toLocaleString());
		;
	}

}
