package com.jeeplus.modules.tcourse.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginHanderInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		System.out.println("-----拦截器---------------");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		String requestURI = request.getRequestURI();
		if (requestURI.indexOf("login") > 0 || requestURI.indexOf("userLogin") > 0) {
			return true;
		} else {
			// 说明处在编辑的页面
			HttpSession session = request.getSession();
			Object user = session.getAttribute("LOGIN_USER");
			if (user != null) {
				// 登陆成功的用户
				return true;
			} else {
				System.out.println("===============没有登陆");
				// 没有登陆，转向登陆界面
				request.getRequestDispatcher("/login").forward(request,
						response);
				return false;
			}
		}

	}

}
