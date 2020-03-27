package com.jeeplus.modules.tcourse.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.modules.tcourse.domain.CustomerMember;
import com.yfhl.commons.web.BaseController;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年3月26日
 * @Description: 
 */
@Controller
@RequestMapping(value = "/login")
public class CustomerMemberController extends BaseController{
	
	/**
	 * 组织活动列表页面
	 */
	@RequestMapping("index")
	public String list(CustomerMember customerMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("---------------");
		return "/index";
	}
}
