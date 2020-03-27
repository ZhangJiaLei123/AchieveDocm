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
import com.jeeplus.modules.course.entity.OfficeCourse;
import com.jeeplus.modules.course.service.OfficeCourseService;

/**
 * 组织课程关系Controller
 * @author panjp
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/course/officeCourse")
public class OfficeCourseController extends BaseController {

	@Autowired
	private OfficeCourseService officeCourseService;
	
	@ModelAttribute
	public OfficeCourse get(@RequestParam(required=false) String id) {
		OfficeCourse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = officeCourseService.get(id);
		}
		if (entity == null){
			entity = new OfficeCourse();
		}
		return entity;
	}
	
	/**
	 * 组织课程关系列表页面
	 */
	@RequiresPermissions("course:officeCourse:list")
	@RequestMapping(value = {"list", ""})
	public String list(OfficeCourse officeCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OfficeCourse> page = officeCourseService.findPage(new Page<OfficeCourse>(request, response), officeCourse); 
		model.addAttribute("page", page);
		return "modules/course/officeCourseList";
	}


	
	/**
	 * 查看，增加，编辑组织课程关系表单页面
	 */
	@RequiresPermissions(value={"course:officeCourse:view","course:officeCourse:add","course:officeCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OfficeCourse officeCourse, Model model) {
		model.addAttribute("officeCourse", officeCourse);
		return "modules/course/officeCourseForm";
	}

	/**
	 * 保存组织课程关系
	 */
	@RequiresPermissions(value={"course:officeCourse:add","course:officeCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OfficeCourse officeCourse, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, officeCourse)){
			return form(officeCourse, model);
		}
		if(!officeCourse.getIsNewRecord()){//编辑表单保存
			OfficeCourse t = officeCourseService.get(officeCourse.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(officeCourse, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			officeCourseService.save(t);//保存
		}else{//新增表单保存
			officeCourseService.save(officeCourse);//保存
		}
		addMessage(redirectAttributes, "保存组织课程关系成功");
		return "redirect:"+Global.getAdminPath()+"/course/officeCourse/?repage";
	}
	
	/**
	 * 删除组织课程关系
	 */
	@RequiresPermissions("course:officeCourse:del")
	@RequestMapping(value = "delete")
	public String delete(OfficeCourse officeCourse, RedirectAttributes redirectAttributes) {
		officeCourseService.delete(officeCourse);
		addMessage(redirectAttributes, "删除组织课程关系成功");
		return "redirect:"+Global.getAdminPath()+"/course/officeCourse/?repage";
	}
	
	/**
	 * 批量删除组织课程关系
	 */
	@RequiresPermissions("course:officeCourse:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			officeCourseService.delete(officeCourseService.get(id));
		}
		addMessage(redirectAttributes, "删除组织课程关系成功");
		return "redirect:"+Global.getAdminPath()+"/course/officeCourse/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("course:officeCourse:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(OfficeCourse officeCourse, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "组织课程关系"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OfficeCourse> page = officeCourseService.findPage(new Page<OfficeCourse>(request, response, -1), officeCourse);
    		new ExportExcel("组织课程关系", OfficeCourse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出组织课程关系记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/officeCourse/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("course:officeCourse:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OfficeCourse> list = ei.getDataList(OfficeCourse.class);
			for (OfficeCourse officeCourse : list){
				try{
					officeCourseService.save(officeCourse);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条组织课程关系记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条组织课程关系记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入组织课程关系失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/officeCourse/?repage";
    }
	
	/**
	 * 下载导入组织课程关系数据模板
	 */
	@RequiresPermissions("course:officeCourse:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "组织课程关系数据导入模板.xlsx";
    		List<OfficeCourse> list = Lists.newArrayList(); 
    		new ExportExcel("组织课程关系数据", OfficeCourse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/officeCourse/?repage";
    }
	
	
	

}