/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.web;

import java.io.IOException;
import java.util.ArrayList;
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
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.sys.entity.ApprovalRecord;
import com.jeeplus.modules.sys.service.ApprovalRecordService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.train.entity.ProgramActivity;
import com.jeeplus.modules.train.entity.TrainProgram;
import com.jeeplus.modules.train.service.ProgramActivityService;
import com.jeeplus.modules.train.service.TrainProgramService;

/**
 * 培训计划Controller
 * @author panjp
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/train/trainProgram")
public class TrainProgramController extends BaseController {

	@Autowired
	private TrainProgramService trainProgramService;
	
	@Autowired
	private ProgramActivityService programActivityService;
	
	
	@Autowired
	private ApprovalRecordService approvalRecordService;
	@ModelAttribute
	public TrainProgram get(@RequestParam(required=false) String id) {
		TrainProgram entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = trainProgramService.get(id);
		}
		if (entity == null){
			entity = new TrainProgram();
		}
		return entity;
	}
	
	/**
	 * 培训计划列表页面
	 */
	@RequiresPermissions("train:trainProgram:list")
	@RequestMapping(value = {"list", ""})
	public String list(TrainProgram trainProgram, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TrainProgram> page = trainProgramService.findPage(new Page<TrainProgram>(request, response), trainProgram); 
		model.addAttribute("page", page);
		return "modules/train/trainProgramList";
	}

	/**
	 * 查看，增加，编辑培训计划表单页面
	 */
	@RequiresPermissions(value={"train:trainProgram:view","train:trainProgram:add","train:trainProgram:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TrainProgram trainProgram, Model model) {
		String id = trainProgram.getId();
		List<ProgramActivity> proGramList = new ArrayList<ProgramActivity>();
		if(null !=id && !"".equals(id)){
			ProgramActivity obj = new ProgramActivity();
			obj.setTrainProgramId(id);
			 proGramList = programActivityService.findList(obj);
			 trainProgram.setProGramList(proGramList);
		}
		model.addAttribute("trainProgram", trainProgram);
		return "modules/train/trainProgramForm";
	}
	/**
	 * 查看，增加，编辑培训计划表单页面
	 */
	@RequiresPermissions(value={"train:trainProgram:view","train:trainProgram:add","train:trainProgram:edit"},logical=Logical.OR)
	@RequestMapping(value = "viewForm")
	public String viewForm(TrainProgram trainProgram, Model model) {
		String id = trainProgram.getId();
		List<ProgramActivity> proGramList = new ArrayList<ProgramActivity>();
		String roleCode = UserUtils.getUser().getRole().getEnname();
		if(null !=id && !"".equals(id)){
			if(UserUtils.USER_JG_ROLE_ENNAME.equals(roleCode)){
				ProgramActivity obj = new ProgramActivity();
				obj.setTrainProgramId(id);
				obj.setUserFllow(UserUtils.getUser().getId());
				proGramList = programActivityService.findGzList(obj);
				trainProgram.setProGramList(proGramList);
			}else{
				ProgramActivity obj = new ProgramActivity();
				obj.setTrainProgramId(id);
				proGramList = programActivityService.findList(obj);
				trainProgram.setProGramList(proGramList);
			}
			
		}
		model.addAttribute("trainProgram", trainProgram);
		model.addAttribute("roleCode", roleCode);
		return "modules/train/trainProgramViewForm";
	}
	/**
	 * 保存培训计划
	 */
	@RequiresPermissions(value={"train:trainProgram:add","train:trainProgram:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TrainProgram trainProgram, Model model, RedirectAttributes redirectAttributes,String activeStr) throws Exception{
		
		if (!beanValidator(model, trainProgram)){
			return form(trainProgram, model);
		}
		if(!trainProgram.getIsNewRecord()){//编辑表单保存
			TrainProgram t = trainProgramService.get(trainProgram.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(trainProgram, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			trainProgramService.save(t);//保存
		}else{//新增表单保存
			String trainProgramId =  IdGen.uuid();
			trainProgram.setId(trainProgramId);
			trainProgram.setIsNewRecord(true);
			trainProgram.setIsCreateAdmin("1");
			trainProgramService.save(trainProgram);//保存
			//判断当前用户是否为管理员如果是管理员则自动审核
			String userRoleEnName = UserUtils.getUser().getRole().getEnname();
			if(UserUtils.USER_SYSTEM_ROLE_ENNAME.equals(userRoleEnName)){//自动提交审核
				ApprovalRecord ap = new ApprovalRecord();
				ap.setIsNewRecord(true);
				ap.setId(IdGen.uuid());
				ap.setResourceId(trainProgramId);
				ap.setStatus("3");
				ap.setOpinion("管理员提交自动审核通过。");
				approvalRecordService.save(ap);
			}
		}
		addMessage(redirectAttributes, "保存培训计划成功");
		return "redirect:"+Global.getAdminPath()+"/train/trainProgram/?repage";
	}
	/**
	 * 发布活动
	 * @param resourceId
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(value={"train:trainProgram:add","train:trainProgram:edit"},logical=Logical.OR)
	@RequestMapping(value = "fbTrainProgram")
	public String fbTrainProgram(String resourceId, Model model, RedirectAttributes redirectAttributes) throws Exception{
		ApprovalRecord appr = new ApprovalRecord();
		appr.setResourceId(resourceId);
		 appr = approvalRecordService.findByResourceId(appr);
		if(appr.getId() !=null ){
			appr.setStatus("0");
			approvalRecordService.save(appr);
			addMessage(redirectAttributes, "发布计划成功!");
		}else{
			addMessage(redirectAttributes, "发布计划失败!");
		}
		return "redirect:"+Global.getAdminPath()+"/train/trainProgram/?repage";
	}
	/**
	 * 删除培训计划
	 */
	@RequiresPermissions("train:trainProgram:del")
	@RequestMapping(value = "delete")
	public String delete(TrainProgram trainProgram, RedirectAttributes redirectAttributes) {
		trainProgramService.delete(trainProgram);
		addMessage(redirectAttributes, "删除培训计划成功");
		return "redirect:"+Global.getAdminPath()+"/train/trainProgram/?repage";
	}
	
	/**
	 * 批量删除培训计划
	 */
	@RequiresPermissions("train:trainProgram:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			trainProgramService.delete(trainProgramService.get(id));
		}
		addMessage(redirectAttributes, "删除培训计划成功");
		return "redirect:"+Global.getAdminPath()+"/train/trainProgram/?repage";
	}
	/**
	 * 发布计划
	 *//*
	@RequiresPermissions("train:trainProgram:edit")
	@RequestMapping(value = "fbTrainProgram")
	public void fbTrainProgram(String ids, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		String idArray[] =ids.split(",");
		String isSuccess  = "0";
		for(String id : idArray){
			trainProgramService.updateRelease(id);
		}
		isSuccess = "1";
		try {
			response.getWriter().print(isSuccess);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:trainProgram:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(TrainProgram trainProgram, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "培训计划"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TrainProgram> page = trainProgramService.findPage(new Page<TrainProgram>(request, response, -1), trainProgram);
    		new ExportExcel("培训计划", TrainProgram.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出培训计划记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/trainProgram/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:trainProgram:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TrainProgram> list = ei.getDataList(TrainProgram.class);
			for (TrainProgram trainProgram : list){
				try{
					trainProgramService.save(trainProgram);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条培训计划记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条培训计划记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入培训计划失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/trainProgram/?repage";
    }
	
	/**
	 * 下载导入培训计划数据模板
	 */
	@RequiresPermissions("train:trainProgram:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "培训计划数据导入模板.xlsx";
    		List<TrainProgram> list = Lists.newArrayList(); 
    		new ExportExcel("培训计划数据", TrainProgram.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/trainProgram/?repage";
    }
	
	
	

}