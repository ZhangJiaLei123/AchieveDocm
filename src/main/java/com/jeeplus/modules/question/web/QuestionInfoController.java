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
import com.jeeplus.modules.question.entity.QuestionProblem;
import com.jeeplus.modules.question.service.QuestionInfoService;
import com.jeeplus.modules.question.service.QuestionProblemService;

/**
 * 问卷信息Controller
 * @author panjp
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/question/questionInfo")
public class QuestionInfoController extends BaseController {

	@Autowired
	private QuestionInfoService questionInfoService;
	@Autowired
	private QuestionProblemService questionProblemService;
	
	@ModelAttribute
	public QuestionInfo get(@RequestParam(required=false) String id) {
		QuestionInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = questionInfoService.get(id);
		}
		if (entity == null){
			entity = new QuestionInfo();
		}
		return entity;
	}
	
	/**
	 * 问卷信息列表页面
	 */
	@RequiresPermissions("question:questionInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list(QuestionInfo questionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QuestionInfo> page = questionInfoService.findPage(new Page<QuestionInfo>(request, response), questionInfo); 
		model.addAttribute("page", page);
		return "modules/question/questionInfoList";
	}

	/**
	 * 查看，增加，编辑问卷信息表单页面
	 */
	@RequiresPermissions(value={"question:questionInfo:view","question:questionInfo:add","question:questionInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(QuestionInfo questionInfo, Model model) {
		model.addAttribute("questionInfo", questionInfo);
		return "modules/question/questionInfoForm";
	}

	/**
	 * 保存问卷信息
	 */
	@RequiresPermissions(value={"question:questionInfo:add","question:questionInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(QuestionInfo questionInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, questionInfo)){
			return form(questionInfo, model);
		}
		if(!questionInfo.getIsNewRecord()){//编辑表单保存
			QuestionInfo t = questionInfoService.get(questionInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(questionInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			questionInfoService.save(t);//保存
		}else{//新增表单保存
			questionInfo.setStatus("1");
			questionInfoService.save(questionInfo);//保存
		}
		addMessage(redirectAttributes, "保存问卷信息成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionInfo/?repage";
	}
	
	/**
	 * 删除问卷信息
	 */
	@RequiresPermissions("question:questionInfo:del")
	@RequestMapping(value = "delete")
	public String delete(QuestionInfo questionInfo, RedirectAttributes redirectAttributes) {
		questionInfo.setStatus("0");
		questionInfoService.save(questionInfo);//保存
		//		questionInfoService.delete(questionInfo);
		addMessage(redirectAttributes, "删除问卷信息成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionInfo/?repage";
	}
	
	/**
	 * 批量删除问卷信息
	 */
	@RequiresPermissions("question:questionInfo:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		QuestionInfo questionInfo =null;
		for(String id : idArray){
			questionInfo = questionInfoService.get(id);
			questionInfo.setStatus("0");
			questionInfoService.save(questionInfo);//保存
//			questionInfoService.delete(questionInfoService.get(id));
		}
		addMessage(redirectAttributes, "删除问卷信息成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionInfo/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("question:questionInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(QuestionInfo questionInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "问卷信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QuestionInfo> page = questionInfoService.findPage(new Page<QuestionInfo>(request, response, -1), questionInfo);
    		new ExportExcel("问卷信息", QuestionInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出问卷信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionInfo/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("question:questionInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QuestionInfo> list = ei.getDataList(QuestionInfo.class);
			for (QuestionInfo questionInfo : list){
				try{
					questionInfoService.save(questionInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条问卷信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条问卷信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入问卷信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionInfo/?repage";
    }
	
	/**
	 * 下载导入问卷信息数据模板
	 */
	@RequiresPermissions("question:questionInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "问卷信息数据导入模板.xlsx";
    		List<QuestionInfo> list = Lists.newArrayList(); 
    		new ExportExcel("问卷信息数据", QuestionInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionInfo/?repage";
    }
	
	/**
	 * 查询问卷详情
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"formdetail"})
	public String formDetail(QuestionInfo questionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("questionInfo", questionInfo);
		//问题信息
		QuestionProblem questionProblem = new QuestionProblem();
		questionProblem.setQuestionId(questionInfo.getId());
		Page<QuestionProblem> page = questionProblemService.findPageByQuestionId(new Page<QuestionProblem>(request, response), questionProblem); 
		model.addAttribute("page", page);
		return "modules/question/questionInfoFormDetail";
	}
	

}