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
import com.jeeplus.modules.course.entity.PostCourse;
import com.jeeplus.modules.course.service.PostCourseService;

/**
 * 岗位课程Controller
 * @author panjp
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/course/postCourse")
public class PostCourseController extends BaseController {

	@Autowired
	private PostCourseService postCourseService;
	
	@ModelAttribute
	public PostCourse get(@RequestParam(required=false) String id) {
		PostCourse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postCourseService.get(id);
		}
		if (entity == null){
			entity = new PostCourse();
		}
		return entity;
	}
	
	/**
	 * 岗位课程列表页面
	 */
	@RequiresPermissions("course:postCourse:list")
	@RequestMapping(value = {"list", ""})
	public String list(PostCourse postCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PostCourse> page = postCourseService.findPage(new Page<PostCourse>(request, response), postCourse); 
		model.addAttribute("page", page);
		return "modules/course/postCourseList";
	}
	
	
	
	/**
	 * 查看，增加，编辑岗位课程表单页面
	 */
	@RequiresPermissions(value={"course:postCourse:view","course:postCourse:add","course:postCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PostCourse postCourse, Model model) {
		model.addAttribute("postCourse", postCourse);
		return "modules/course/postCourseForm";
	}

	/**
	 * 保存岗位课程
	 */
	@RequiresPermissions(value={"course:postCourse:add","course:postCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PostCourse postCourse, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, postCourse)){
			return form(postCourse, model);
		}
		if(!postCourse.getIsNewRecord()){//编辑表单保存
			PostCourse t = postCourseService.get(postCourse.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(postCourse, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			postCourseService.save(t);//保存
		}else{//新增表单保存
			postCourseService.save(postCourse);//保存
		}
		addMessage(redirectAttributes, "保存岗位课程成功");
		return "redirect:"+Global.getAdminPath()+"/course/postCourse/?repage";
	}
	
	/**
	 * 删除岗位课程
	 */
	@RequiresPermissions("course:postCourse:del")
	@RequestMapping(value = "delete")
	public String delete(PostCourse postCourse, RedirectAttributes redirectAttributes) {
		postCourseService.delete(postCourse);
		addMessage(redirectAttributes, "删除岗位课程成功");
		return "redirect:"+Global.getAdminPath()+"/course/postCourse/?repage";
	}
	
	/**
	 * 批量删除岗位课程
	 */
	@RequiresPermissions("course:postCourse:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			postCourseService.delete(postCourseService.get(id));
		}
		addMessage(redirectAttributes, "删除岗位课程成功");
		return "redirect:"+Global.getAdminPath()+"/course/postCourse/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("course:postCourse:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PostCourse postCourse, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位课程"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PostCourse> page = postCourseService.findPage(new Page<PostCourse>(request, response, -1), postCourse);
    		new ExportExcel("岗位课程", PostCourse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出岗位课程记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/postCourse/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("course:postCourse:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PostCourse> list = ei.getDataList(PostCourse.class);
			for (PostCourse postCourse : list){
				try{
					postCourseService.save(postCourse);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条岗位课程记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条岗位课程记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入岗位课程失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/postCourse/?repage";
    }
	
	/**
	 * 下载导入岗位课程数据模板
	 */
	@RequiresPermissions("course:postCourse:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位课程数据导入模板.xlsx";
    		List<PostCourse> list = Lists.newArrayList(); 
    		new ExportExcel("岗位课程数据", PostCourse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/postCourse/?repage";
    }
	
	
	

}