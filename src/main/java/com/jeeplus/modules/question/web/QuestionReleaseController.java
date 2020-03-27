/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.web;

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
import com.jeeplus.modules.question.entity.QuestionInfo;
import com.jeeplus.modules.question.entity.QuestionRelease;
import com.jeeplus.modules.question.service.QuestionInfoService;
import com.jeeplus.modules.question.service.QuestionReleaseService;

/**
 * 问卷发布Controller
 * @author wsp
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/question/questionRelease")
public class QuestionReleaseController extends BaseController {

	@Autowired
	private QuestionReleaseService questionReleaseService;
	@Autowired
	private QuestionInfoService questionInfoService;
	
	@ModelAttribute
	public QuestionRelease get(@RequestParam(required=false) String id) {
		QuestionRelease entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = questionReleaseService.get(id);
		}
		if (entity == null){
			entity = new QuestionRelease();
		}
		return entity;
	}
	
	/**
	 * 问卷发布列表页面
	 */
	@RequiresPermissions("question:questionRelease:list")
	@RequestMapping(value = {"list", ""})
	public String list(QuestionRelease questionRelease, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QuestionRelease> page = questionReleaseService.findPage(new Page<QuestionRelease>(request, response), questionRelease); 
		model.addAttribute("page", page);
		return "modules/question/questionReleaseList";
	}

	/**
	 * 查看，增加，编辑问卷发布表单页面
	 */
	@RequiresPermissions(value={"question:questionRelease:view","question:questionRelease:add","question:questionRelease:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(QuestionRelease questionRelease, Model model) {
		model.addAttribute("questionRelease", questionRelease);
		return "modules/question/questionReleaseForm";
	}

	/**
	 * 保存问卷发布
	 */
	@RequiresPermissions(value={"question:questionRelease:add","question:questionRelease:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(QuestionRelease questionRelease, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, questionRelease)){
			return form(questionRelease, model);
		}
		if(!questionRelease.getIsNewRecord()){//编辑表单保存
			QuestionRelease t = questionReleaseService.get(questionRelease.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(questionRelease, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			questionReleaseService.save(t);//保存
		}else{//新增表单保存
			questionReleaseService.save(questionRelease);//保存
		}
		addMessage(redirectAttributes, "保存问卷发布成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionRelease/?repage";
	}
	
	/**
	 * 删除问卷发布
	 */
	@RequiresPermissions("question:questionRelease:del")
	@RequestMapping(value = "delete")
	public String delete(QuestionRelease questionRelease, RedirectAttributes redirectAttributes) {
		questionReleaseService.delete(questionRelease);
		addMessage(redirectAttributes, "删除问卷发布成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionRelease/?repage";
	}
	
	/**
	 * 批量删除问卷发布
	 */
	@RequiresPermissions("question:questionRelease:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			questionReleaseService.delete(questionReleaseService.get(id));
		}
		addMessage(redirectAttributes, "删除问卷发布成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionRelease/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("question:questionRelease:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(QuestionRelease questionRelease, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "问卷发布"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QuestionRelease> page = questionReleaseService.findPage(new Page<QuestionRelease>(request, response, -1), questionRelease);
    		new ExportExcel("问卷发布", QuestionRelease.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出问卷发布记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionRelease/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("question:questionRelease:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QuestionRelease> list = ei.getDataList(QuestionRelease.class);
			for (QuestionRelease questionRelease : list){
				try{
					questionReleaseService.save(questionRelease);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条问卷发布记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条问卷发布记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入问卷发布失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionRelease/?repage";
    }
	
	/**
	 * 下载导入问卷发布数据模板
	 */
	@RequiresPermissions("question:questionRelease:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "问卷发布数据导入模板.xlsx";
    		List<QuestionRelease> list = Lists.newArrayList(); 
    		new ExportExcel("问卷发布数据", QuestionRelease.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionRelease/?repage";
    }
	/**
	 * 发布问卷操作
	 */
	@RequiresPermissions(value={"question:questionRelease:view","question:questionRelease:add","question:questionRelease:edit"},logical=Logical.OR)
	@RequestMapping(value = "releaseform")
	public String realseForm(@RequestParam(required=false) String questionid, Model model) {
		model.addAttribute("questionid", questionid);
		return "modules/question/questionReleaseForm";
	}
	/**
	 *  发布问卷
	 */
	@RequiresPermissions(value={"question:questionRelease:add","question:questionRelease:edit"},logical=Logical.OR)
	@RequestMapping(value = "release")
	public String relase(QuestionRelease questionRelease, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, questionRelease)){
			return form(questionRelease, model);
		}
		if(!questionRelease.getIsNewRecord()){//编辑表单保存
			QuestionRelease t = questionReleaseService.get(questionRelease.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(questionRelease, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			questionReleaseService.save(t);//保存
		}else{//新增表单保存
			
			//修改问卷信息的状态
			QuestionInfo questionInfo = questionInfoService.get(questionRelease.getQuestionInfoId());
			questionInfo.setStatus("2");
			questionInfoService.save(questionInfo);//保存
			
			questionReleaseService.save(questionRelease);//保存
		}
		addMessage(redirectAttributes, "保存问卷发布成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionInfo/?repage";
//		return "redirect:"+Global.getAdminPath()+"/question/questionRelease/?repage";
	}
	
	/**
	 * 根据问卷id查询发布列表
	 */
	@RequiresPermissions("question:questionRelease:list")
	@RequestMapping(value = {"listbyquestionid"})
	public String listByQuestionId(@RequestParam(required=false) String questionid, HttpServletRequest request, HttpServletResponse response, Model model) {
		QuestionRelease questionReleaseTmp = new QuestionRelease();
		questionReleaseTmp.setQuestionInfoId(questionid);
		QuestionInfo entity = null;
		if (StringUtils.isNotBlank(questionid)){
			entity = questionInfoService.get(questionid);
		}
		if (entity == null){
			entity = new QuestionInfo();
		}
		model.addAttribute("questionInfo", entity);
		Page<QuestionRelease> page = questionReleaseService.findPage(new Page<QuestionRelease>(request, response), questionReleaseTmp); 
		model.addAttribute("page", page);
		return "modules/question/questionReleaseList";
	}

}