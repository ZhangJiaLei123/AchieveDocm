/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.course.entity.UserCourseReg;
import com.jeeplus.modules.train.entity.UserActivityReg;
import com.jeeplus.modules.train.service.UserActivityRegService;

/**
 * 活动报名审核表Controller
 * @author wsp
 * @version 2017-04-02
 */
@Controller
@RequestMapping(value = "${adminPath}/train/userActivityReg")
public class UserActivityRegController extends BaseController {

	@Autowired
	private UserActivityRegService userActivityRegService;
	
	@ModelAttribute
	public UserActivityReg get(@RequestParam(required=false) String id) {
		UserActivityReg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userActivityRegService.get(id);
		}
		if (entity == null){
			entity = new UserActivityReg();
		}
		return entity;
	}
	
	/**
	 * 活动报名审核列表页面
	 */
	@RequiresPermissions("train:userActivityReg:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserActivityReg userActivityReg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserActivityReg> page = userActivityRegService.findPage(new Page<UserActivityReg>(request, response), userActivityReg); 
		model.addAttribute("page", page);
		return "modules/train/userActivityRegList";
	}

	/**
	 * 查看，增加，编辑活动报名审核表单页面
	 */
	@RequiresPermissions(value={"train:userActivityReg:view","train:userActivityReg:add","train:userActivityReg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserActivityReg userActivityReg, Model model) {
		model.addAttribute("userActivityReg", userActivityReg);
		return "modules/train/userActivityRegForm";
	}

	/**
	 * 保存活动报名审核
	 */
	@RequiresPermissions(value={"train:userActivityReg:add","train:userActivityReg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserActivityReg userActivityReg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userActivityReg)){
			return form(userActivityReg, model);
		}
		if(!userActivityReg.getIsNewRecord()){//编辑表单保存
			UserActivityReg t = userActivityRegService.get(userActivityReg.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userActivityReg, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userActivityRegService.save(t);//保存
		}else{//新增表单保存
			userActivityRegService.save(userActivityReg);//保存
		}
		addMessage(redirectAttributes, "保存活动报名审核成功");
		return "redirect:"+Global.getAdminPath()+"/train/userActivityReg/?repage";
	}
	
	/**
	 * 删除活动报名审核
	 */
	@RequiresPermissions("train:userActivityReg:del")
	@RequestMapping(value = "delete")
	public String delete(UserActivityReg userActivityReg, RedirectAttributes redirectAttributes) {
		userActivityRegService.delete(userActivityReg);
		addMessage(redirectAttributes, "删除活动报名审核成功");
		return "redirect:"+Global.getAdminPath()+"/train/userActivityReg/?repage";
	}
	
	/**
	 * 批量删除活动报名审核
	 */
	@RequiresPermissions("train:userActivityReg:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userActivityRegService.delete(userActivityRegService.get(id));
		}
		addMessage(redirectAttributes, "删除活动报名审核成功");
		return "redirect:"+Global.getAdminPath()+"/train/userActivityReg/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:userActivityReg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserActivityReg userActivityReg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "活动报名审核"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserActivityReg> page = userActivityRegService.findPage(new Page<UserActivityReg>(request, response, -1), userActivityReg);
    		new ExportExcel("活动报名审核", UserActivityReg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出活动报名审核记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/userActivityReg/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:userActivityReg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserActivityReg> list = ei.getDataList(UserActivityReg.class);
			for (UserActivityReg userActivityReg : list){
				try{
					userActivityRegService.save(userActivityReg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条活动报名审核记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条活动报名审核记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入活动报名审核失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/userActivityReg/?repage";
    }
	
	/**
	 * 下载导入活动报名审核数据模板
	 */
	@RequiresPermissions("train:userActivityReg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "活动报名审核数据导入模板.xlsx";
    		List<UserActivityReg> list = Lists.newArrayList(); 
    		new ExportExcel("活动报名审核数据", UserActivityReg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/userActivityReg/?repage";
    }
	
	
	/**
	 * 查看报名列表
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"listbyactivityid"})
	public String list(@RequestParam(required=false) String activityid, HttpServletRequest request, HttpServletResponse response, Model model) {
		UserActivityReg userActivityReg = new UserActivityReg();
		userActivityReg.setActivityId(activityid);
		Page<UserActivityReg> page = userActivityRegService.findPage(new Page<UserActivityReg>(request, response), userActivityReg); 
		model.addAttribute("page", page);
		model.addAttribute("activityid", activityid);
		return "modules/train/userActivityRegList";
	}

	/**
	 * 进行审核
	 */
	@RequiresPermissions("train:userActivityReg:edit")
	@RequestMapping(value = {"registerok"})
	public String registerOk(UserActivityReg userActivityReg, HttpServletRequest request, HttpServletResponse response, Model model) {
		userActivityRegService.save(userActivityReg);
		UserActivityReg temp = new UserActivityReg();
		temp.setActivityId(userActivityReg.getActivityId());
		Page<UserActivityReg> page = userActivityRegService.findPage(new Page<UserActivityReg>(request, response), temp); 
		model.addAttribute("page", page);
		return "modules/train/userCourseRegList";
	}
	
	/*
	 * 进行批量审核
	 */
	@RequiresPermissions("train:userActivityReg:edit")
	@RequestMapping(value = "registerokall")
	public String registerOkAll(@RequestParam(required=false) String activityid,String ids,  HttpServletRequest request, HttpServletResponse response, Model model) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			UserActivityReg userActivityReg = userActivityRegService.get(id);
			userActivityReg.setAuditState("1");
			userActivityRegService.save(userActivityReg);
		}
		addMessage(model, "审核成功");
		
		UserActivityReg temp = new UserActivityReg();
		temp.setActivityId(activityid);
		Page<UserActivityReg> page = userActivityRegService.findPage(new Page<UserActivityReg>(request, response), temp); 
		model.addAttribute("page", page);
		
		return "modules/train/userCourseRegList";
	}
	
	/*
	 * 教师端活动报名审批
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherSpActityUser")
	public void teacherSpActityUser(String ids,String rdio,String textVlue,  HttpServletRequest request, HttpServletResponse response, Model model) {
		String str = "0";
		try {
			String idArray[] =ids.split(",");
			for(String id : idArray){
				UserActivityReg userActivityReg = userActivityRegService.get(id);
				userActivityReg.setAuditState(rdio);
				userActivityReg.setAuditAdvice(textVlue);
				userActivityRegService.save(userActivityReg);
			}
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}