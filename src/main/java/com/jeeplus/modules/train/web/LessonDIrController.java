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
import com.jeeplus.modules.train.entity.LessonDIr;
import com.jeeplus.modules.train.service.LessonDIrService;

/**
 * 授课时间与章节表Controller
 * @author wsp
 * @version 2017-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/train/lessonDIr")
public class LessonDIrController extends BaseController {

	@Autowired
	private LessonDIrService lessonDIrService;
	
	@ModelAttribute
	public LessonDIr get(@RequestParam(required=false) String id) {
		LessonDIr entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lessonDIrService.get(id);
		}
		if (entity == null){
			entity = new LessonDIr();
		}
		return entity;
	}
	
	/**
	 * 授课时间与章节列表页面
	 */
	@RequiresPermissions("train:lessonDIr:list")
	@RequestMapping(value = {"list", ""})
	public String list(LessonDIr lessonDIr, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LessonDIr> page = lessonDIrService.findPage(new Page<LessonDIr>(request, response), lessonDIr); 
		model.addAttribute("page", page);
		return "modules/train/lessonDIrList";
	}

	/**
	 * 查看，增加，编辑授课时间与章节表单页面
	 */
	@RequiresPermissions(value={"train:lessonDIr:view","train:lessonDIr:add","train:lessonDIr:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LessonDIr lessonDIr, Model model) {
		model.addAttribute("lessonDIr", lessonDIr);
		return "modules/train/lessonDIrForm";
	}

	/**
	 * 保存授课时间与章节
	 */
	@RequiresPermissions(value={"train:lessonDIr:add","train:lessonDIr:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(LessonDIr lessonDIr, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, lessonDIr)){
			return form(lessonDIr, model);
		}
		if(!lessonDIr.getIsNewRecord()){//编辑表单保存
			LessonDIr t = lessonDIrService.get(lessonDIr.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(lessonDIr, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			lessonDIrService.save(t);//保存
		}else{//新增表单保存
			lessonDIrService.save(lessonDIr);//保存
		}
		addMessage(redirectAttributes, "保存授课时间与章节成功");
		return "redirect:"+Global.getAdminPath()+"/train/lessonDIr/?repage";
	}
	
	/**
	 * 删除授课时间与章节
	 */
	@RequiresPermissions("train:lessonDIr:del")
	@RequestMapping(value = "delete")
	public String delete(LessonDIr lessonDIr, RedirectAttributes redirectAttributes) {
		lessonDIrService.delete(lessonDIr);
		addMessage(redirectAttributes, "删除授课时间与章节成功");
		return "redirect:"+Global.getAdminPath()+"/train/lessonDIr/?repage";
	}
	
	/**
	 * 批量删除授课时间与章节
	 */
	@RequiresPermissions("train:lessonDIr:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			lessonDIrService.delete(lessonDIrService.get(id));
		}
		addMessage(redirectAttributes, "删除授课时间与章节成功");
		return "redirect:"+Global.getAdminPath()+"/train/lessonDIr/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:lessonDIr:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LessonDIr lessonDIr, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "授课时间与章节"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LessonDIr> page = lessonDIrService.findPage(new Page<LessonDIr>(request, response, -1), lessonDIr);
    		new ExportExcel("授课时间与章节", LessonDIr.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出授课时间与章节记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/lessonDIr/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:lessonDIr:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LessonDIr> list = ei.getDataList(LessonDIr.class);
			for (LessonDIr lessonDIr : list){
				try{
					lessonDIrService.save(lessonDIr);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条授课时间与章节记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条授课时间与章节记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入授课时间与章节失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/lessonDIr/?repage";
    }
	
	/**
	 * 下载导入授课时间与章节数据模板
	 */
	@RequiresPermissions("train:lessonDIr:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "授课时间与章节数据导入模板.xlsx";
    		List<LessonDIr> list = Lists.newArrayList(); 
    		new ExportExcel("授课时间与章节数据", LessonDIr.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/lessonDIr/?repage";
    }
	
	
	

}