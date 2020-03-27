/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.jeeplus.modules.course.entity.UserCourse;
import com.jeeplus.modules.course.service.PostCourseService;
import com.jeeplus.modules.course.service.UserCourseService;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;

import scala.collection.mutable.HashMap;

/**
 * 学员课程关系表Controller
 * @author panjp
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/course/userCourse")
public class UserCourseController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private UserCourseService userCourseService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private PostCourseService postCourseService;
	
	@ModelAttribute
	public UserCourse get(@RequestParam(required=false) String id) {
		UserCourse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userCourseService.get(id);
		}
		if (entity == null){
			entity = new UserCourse();
		}
		return entity;
	}
	
	/**
	 * 学员课程关系表列表页面
	 */
	@RequiresPermissions("course:userCourse:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserCourse userCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserCourse> page = userCourseService.findPage(new Page<UserCourse>(request, response), userCourse); 
		model.addAttribute("page", page);
		return "modules/course/userCourseList";
	}

	/**
	 * 学员课程关系表列表页面
	 */
	@RequiresPermissions("course:userCourse:list")
	@RequestMapping(value = {"list"})
	public String showUserCourseList(UserCourse userCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserCourse> page = userCourseService.showUserCourseList(new Page<UserCourse>(request, response), userCourse); 
		model.addAttribute("page", page);
		return "modules/course/showUserCourseList";
	}
	
	/**
	 * 查看，增加，编辑学员课程关系表表单页面
	 */
	@RequiresPermissions(value={"course:userCourse:view","course:userCourse:add","course:userCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserCourse userCourse, Model model) {
		model.addAttribute("userCourse", userCourse);
		return "modules/course/userCourseForm";
	}

	/**
	 * 保存学员课程关系表
	 */
	@RequiresPermissions(value={"course:userCourse:add","course:userCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserCourse userCourse, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userCourse)){
			return form(userCourse, model);
		}
		if(!userCourse.getIsNewRecord()){//编辑表单保存
			UserCourse t = userCourseService.get(userCourse.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userCourse, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userCourseService.save(t);//保存
		}else{//新增表单保存
			userCourseService.save(userCourse);//保存
		}
		addMessage(redirectAttributes, "保存学员课程关系表成功");
		return "redirect:"+Global.getAdminPath()+"/course/userCourse/?repage";
	}
	
	/**
	 * 删除学员课程关系表
	 */
	@RequiresPermissions("course:userCourse:del")
	@RequestMapping(value = "delete")
	public String delete(UserCourse userCourse, RedirectAttributes redirectAttributes) {
		userCourseService.delete(userCourse);
		addMessage(redirectAttributes, "删除学员课程关系表成功");
		return "redirect:"+Global.getAdminPath()+"/course/userCourse/?repage";
	}
	
	/**
	 * 批量删除学员课程关系表
	 */
	@RequiresPermissions("course:userCourse:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userCourseService.delete(userCourseService.get(id));
		}
		addMessage(redirectAttributes, "删除学员课程关系表成功");
		return "redirect:"+Global.getAdminPath()+"/course/userCourse/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("course:userCourse:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserCourse userCourse, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学员课程关系表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserCourse> page = userCourseService.findPage(new Page<UserCourse>(request, response, -1), userCourse);
    		new ExportExcel("学员课程关系表", UserCourse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学员课程关系表记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/userCourse/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("course:userCourse:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserCourse> list = ei.getDataList(UserCourse.class);
			for (UserCourse userCourse : list){
				try{
					userCourseService.save(userCourse);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条学员课程关系表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条学员课程关系表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入学员课程关系表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/userCourse/?repage";
    }
	
	/**
	 * 下载导入学员课程关系表数据模板
	 */
	@RequiresPermissions("course:userCourse:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学员课程关系表数据导入模板.xlsx";
    		List<UserCourse> list = Lists.newArrayList(); 
    		new ExportExcel("学员课程关系表数据", UserCourse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/userCourse/?repage";
    }
	
	/**
	 * 查询必学学员和可以报名的学院
	 */
//	@RequiresPermissions("course:userCourse:list")
	@RequestMapping(value = {"liststudentinfo"})
	public String list(UserCourse userCourse,String courseid,String type, HttpServletRequest request, HttpServletResponse response, Model model) {
		userCourse.setCourseId(courseid);
		userCourse.setType(type);
		Page<UserCourse> page = userCourseService.findOfficeListMap(new Page<UserCourse>(request, response), userCourse); 
		//根据课程id和类型id，查找所有的人员
		int officeTypeCount = userCourseService.findUserOfficeTypeCount(courseid, type);
		int userCount = userCourseService.findUserCount(courseid, type);
		PostCourse postCourse = new PostCourse();
		postCourse.setCourseId(courseid);
		postCourse.setType("1");
		List<PostCourse> postList = postCourseService.showPpostCourseList(postCourse);
		model.addAttribute("officeTypeCount",officeTypeCount);
		model.addAttribute("userCount",userCount);
		model.addAttribute("postList",postList);
		model.addAttribute("page", page);
		return "modules/course/courseInfoManageViewStudent";
	}
	
	/**
	 * 学员课程关系表列表页面
	 */
	@RequestMapping(value = {"showlist"})
	public String showUserCourseListNew(@RequestParam(required=false) String courseid, HttpServletRequest request, HttpServletResponse response, Model model) {
		UserCourse userCourse = new UserCourse();
		userCourse.setCourseId(courseid);
		Page<UserCourse> page = userCourseService.findUserCourseInfoList(new Page<UserCourse>(request, response), userCourse); 
		model.addAttribute("page", page);
		return "modules/course/courseInfoManageViewUserCourseList";
	}
	/**
	 * 根据岗位查询岗位级别
	 */
	@RequiresPermissions("user")
    @RequestMapping(value = "findUserByOfficeId")
    public void findUserByOfficeId(HttpServletResponse response, RedirectAttributes redirectAttributes,String officeId){

		List<Map> lsMap = userCourseService.findUserByOfficeId(officeId);
		String str = "0";
		if(null!=lsMap && !lsMap.isEmpty()){
			str = JSONArray.toJSONString(lsMap);
		}
		try {
			response.getWriter().print(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}