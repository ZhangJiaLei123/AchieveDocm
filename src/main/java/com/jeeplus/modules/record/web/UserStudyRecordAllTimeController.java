/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.record.web;

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
import com.jeeplus.modules.record.entity.UserStudyRecordAllTime;
import com.jeeplus.modules.record.service.UserStudyRecordAllTimeService;

/**
 * recordController
 * @author w
 * @version 2017-06-29
 */
@Controller
@RequestMapping(value = "${adminPath}/record/userStudyRecordAllTime")
public class UserStudyRecordAllTimeController extends BaseController {

	@Autowired
	private UserStudyRecordAllTimeService userStudyRecordAllTimeService;
	
	@ModelAttribute
	public UserStudyRecordAllTime get(@RequestParam(required=false) String id) {
		UserStudyRecordAllTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userStudyRecordAllTimeService.get(id);
		}
		if (entity == null){
			entity = new UserStudyRecordAllTime();
		}
		return entity;
	}
	
	/**
	 * 记录列表页面
	 */
	@RequiresPermissions("record:userStudyRecordAllTime:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserStudyRecordAllTime userStudyRecordAllTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserStudyRecordAllTime> page = userStudyRecordAllTimeService.findPage(new Page<UserStudyRecordAllTime>(request, response), userStudyRecordAllTime); 
		model.addAttribute("page", page);
		return "modules/record/userStudyRecordAllTimeList";
	}

	/**
	 * 查看，增加，编辑记录表单页面
	 */
	@RequiresPermissions(value={"record:userStudyRecordAllTime:view","record:userStudyRecordAllTime:add","record:userStudyRecordAllTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserStudyRecordAllTime userStudyRecordAllTime, Model model) {
		model.addAttribute("userStudyRecordAllTime", userStudyRecordAllTime);
		return "modules/record/userStudyRecordAllTimeForm";
	}

	/**
	 * 保存记录
	 */
	@RequiresPermissions(value={"record:userStudyRecordAllTime:add","record:userStudyRecordAllTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserStudyRecordAllTime userStudyRecordAllTime, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userStudyRecordAllTime)){
			return form(userStudyRecordAllTime, model);
		}
		if(!userStudyRecordAllTime.getIsNewRecord()){//编辑表单保存
			UserStudyRecordAllTime t = userStudyRecordAllTimeService.get(userStudyRecordAllTime.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userStudyRecordAllTime, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userStudyRecordAllTimeService.save(t);//保存
		}else{//新增表单保存
			userStudyRecordAllTimeService.save(userStudyRecordAllTime);//保存
		}
		addMessage(redirectAttributes, "保存记录成功");
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordAllTime/?repage";
	}
	
	/**
	 * 删除记录
	 */
	@RequiresPermissions("record:userStudyRecordAllTime:del")
	@RequestMapping(value = "delete")
	public String delete(UserStudyRecordAllTime userStudyRecordAllTime, RedirectAttributes redirectAttributes) {
		userStudyRecordAllTimeService.delete(userStudyRecordAllTime);
		addMessage(redirectAttributes, "删除记录成功");
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordAllTime/?repage";
	}
	
	/**
	 * 批量删除记录
	 */
	@RequiresPermissions("record:userStudyRecordAllTime:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userStudyRecordAllTimeService.delete(userStudyRecordAllTimeService.get(id));
		}
		addMessage(redirectAttributes, "删除记录成功");
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordAllTime/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("record:userStudyRecordAllTime:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserStudyRecordAllTime userStudyRecordAllTime, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserStudyRecordAllTime> page = userStudyRecordAllTimeService.findPage(new Page<UserStudyRecordAllTime>(request, response, -1), userStudyRecordAllTime);
    		new ExportExcel("记录", UserStudyRecordAllTime.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordAllTime/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("record:userStudyRecordAllTime:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserStudyRecordAllTime> list = ei.getDataList(UserStudyRecordAllTime.class);
			for (UserStudyRecordAllTime userStudyRecordAllTime : list){
				try{
					userStudyRecordAllTimeService.save(userStudyRecordAllTime);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordAllTime/?repage";
    }
	
	/**
	 * 下载导入记录数据模板
	 */
	@RequiresPermissions("record:userStudyRecordAllTime:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "记录数据导入模板.xlsx";
    		List<UserStudyRecordAllTime> list = Lists.newArrayList(); 
    		new ExportExcel("记录数据", UserStudyRecordAllTime.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordAllTime/?repage";
    }
	
	
	

}