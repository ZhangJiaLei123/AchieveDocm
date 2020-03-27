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
import com.jeeplus.modules.question.entity.QuestionResult;
import com.jeeplus.modules.question.service.QuestionInfoService;
import com.jeeplus.modules.question.service.QuestionReleaseService;
import com.jeeplus.modules.question.service.QuestionResultService;

/**
 * 用户答题信息表Controller
 * @author wsp
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/question/questionResult")
public class QuestionResultController extends BaseController {

	@Autowired
	private QuestionResultService questionResultService;
	@Autowired
	private QuestionReleaseService questionReleaseService;
	@Autowired
	private QuestionInfoService questionInfoService;
	
	@ModelAttribute
	public QuestionResult get(@RequestParam(required=false) String id) {
		QuestionResult entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = questionResultService.get(id);
		}
		if (entity == null){
			entity = new QuestionResult();
		}
		return entity;
	}
	
	/**
	 * 用户答题信息表列表页面
	 */
	@RequiresPermissions("question:questionResult:list")
	@RequestMapping(value = {"list", ""})
	public String list(QuestionResult questionResult, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QuestionResult> page = questionResultService.findPage(new Page<QuestionResult>(request, response), questionResult); 
		model.addAttribute("page", page);
		return "modules/question/questionResultList";
	}

	/**
	 * 查看，增加，编辑用户答题信息表表单页面
	 */
	@RequiresPermissions(value={"question:questionResult:view","question:questionResult:add","question:questionResult:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(QuestionResult questionResult, Model model) {
		model.addAttribute("questionResult", questionResult);
		return "modules/question/questionResultForm";
	}

	/**
	 * 保存用户答题信息表
	 */
	@RequiresPermissions(value={"question:questionResult:add","question:questionResult:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(QuestionResult questionResult, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, questionResult)){
			return form(questionResult, model);
		}
		if(!questionResult.getIsNewRecord()){//编辑表单保存
			QuestionResult t = questionResultService.get(questionResult.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(questionResult, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			questionResultService.save(t);//保存
		}else{//新增表单保存
			questionResultService.save(questionResult);//保存
		}
		addMessage(redirectAttributes, "保存用户答题信息表成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionResult/?repage";
	}
	
	/**
	 * 删除用户答题信息表
	 */
	@RequiresPermissions("question:questionResult:del")
	@RequestMapping(value = "delete")
	public String delete(QuestionResult questionResult, RedirectAttributes redirectAttributes) {
		questionResultService.delete(questionResult);
		addMessage(redirectAttributes, "删除用户答题信息表成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionResult/?repage";
	}
	
	/**
	 * 批量删除用户答题信息表
	 */
	@RequiresPermissions("question:questionResult:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			questionResultService.delete(questionResultService.get(id));
		}
		addMessage(redirectAttributes, "删除用户答题信息表成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionResult/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("question:questionResult:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(QuestionResult questionResult, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户答题信息表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QuestionResult> page = questionResultService.findPage(new Page<QuestionResult>(request, response, -1), questionResult);
    		new ExportExcel("用户答题信息表", QuestionResult.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户答题信息表记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionResult/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("question:questionResult:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QuestionResult> list = ei.getDataList(QuestionResult.class);
			for (QuestionResult questionResult : list){
				try{
					questionResultService.save(questionResult);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户答题信息表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户答题信息表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户答题信息表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionResult/?repage";
    }
	
	/**
	 * 下载导入用户答题信息表数据模板
	 */
	@RequiresPermissions("question:questionResult:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户答题信息表数据导入模板.xlsx";
    		List<QuestionResult> list = Lists.newArrayList(); 
    		new ExportExcel("用户答题信息表数据", QuestionResult.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionResult/?repage";
    }
	
	/**
	 * 根据用户统计之后的信息列表
	 */
	@RequiresPermissions("question:questionResult:list")
	@RequestMapping(value = {"infolist"})
	public String infoList(@RequestParam(required=false) String releaseid, HttpServletRequest request, HttpServletResponse response, Model model) {
		QuestionResult questionResult = new QuestionResult();
		questionResult.setReleaseId(releaseid);
		QuestionRelease questionRelease = null;
		if (StringUtils.isNotBlank(releaseid)){
			questionRelease = questionReleaseService.get(releaseid);
		}
		if (questionRelease == null){
			questionRelease = new QuestionRelease();
		}
		QuestionInfo questionInfo = null;
		if (StringUtils.isNotBlank(questionRelease.getQuestionInfoId())){
			questionInfo = questionInfoService.get(questionRelease.getQuestionInfoId());
		}
		model.addAttribute("questionInfo", questionInfo);
		model.addAttribute("questionRelease", questionRelease);
		Page<QuestionResult> page = questionResultService.findPageGroupRid(new Page<QuestionResult>(request, response), questionResult); 
		model.addAttribute("page", page);
		return "modules/question/questionResultList";
	}
	

}