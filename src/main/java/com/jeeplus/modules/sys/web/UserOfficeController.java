/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.UserOffice;
import com.jeeplus.modules.sys.service.UserOfficeService;

/**
 * 用户组织关系Controller
 * @author panjp
 * @version 2017-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userOffice")
public class UserOfficeController extends BaseController {

	@Autowired
	private UserOfficeService userOfficeService;
	
	@Autowired
	private UserDao userDao;

	@ModelAttribute
	public UserOffice get(@RequestParam(required=false) String id) {
		UserOffice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userOfficeService.get(id);
		}
		if (entity == null){
			entity = new UserOffice();
		}
		return entity;
	}
	
	/**
	 * 用户组织关系列表页面
	 */
	@RequiresPermissions("sys:userOffice:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserOffice userOffice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserOffice> page = userOfficeService.findPage(new Page<UserOffice>(request, response), userOffice); 
		model.addAttribute("page", page);
		return "modules/sys/userOfficeList";
	}

	/**
	 * 查看，增加，编辑用户组织关系表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "form")
	public String form(UserOffice userOffice, Model model) {
		//根据用户id，查询用户的角色
		Map map = new HashMap();
		map.put("userId", userOffice.getUserId());
		Role role = userDao.selectRoleByUserId(map);
		User user = userDao.get(userOffice.getUserId());
		UserOffice sercherO = new UserOffice();
		sercherO.setUserId( userOffice.getUserId());
		List<UserOffice> lsUserOff = userOfficeService.findList(sercherO);
		String str = "";
		if(null!=lsUserOff && !lsUserOff.isEmpty()){
			for (int i = 0; i < lsUserOff.size(); i++) {
				str +=lsUserOff.get(i).getOfficeId()+",";
			}
		}
		userOffice.setOfficeStr(str);
		userOffice.setVerifyUrl(user.getVerifyUrl());
		model.addAttribute("userOffice", userOffice);
		model.addAttribute("role", role);
		return "modules/sys/userOfficeForm";
	}

	/**
	 * 保存用户组织关系
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save")
	public String save(UserOffice userOffice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userOffice)){
			return form(userOffice, model);
		}
		if(!userOffice.getIsNewRecord()){//编辑表单保存
			UserOffice t = userOfficeService.get(userOffice.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userOffice, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userOfficeService.save(t);//保存
		}else{//新增表单保存
			userOfficeService.save(userOffice);//保存
		}
		addMessage(redirectAttributes, "保存用户权限成功");
		return "redirect:"+Global.getAdminPath()+"/sys/user/showListUser";
	}
	/**
	 * 保存用户组织关系
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "userRoleSaveOffice")
	public String userRoleSaveOffice(UserOffice userOffice, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception{
		if (!beanValidator(model, userOffice)){
			return form(userOffice, model);
		}
		userOfficeService.saveUserRoleUserOffice(userOffice,request);
			addMessage(redirectAttributes, "保存用户权限成功");
		return "redirect:"+Global.getAdminPath()+"/sys/user/showListUser?repage";
	}
	
	/**
	 * 删除用户组织关系
	 */
	@RequiresPermissions("sys:userOffice:del")
	@RequestMapping(value = "delete")
	public String delete(UserOffice userOffice, RedirectAttributes redirectAttributes) {
		userOfficeService.delete(userOffice);
		addMessage(redirectAttributes, "删除用户组织关系成功");
		return "redirect:"+Global.getAdminPath()+"/sys/userOffice/?repage";
	}
	
	/**
	 * 批量删除用户组织关系
	 */
	@RequiresPermissions("sys:userOffice:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userOfficeService.delete(userOfficeService.get(id));
		}
		addMessage(redirectAttributes, "删除用户组织关系成功");
		return "redirect:"+Global.getAdminPath()+"/sys/userOffice/?repage";
	}
	@RequiresPermissions("user")
	@RequestMapping(value = "findAllRole")
	public void findAllRole(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");  
		List<Map> lsMap = userOfficeService.findAllRole();
		String printStr = new String();
		if(null!=lsMap && !lsMap.isEmpty()){
			printStr = JSONArray.toJSONString(lsMap);
		}
		try {
			response.getWriter().print(printStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}