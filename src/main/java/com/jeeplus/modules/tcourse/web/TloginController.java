package com.jeeplus.modules.tcourse.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.modules.tcourse.service.TeacherService;
import com.yfhl.commons.utils.CheckUserPassword;
@Controller
public class TloginController {
	@Autowired
	TeacherService iTeacherService;
	/**
	 * 教师端首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("userLogin")
	public String userLogin(HttpServletRequest request, HttpServletResponse response, Model model,String username,String userpwd) {
		Map<String, String> mapUser = iTeacherService.findUserByLoginName(username);
		if(mapUser!=null){
			if(CheckUserPassword.validatePassword(userpwd, mapUser.get("password").toString())){//验证通过
				  HttpSession session = request.getSession();   
				  session.setAttribute("LOGIN_USER", mapUser);
				  return "/teacherindex";
			}
		}
		return "/login";
	}
	/**
	 * 教师端首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model,String username,String userpwd) {
		System.out.println("=========login=========");
		return "/login";
	}
}
