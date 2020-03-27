/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tcourse.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.course.entity.CourseInfo;
import com.jeeplus.modules.course.entity.OfficeCourse;
import com.jeeplus.modules.course.entity.PostCourse;
import com.jeeplus.modules.course.entity.UserCourse;
import com.jeeplus.modules.course.entity.UserCourseReg;
import com.jeeplus.modules.course.service.CourseInfoService;
import com.jeeplus.modules.course.service.OfficeCourseService;
import com.jeeplus.modules.course.service.PostCourseService;
import com.jeeplus.modules.course.service.UserCourseRegService;
import com.jeeplus.modules.course.service.UserCourseService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;

/**
 * 教师端课程管理Controller
 * @author panjp
 * @version 2017-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/course/tcourseInfo")
public class TcourseInfoController extends BaseController {

	@Autowired
	private CourseInfoService courseInfoService;
	@Autowired
	private PostCourseService postCourseService;
	@Autowired
	private OfficeCourseService officeCourseService;
	@Autowired
	private UserCourseService userCourseSercice;
	@Autowired
	private UserCourseRegService userCourseRegService;
	
	@Autowired
	SystemService systemService;
	@ModelAttribute
	public CourseInfo get(@RequestParam(required=false) String id) {
		CourseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = courseInfoService.get(id);
		}
		if (entity == null){
			entity = new CourseInfo();
		}
		return entity;
	}
	
	/**
	 * 审核课程管理列表页面
	 */
	@RequestMapping(value = {"shList"})
	public String shList(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CourseInfo> page = courseInfoService.findPage(new Page<CourseInfo>(request, response), courseInfo); 
		model.addAttribute("page", page);
		return "modules/tcourse/courseInfoshList";
	}
	
	
	/**
	 * 课程信息详情页面
	 */
	@RequestMapping(value = {"showResourceInfo"})
	public String showResourceInfo(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		PostCourse postCourse = new PostCourse();
		postCourse.setCourseId(courseInfo.getId());
		postCourse.setType("1");
		List<PostCourse> lsBxPostCourse = postCourseService.showPpostCourseList(postCourse);
		postCourse.setType("0");
		List<PostCourse> lsXxPostCourse = postCourseService.showPpostCourseList(postCourse);
		courseInfo = courseInfoService.get(courseInfo);
		model.addAttribute("courseInfo", courseInfo);
		model.addAttribute("lsBxPostCourse", lsBxPostCourse);
		model.addAttribute("lsXxPostCourse", lsXxPostCourse);
		return "modules/tcourse/showResourceInfo";
	}
	
	/**
	 * 查看岗位课程列表
	 */
	@RequestMapping(value = {"showUserCourseList"})
	public String showUserCourseList(UserCourse userCourse, String id,String type,HttpServletRequest request, HttpServletResponse response, Model model) {
		userCourse.setCourseId(id);
		userCourse.setType(type);
		Page<UserCourse> page = userCourseSercice.showUserCourseList(new Page<UserCourse>(request, response), userCourse); 
		model.addAttribute("page", page);
		model.addAttribute("courseId", id);
		model.addAttribute("type", type);
		return "modules/tcourse/showUserCourseList";
	}
	
	
	/**
	 * 查看组织课程列表
	 */
	@RequestMapping(value = {"showOfficeCourseList"})
	public String showOfficeCourseList(OfficeCourse officeCourse, HttpServletRequest request, HttpServletResponse response, Model model,String id ,String type) {
		officeCourse.setCourseId(id);
		officeCourse.setType(type);
		Page<OfficeCourse> page = officeCourseService.showOfficeCourseLlist(new Page<OfficeCourse>(request, response), officeCourse); 
		model.addAttribute("page", page);
		return "modules/tcourse/showOfficeCourseList";
	}

	/**
	 * 课程管理列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"list"})
	public String list(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		courseInfo.setResourceStatus("0");
		Page<CourseInfo> page = courseInfoService.findPage(new Page<CourseInfo>(request, response), courseInfo); 
		model.addAttribute("page", page);
		return "modules/tcourse/courseInfoList";
	}

	
	
	/**
	 * 我的课程管理
	 */
	@RequiresPermissions("user")
	@RequestMapping("findMyCourseInfo")
	public String findMyCourseInfo(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CourseInfo> page = courseInfoService.findMyCourseInfoPage(new Page<CourseInfo>(request, response), courseInfo); 
		model.addAttribute("page", page);
		return "modules/tcourse/findMyCourseInfo";
	}
	/**
	 * 删除课程
	 * @param courseInfo
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequiresPermissions("user")
	@RequestMapping("deleteResourceInfo")
	public void deleteResourceInfo(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String str = "0";
		try {
			courseInfoService.delete(courseInfo);
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 查看，增加，编辑课程管理表单页面
	 */
	@RequestMapping(value = "form")
	public String form(CourseInfo courseInfo, Model model) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/tcourse/courseInfoForm";
	}

	/**
	 * 保存课程管理
	 */
	@RequestMapping(value = "save")
	public String save(CourseInfo courseInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, courseInfo)){
			return form(courseInfo, model);
		}
		if(!courseInfo.getIsNewRecord()){//编辑表单保存
			CourseInfo t = courseInfoService.get(courseInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(courseInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			courseInfoService.save(t);//保存
		}else{//新增表单保存
			courseInfoService.save(courseInfo);//保存
		}
		addMessage(redirectAttributes, "保存课程管理成功");
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
	}
	
	/**
	 * 删除课程管理
	 */
	@RequestMapping(value = "delete")
	public String delete(CourseInfo courseInfo, RedirectAttributes redirectAttributes) {
		courseInfoService.delete(courseInfo);
		addMessage(redirectAttributes, "删除课程管理成功");
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
	}
	
	/**
	 * 批量删除课程管理
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			courseInfoService.delete(courseInfoService.get(id));
		}
		addMessage(redirectAttributes, "删除课程管理成功");
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
	}
	
	
	
	/**
	 * 课程管理界面，查看
	 */
	@RequestMapping(value = {"managecourseview"})
	public String manageCourseView(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model,String id ,String type) {
//		officeCourse.setCourseId(id);
//		officeCourse.setType(type);
//		Page<OfficeCourse> page = officeCourseService.showOfficeCourseLlist(new Page<OfficeCourse>(request, response), officeCourse); 
//		model.addAttribute("page", page);
//		return "modules/tcourse/showOfficeCourseList";
		//课程信息
		//必学人员
		//可报名人员
		//报名审批
		//学员管理
		model.addAttribute("courseInfo", courseInfo);
		return "modules/tcourse/courseInfoManageView";
	}
	/**
	 * 教师端课程管理学员展示
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"teacherCourseUserList"})
	public String teacherCourseUserList(@RequestParam(required=false) String courseId,User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setOfficeId(courseId);
		Page<User> page = userCourseSercice.findUserList(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		model.addAttribute("courseId", courseId);
		model.addAttribute("user", user);
		return "modules/tcourse/teacherCourseUserList";
	}
	
	
	
	/**
	 * 教师端课我的课程查看
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"teacherMyCourseView"})
	public String teacherMyCourseView(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model,String id ,String type) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/tcourse/teacherMyCourseView";
	}
	
	/**
	 * 教师端学员审批
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"techerSpCourseUser"})
	public String techerSpCourseUser(String courseId,User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setOfficeId(courseId);
		Page<User> page = userCourseSercice.findBmUserList(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		model.addAttribute("courseId", courseId);
		model.addAttribute("user", user);
		return "modules/tcourse/techerSpCourseUser";
	}
	/*
	 * 教师端課程报名审批
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherSpCourseUser")
	public void teacherSpCourseUser(String ids,String rdio,String textVlue,  HttpServletRequest request, HttpServletResponse response, Model model) {
		String str = "0";
		try {
			String idArray[] =ids.split(",");
			for(String id : idArray){
				UserCourseReg userCourseReg = userCourseRegService.getById(id);
				userCourseReg.setAuditState(rdio);
				userCourseReg.setAuditAdvice(textVlue);
				userCourseRegService.save(userCourseReg);
			}
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 教师端学员管理
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"showListCourRegUser"})
	public String showListCourRegUser(@RequestParam(required=false) String courseId,User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setOfficeId(courseId);
		Page<User> page = userCourseSercice.findUserList(new Page<User>(request, response), user); 
		CourseInfo courserInfo = courseInfoService.get(courseId);
		model.addAttribute("page", page);
		model.addAttribute("courseId", courseId);
		model.addAttribute("user", user);
		model.addAttribute("courserInfo", courserInfo);
		return "modules/tcourse/showListCourRegUser";
	}
	
	/**
	 * 学习活动新增学员
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"findAddCourseUserList"})
	public String findAddStudyUserList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findAddCourseUserList(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        model.addAttribute("userActivityId", user.getUserActivityId());
		return "modules/tcourse/findAddCourseUserList";
	}
	/**
	 *教师课程学员管理添加学员
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherAddCourseUser")
	public void teacherAddCourseUser(HttpServletRequest request, HttpServletResponse response, Model model,String ids,String courserId) {
		String str = "0";
		try {
			if(null!=ids && !"".equals(ids.trim())){
				String id[] = ids.split(",");
				for (int i = 0; i < id.length; i++) {
					UserCourse userCourse = new UserCourse();
					userCourse.setId(IdGen.uuid());
					userCourse.setType("1");
					userCourse.setUserId(id[i]);
					userCourse.setCourseId(courserId);
					userCourse.setIsNewRecord(true);
					userCourseSercice.save(userCourse);
				}
			}
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *教师课程学员管理添加学员（在课程管理的界面，临时添加，需要先删除，后添加）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherAddCourseUserInManager")
	public void teacherAddCourseUserInManager(HttpServletRequest request, HttpServletResponse response, Model model,String ids,String courserId) {
		String str = "0";
		try {
			if(null!=ids && !"".equals(ids.trim())){
				String id[] = ids.split(",");
				//删除
				for (int i = 0; i < id.length; i++) {
					//删除UserCourse
					UserCourse userCourse = new UserCourse();
					userCourse.setCourseId(courserId);
					userCourse.setUserId(id[i]);
					userCourseSercice.deleteByUserIdAndCourseId(userCourse);
					//删除UserCourseReg
					UserCourseReg userCourseReg = new UserCourseReg();
					User createBy = new User();
					createBy.setId(id[i]);
					userCourseReg.setCreateBy(createBy);
					userCourseReg.setCourseId(courserId);
					userCourseRegService.deleteByUserIdAndCourseId(userCourseReg);
				}
				//添加
				for (int i = 0; i < id.length; i++) {
					UserCourse userCourse = new UserCourse();
					userCourse.setId(IdGen.uuid());
					userCourse.setType("1");
					userCourse.setUserId(id[i]);
					userCourse.setCourseId(courserId);
					userCourse.setIsNewRecord(true);
					userCourseSercice.save(userCourse);
				}
			}
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *教师删除学员
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherDelCourseUser")
	public void teacherDelStuActivityUser(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		String str = "0";
		try {
			userCourseSercice.deleteById(id);
			userCourseRegService.deleteById(id);
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}