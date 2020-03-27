/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.web;

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
import com.jeeplus.modules.course.service.UserCourseRegService;

/**
 * 课程报名表Controller
 * @author wsp
 * @version 2017-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/course/userCourseReg")
public class UserCourseRegController extends BaseController {

	@Autowired
	private UserCourseRegService userCourseRegService;
	
	@ModelAttribute
	public UserCourseReg get(@RequestParam(required=false) String id) {
		UserCourseReg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userCourseRegService.get(id);
		}
		if (entity == null){
			entity = new UserCourseReg();
		}
		return entity;
	}
	
	/**
	 * 报名审核列表页面
	 */
	@RequiresPermissions("course:userCourseReg:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserCourseReg userCourseReg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserCourseReg> page = userCourseRegService.findPage(new Page<UserCourseReg>(request, response), userCourseReg); 
		model.addAttribute("page", page);
		model.addAttribute("courseid", userCourseReg.getCourseId());
		return "modules/course/userCourseRegList";
	}

	/**
	 * 查看，增加，编辑报名审核表单页面
	 */
	@RequiresPermissions(value={"course:userCourseReg:view","course:userCourseReg:add","course:userCourseReg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserCourseReg userCourseReg, Model model) {
		model.addAttribute("userCourseReg", userCourseReg);
		return "modules/course/userCourseRegForm";
	}

	/**
	 * 保存报名审核
	 */
	@RequiresPermissions(value={"course:userCourseReg:add","course:userCourseReg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserCourseReg userCourseReg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userCourseReg)){
			return form(userCourseReg, model);
		}
		if(!userCourseReg.getIsNewRecord()){//编辑表单保存
			UserCourseReg t = userCourseRegService.get(userCourseReg.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userCourseReg, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userCourseRegService.save(t);//保存
		}else{//新增表单保存
			userCourseRegService.save(userCourseReg);//保存
		}
		addMessage(redirectAttributes, "保存报名审核成功");
		return "redirect:"+Global.getAdminPath()+"/course/userCourseReg/?repage";
	}
	
	/**
	 * 删除报名审核
	 */
	@RequiresPermissions("course:userCourseReg:del")
	@RequestMapping(value = "delete")
	public String delete(UserCourseReg userCourseReg, RedirectAttributes redirectAttributes) {
		userCourseRegService.delete(userCourseReg);
		addMessage(redirectAttributes, "删除报名审核成功");
		return "redirect:"+Global.getAdminPath()+"/course/userCourseReg/?repage";
	}
	
	/**
	 * 批量删除报名审核
	 */
	@RequiresPermissions("course:userCourseReg:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userCourseRegService.delete(userCourseRegService.get(id));
		}
		addMessage(redirectAttributes, "删除报名审核成功");
		return "redirect:"+Global.getAdminPath()+"/course/userCourseReg/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("course:userCourseReg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserCourseReg userCourseReg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "报名审核"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserCourseReg> page = userCourseRegService.findPage(new Page<UserCourseReg>(request, response, -1), userCourseReg);
    		new ExportExcel("报名审核", UserCourseReg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出报名审核记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/userCourseReg/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("course:userCourseReg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserCourseReg> list = ei.getDataList(UserCourseReg.class);
			for (UserCourseReg userCourseReg : list){
				try{
					userCourseRegService.save(userCourseReg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条报名审核记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条报名审核记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入报名审核失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/userCourseReg/?repage";
    }
	
	/**
	 * 下载导入报名审核数据模板
	 */
	@RequiresPermissions("course:userCourseReg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "报名审核数据导入模板.xlsx";
    		List<UserCourseReg> list = Lists.newArrayList(); 
    		new ExportExcel("报名审核数据", UserCourseReg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/userCourseReg/?repage";
    }
	
	/**
	 * 报名列表
	 */
	@RequiresPermissions("course:userCourseReg:list")
	@RequestMapping(value = {"listbycourseid"})
	public String list(@RequestParam(required=false) String courseid, HttpServletRequest request, HttpServletResponse response, Model model) {
		UserCourseReg userCourseReg = new UserCourseReg();
		userCourseReg.setCourseId(courseid);
		Page<UserCourseReg> page = userCourseRegService.findPage(new Page<UserCourseReg>(request, response), userCourseReg); 
		model.addAttribute("page", page);
		model.addAttribute("courseid", courseid);
		return "modules/course/userCourseRegList";
	}

	/**
	 * 进行审核
	 */
	@RequiresPermissions("course:userCourseReg:edit")
	@RequestMapping(value = {"registerok"})
	public String registerOk(UserCourseReg userCourseReg, HttpServletRequest request, HttpServletResponse response, Model model) {
		userCourseReg.setAuditState("1");
		userCourseRegService.save(userCourseReg);
		UserCourseReg temp = new UserCourseReg();
		temp.setCourseId(userCourseReg.getCourseId());
		Page<UserCourseReg> page = userCourseRegService.findPage(new Page<UserCourseReg>(request, response), temp); 
		model.addAttribute("page", page);
		return "modules/course/userCourseRegList";
	}
	
	@RequiresPermissions("course:userCourseReg:edit")
	@RequestMapping(value = "registerokall")
	public String registerOkAll(@RequestParam(required=false) String courseid,String ids,  HttpServletRequest request, HttpServletResponse response, Model model) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			UserCourseReg usercourseReg = userCourseRegService.get(id);
			usercourseReg.setAuditState("1");
			userCourseRegService.save(usercourseReg);
		}
		addMessage(model, "审核成功");
		
		UserCourseReg temp = new UserCourseReg();
		temp.setCourseId(courseid);
		Page<UserCourseReg> page = userCourseRegService.findPage(new Page<UserCourseReg>(request, response), temp); 
		model.addAttribute("page", page);
		
		return "modules/course/userCourseRegList";
	}
	
	

}