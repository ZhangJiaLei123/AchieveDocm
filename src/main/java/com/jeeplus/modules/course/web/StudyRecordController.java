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
import com.jeeplus.modules.course.entity.StudyRecord;
import com.jeeplus.modules.course.service.StudyRecordService;

/**
 * 学习记录信息Controller
 * @author wsp
 * @version 2017-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/course/studyRecord")
public class StudyRecordController extends BaseController {

	@Autowired
	private StudyRecordService studyRecordService;
	
	@ModelAttribute
	public StudyRecord get(@RequestParam(required=false) String id) {
		StudyRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studyRecordService.get(id);
		}
		if (entity == null){
			entity = new StudyRecord();
		}
		return entity;
	}
	
	/**
	 * 学习记录信息列表页面
	 */
	@RequiresPermissions("course:studyRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list(StudyRecord studyRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudyRecord> page = studyRecordService.findPage(new Page<StudyRecord>(request, response), studyRecord); 
		model.addAttribute("page", page);
		return "modules/course/studyRecordList";
	}

	/**
	 * 查看，增加，编辑学习记录信息表单页面
	 */
	@RequiresPermissions(value={"course:studyRecord:view","course:studyRecord:add","course:studyRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(StudyRecord studyRecord, Model model) {
		model.addAttribute("studyRecord", studyRecord);
		return "modules/course/studyRecordForm";
	}

	/**
	 * 保存学习记录信息
	 */
	@RequiresPermissions(value={"course:studyRecord:add","course:studyRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(StudyRecord studyRecord, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, studyRecord)){
			return form(studyRecord, model);
		}
		if(!studyRecord.getIsNewRecord()){//编辑表单保存
			StudyRecord t = studyRecordService.get(studyRecord.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(studyRecord, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			studyRecordService.save(t);//保存
		}else{//新增表单保存
			studyRecordService.save(studyRecord);//保存
		}
		addMessage(redirectAttributes, "保存学习记录信息成功");
		return "redirect:"+Global.getAdminPath()+"/course/studyRecord/?repage";
	}
	
	/**
	 * 删除学习记录信息
	 */
	@RequiresPermissions("course:studyRecord:del")
	@RequestMapping(value = "delete")
	public String delete(StudyRecord studyRecord, RedirectAttributes redirectAttributes) {
		studyRecordService.delete(studyRecord);
		addMessage(redirectAttributes, "删除学习记录信息成功");
		return "redirect:"+Global.getAdminPath()+"/course/studyRecord/?repage";
	}
	
	/**
	 * 批量删除学习记录信息
	 */
	@RequiresPermissions("course:studyRecord:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			studyRecordService.delete(studyRecordService.get(id));
		}
		addMessage(redirectAttributes, "删除学习记录信息成功");
		return "redirect:"+Global.getAdminPath()+"/course/studyRecord/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("course:studyRecord:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(StudyRecord studyRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学习记录信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StudyRecord> page = studyRecordService.findPage(new Page<StudyRecord>(request, response, -1), studyRecord);
    		new ExportExcel("学习记录信息", StudyRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学习记录信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/studyRecord/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("course:studyRecord:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StudyRecord> list = ei.getDataList(StudyRecord.class);
			for (StudyRecord studyRecord : list){
				try{
					studyRecordService.save(studyRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条学习记录信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条学习记录信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入学习记录信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/studyRecord/?repage";
    }
	
	/**
	 * 下载导入学习记录信息数据模板
	 */
	@RequiresPermissions("course:studyRecord:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学习记录信息数据导入模板.xlsx";
    		List<StudyRecord> list = Lists.newArrayList(); 
    		new ExportExcel("学习记录信息数据", StudyRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/studyRecord/?repage";
    }
	
	
	

}