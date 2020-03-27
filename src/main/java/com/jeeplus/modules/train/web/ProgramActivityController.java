/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.web;

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
import com.jeeplus.modules.train.entity.ProgramActivity;
import com.jeeplus.modules.train.service.ProgramActivityService;

/**
 * 计划活动Controller
 * @author panjp
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/train/programActivity")
public class ProgramActivityController extends BaseController {

	@Autowired
	private ProgramActivityService programActivityService;
	
	@ModelAttribute
	public ProgramActivity get(@RequestParam(required=false) String id) {
		ProgramActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = programActivityService.get(id);
		}
		if (entity == null){
			entity = new ProgramActivity();
		}
		return entity;
	}
	
	/**
	 * 计划活动列表页面
	 */
	@RequiresPermissions("train:programActivity:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProgramActivity programActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProgramActivity> page = programActivityService.findPage(new Page<ProgramActivity>(request, response), programActivity); 
		model.addAttribute("page", page);
		return "modules/train/programActivityList";
	}

	/**
	 * 查看，增加，编辑计划活动表单页面
	 */
	@RequiresPermissions(value={"train:programActivity:view","train:programActivity:add","train:programActivity:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProgramActivity programActivity, Model model) {
		model.addAttribute("programActivity", programActivity);
		return "modules/train/programActivityForm";
	}

	/**
	 * 保存计划活动
	 */
	@RequiresPermissions(value={"train:programActivity:add","train:programActivity:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProgramActivity programActivity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, programActivity)){
			return form(programActivity, model);
		}
		if(!programActivity.getIsNewRecord()){//编辑表单保存
			ProgramActivity t = programActivityService.get(programActivity.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(programActivity, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			programActivityService.save(t);//保存
		}else{//新增表单保存
			programActivityService.save(programActivity);//保存
		}
		addMessage(redirectAttributes, "保存计划活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/programActivity/?repage";
	}
	
	/**
	 * 删除计划活动
	 */
	@RequiresPermissions("train:programActivity:del")
	@RequestMapping(value = "delete")
	public String delete(ProgramActivity programActivity, RedirectAttributes redirectAttributes) {
		programActivityService.delete(programActivity);
		addMessage(redirectAttributes, "删除计划活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/programActivity/?repage";
	}
	
	/**
	 * 批量删除计划活动
	 */
	@RequiresPermissions("train:programActivity:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			programActivityService.delete(programActivityService.get(id));
		}
		addMessage(redirectAttributes, "删除计划活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/programActivity/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:programActivity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ProgramActivity programActivity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "计划活动"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProgramActivity> page = programActivityService.findPage(new Page<ProgramActivity>(request, response, -1), programActivity);
    		new ExportExcel("计划活动", ProgramActivity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出计划活动记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/programActivity/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:programActivity:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProgramActivity> list = ei.getDataList(ProgramActivity.class);
			for (ProgramActivity programActivity : list){
				try{
					programActivityService.save(programActivity);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条计划活动记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条计划活动记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入计划活动失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/programActivity/?repage";
    }
	
	/**
	 * 下载导入计划活动数据模板
	 */
	@RequiresPermissions("train:programActivity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "计划活动数据导入模板.xlsx";
    		List<ProgramActivity> list = Lists.newArrayList(); 
    		new ExportExcel("计划活动数据", ProgramActivity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/programActivity/?repage";
    }
	
	
	

}