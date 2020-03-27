/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.web;

import java.util.Iterator;
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
import com.jeeplus.modules.question.entity.QuestionResult;
import com.jeeplus.modules.question.service.QuestionInfoService;
import com.jeeplus.modules.question.service.QuestionProblemService;
import com.jeeplus.modules.question.service.QuestionReleaseService;

/**
 * 问卷题目Controller
 * @author wsp
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/question/questionProblem")
public class QuestionProblemController extends BaseController {

	@Autowired
	private QuestionProblemService questionProblemService;
	@Autowired
	private QuestionInfoService questionInfoService;
	@Autowired
	private QuestionReleaseService questionReleaseService;
	
	@ModelAttribute
	public QuestionProblem get(@RequestParam(required=false) String id) {
		QuestionProblem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = questionProblemService.get(id);
		}
		if (entity == null){
			entity = new QuestionProblem();
		}
		return entity;
	}
	
	/**
	 * 问卷题目列表页面
	 */
	@RequiresPermissions("question:questionProblem:list")
	@RequestMapping(value = {"list", ""})
	public String list(QuestionProblem questionProblem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QuestionProblem> page = questionProblemService.findPage(new Page<QuestionProblem>(request, response), questionProblem); 
		model.addAttribute("page", page);
		return "modules/question/questionProblemList";
	}

	/**
	 * 查看，增加，编辑问卷题目表单页面
	 */
	@RequiresPermissions(value={"question:questionProblem:view","question:questionProblem:add","question:questionProblem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(QuestionProblem questionProblem, Model model) {
		model.addAttribute("questionProblem", questionProblem);
		return "modules/question/questionProblemForm";
	}

	/**
	 * 保存问卷题目
	 */
	@RequiresPermissions(value={"question:questionProblem:add","question:questionProblem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(QuestionProblem questionProblem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, questionProblem)){
			return form(questionProblem, model);
		}
		if(!questionProblem.getIsNewRecord()){//编辑表单保存
			QuestionProblem t = questionProblemService.get(questionProblem.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(questionProblem, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			questionProblemService.save(t);//保存
		}else{//新增表单保存
			//获取当前排序最大的编号
			String maxSort = questionProblemService.getMaxSortByQuestionId(questionProblem);
			if(StringUtils.isEmpty(maxSort)){
				maxSort = "0";
			}
			int max = Integer.valueOf(maxSort).intValue();
			questionProblem.setProSort(max+1+"");
			
			questionProblemService.save(questionProblem);//保存
		}
		addMessage(redirectAttributes, "保存问卷题目成功");
		//return "redirect:"+Global.getAdminPath()+"/question/questionProblem/?repage";
		return "redirect:"+Global.getAdminPath()+"/question/questionProblem/editproblem?questionid="+questionProblem.getQuestionId();
	}
	
	/**
	 * 删除问卷题目
	 */
	@RequiresPermissions("question:questionProblem:del")
	@RequestMapping(value = "delete")
	public String delete(QuestionProblem questionProblem, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) {
		questionProblemService.delete(questionProblem);
		//删除成功后，移动后面所有的数据
		int originalSort = Integer.valueOf(questionProblem.getProSort()).intValue();
		Iterator<QuestionProblem> itResult = questionProblemService.findList(questionProblem).iterator();
    	while (itResult.hasNext()){
    		QuestionProblem temp = itResult.next();
    		int newSort = Integer.valueOf(temp.getProSort()).intValue();
    		if(newSort>originalSort)
    		{
    			newSort--;
    			temp.setProSort(newSort+"");
    			questionProblemService.save(temp);
    		}
    	}
		
//		addMessage(redirectAttributes, "删除问卷题目成功");
//		return "redirect:"+Global.getAdminPath()+"/question/questionProblem/?repage";
    	//跳转界面
    	QuestionInfo questionInfo = null;
		if (StringUtils.isNotBlank(questionProblem.getQuestionId())){
			questionInfo = questionInfoService.get(questionProblem.getQuestionId());
		}
		model.addAttribute("questionInfo", questionInfo);
		//问题信息
		QuestionProblem questionProblemTmp = new QuestionProblem();
		questionProblemTmp.setQuestionId(questionInfo.getId());
		Page<QuestionProblem> page = questionProblemService.findPageByQuestionId(new Page<QuestionProblem>(request, response), questionProblemTmp); 
		model.addAttribute("page", page);
		return "modules/question/questionProblemEdit";
	}
	
	/**
	 * 批量删除问卷题目
	 */
	@RequiresPermissions("question:questionProblem:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			questionProblemService.delete(questionProblemService.get(id));
		}
		addMessage(redirectAttributes, "删除问卷题目成功");
		return "redirect:"+Global.getAdminPath()+"/question/questionProblem/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("question:questionProblem:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(QuestionProblem questionProblem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "问卷题目"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QuestionProblem> page = questionProblemService.findPage(new Page<QuestionProblem>(request, response, -1), questionProblem);
    		new ExportExcel("问卷题目", QuestionProblem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出问卷题目记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionProblem/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("question:questionProblem:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QuestionProblem> list = ei.getDataList(QuestionProblem.class);
			for (QuestionProblem questionProblem : list){
				try{
					questionProblemService.save(questionProblem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条问卷题目记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条问卷题目记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入问卷题目失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionProblem/?repage";
    }
	
	/**
	 * 下载导入问卷题目数据模板
	 */
	@RequiresPermissions("question:questionProblem:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "问卷题目数据导入模板.xlsx";
    		List<QuestionProblem> list = Lists.newArrayList(); 
    		new ExportExcel("问卷题目数据", QuestionProblem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/question/questionProblem/?repage";
    }
	
	/**
	 * 问卷题目列表页面与增加问题的题目
	 */
	@RequiresPermissions("question:questionProblem:list")
	@RequestMapping(value = {"editproblem"})
	public String editProblem(@RequestParam(required=false) String problemid,@RequestParam(required=false) String questionid, HttpServletRequest request, HttpServletResponse response, Model model) {
		//查询已有数据
		QuestionInfo questionInfo = null;
		if (StringUtils.isNotBlank(questionid)){
			questionInfo = questionInfoService.get(questionid);
		}
		model.addAttribute("questionInfo", questionInfo);
		//问题信息
		QuestionProblem questionProblemTmp = new QuestionProblem();
		questionProblemTmp.setQuestionId(questionInfo.getId());
		Page<QuestionProblem> page = questionProblemService.findPageByQuestionId(new Page<QuestionProblem>(request, response), questionProblemTmp); 
		model.addAttribute("page", page);
		
		//如果是编辑
		if(problemid!=null&&!problemid.equals("")){
			model.addAttribute("questionProblem", questionProblemService.get(problemid));
		}
		return "modules/question/questionProblemEdit";
	}
	
	/**
	 * 查询一个用户的答题信息
	 */
	@RequiresPermissions(value={"question:questionInfo:view","question:questionInfo:add","question:questionInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = {"formdetailbyuid"})
	public String formDetailByUserId(@RequestParam(required=false) String stime,@RequestParam(required=false) String uname,@RequestParam(required=false) String releaseid,@RequestParam(required=false) String questionid,@RequestParam(required=false) String uid, HttpServletRequest request, HttpServletResponse response, Model model) {		
		
		QuestionProblem questionProblem = new QuestionProblem();
		questionProblem.setQuestionId(questionid);
		questionProblem.setUserId(uid);
		questionProblem.setReleaseId(releaseid);
		Page<QuestionProblem> page = questionProblemService.findPageByQuestionIdAndUid(new Page<QuestionProblem>(request, response), questionProblem); 
		model.addAttribute("page", page);
		
		model.addAttribute("questionInfo", questionInfoService.get(questionid));
	
		model.addAttribute("startTime", stime);
		model.addAttribute("userName", uname);
		
		return "modules/question/questionInfoFormDetail";
	}
	
	/**
	 * 调查结果统计
	 */
	@RequiresPermissions(value={"question:questionInfo:view","question:questionInfo:add","question:questionInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = {"formdetailtotal"})
	public String formDetailTotal(@RequestParam(required=false) String questionid,@RequestParam(required=false) String releaseid, HttpServletRequest request, HttpServletResponse response, Model model) {		
		
		//得到所有的problem
		QuestionProblem questionProblem = new QuestionProblem();
		questionProblem.setQuestionId(questionid);
		questionProblem.setReleaseId(releaseid);
		Page<QuestionProblem> page = questionProblemService.formDetailTotal(new Page<QuestionProblem>(request, response), questionProblem); 
		model.addAttribute("page", page);
		
		model.addAttribute("questionInfo", questionInfoService.get(questionid));
		model.addAttribute("questionRelease",questionReleaseService.get(releaseid));
		
		return "modules/question/questionInfoFormDetailTotal";
	}
	
	/**
	 * 上移或者下移
	 */
	@RequiresPermissions(value={"question:questionProblem:view","question:questionProblem:add","question:questionProblem:edit"},logical=Logical.OR)
	@RequestMapping(value = {"upordownsort"})
	public String upOrDownSort(@RequestParam(required=false) String questionid,@RequestParam(required=false) String problemid,@RequestParam(required=false) String type, HttpServletRequest request, HttpServletResponse response, Model model) {		
		
		//上移或者下移部分
		QuestionProblem questionProblem = null,changeQuestionProblem,tmp;
		questionProblem = questionProblemService.get(problemid);
		int originalSort = Integer.valueOf(questionProblem.getProSort()).intValue(),nowSort = 0;
		if(type.equals("up")){
			nowSort = originalSort - 1;
		}else if(type.equals("down")){
			nowSort = originalSort + 1;
		}
		tmp = new QuestionProblem();
		tmp.setQuestionId(questionid);
		tmp.setProSort(nowSort+"");
		changeQuestionProblem = questionProblemService.getByQuestionIdAndSort(tmp);
		if(changeQuestionProblem==null){
			addMessage(model,"不能移动数据。");
		}else{
			questionProblem.setProSort(nowSort+"");
			questionProblemService.save(questionProblem);
			changeQuestionProblem.setProSort(originalSort+"");
			questionProblemService.save(changeQuestionProblem);
		}
		
		//返回页面
		QuestionInfo questionInfo = null;
		if (StringUtils.isNotBlank(questionid)){
			questionInfo = questionInfoService.get(questionid);
		}
		model.addAttribute("questionInfo", questionInfo);
		//问题信息
		QuestionProblem questionProblemTmp = new QuestionProblem();
		questionProblemTmp.setQuestionId(questionInfo.getId());
		Page<QuestionProblem> page = questionProblemService.findPageByQuestionId(new Page<QuestionProblem>(request, response), questionProblemTmp); 
		model.addAttribute("page", page);
		return "modules/question/questionProblemEdit";
	}
}