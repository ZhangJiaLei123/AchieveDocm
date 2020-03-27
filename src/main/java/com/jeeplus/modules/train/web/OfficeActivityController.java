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
import com.jeeplus.modules.train.entity.OfficeActivity;
import com.jeeplus.modules.train.service.OfficeActivityService;

/**
 * 组织活动Controller
 * @author panjp
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/train/officeActivity")
public class OfficeActivityController extends BaseController {

	@Autowired
	private OfficeActivityService officeActivityService;
	
	@ModelAttribute
	public OfficeActivity get(@RequestParam(required=false) String id) {
		OfficeActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = officeActivityService.get(id);
		}
		if (entity == null){
			entity = new OfficeActivity();
		}
		return entity;
	}
	
	/**
	 * 组织活动列表页面
	 */
	@RequiresPermissions("train:officeActivity:list")
	@RequestMapping(value = {"list", ""})
	public String list(OfficeActivity officeActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OfficeActivity> page = officeActivityService.findPage(new Page<OfficeActivity>(request, response), officeActivity); 
		model.addAttribute("page", page);
		return "modules/train/officeActivityList";
	}

	/**
	 * 查看，增加，编辑组织活动表单页面
	 */
	@RequiresPermissions(value={"train:officeActivity:view","train:officeActivity:add","train:officeActivity:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OfficeActivity officeActivity, Model model) {
		model.addAttribute("officeActivity", officeActivity);
		return "modules/train/officeActivityForm";
	}

	/**
	 * 保存组织活动
	 */
	@RequiresPermissions(value={"train:officeActivity:add","train:officeActivity:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OfficeActivity officeActivity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, officeActivity)){
			return form(officeActivity, model);
		}
		if(!officeActivity.getIsNewRecord()){//编辑表单保存
			OfficeActivity t = officeActivityService.get(officeActivity.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(officeActivity, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			officeActivityService.save(t);//保存
		}else{//新增表单保存
			officeActivityService.save(officeActivity);//保存
		}
		addMessage(redirectAttributes, "保存组织活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/officeActivity/?repage";
	}
	
	/**
	 * 删除组织活动
	 */
	@RequiresPermissions("train:officeActivity:del")
	@RequestMapping(value = "delete")
	public String delete(OfficeActivity officeActivity, RedirectAttributes redirectAttributes) {
		officeActivityService.delete(officeActivity);
		addMessage(redirectAttributes, "删除组织活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/officeActivity/?repage";
	}
	
	/**
	 * 批量删除组织活动
	 */
	@RequiresPermissions("train:officeActivity:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			officeActivityService.delete(officeActivityService.get(id));
		}
		addMessage(redirectAttributes, "删除组织活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/officeActivity/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:officeActivity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(OfficeActivity officeActivity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "组织活动"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OfficeActivity> page = officeActivityService.findPage(new Page<OfficeActivity>(request, response, -1), officeActivity);
    		new ExportExcel("组织活动", OfficeActivity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出组织活动记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/officeActivity/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:officeActivity:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OfficeActivity> list = ei.getDataList(OfficeActivity.class);
			for (OfficeActivity officeActivity : list){
				try{
					officeActivityService.save(officeActivity);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条组织活动记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条组织活动记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入组织活动失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/officeActivity/?repage";
    }
	
	/**
	 * 下载导入组织活动数据模板
	 */
	@RequiresPermissions("train:officeActivity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "组织活动数据导入模板.xlsx";
    		List<OfficeActivity> list = Lists.newArrayList(); 
    		new ExportExcel("组织活动数据", OfficeActivity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/officeActivity/?repage";
    }
	
	
	

}