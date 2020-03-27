/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.web;

import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.cst.HeadCst;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.course.entity.CourseInfo;
import com.jeeplus.modules.course.entity.CourseInfoCondition;
import com.jeeplus.modules.course.entity.OfficeCourse;
import com.jeeplus.modules.course.entity.PostCourse;
import com.jeeplus.modules.course.entity.UserCourse;
import com.jeeplus.modules.course.entity.UserCourseReg;
import com.jeeplus.modules.course.service.CourseInfoConditionService;
import com.jeeplus.modules.course.service.CourseInfoService;
import com.jeeplus.modules.course.service.OfficeCourseService;
import com.jeeplus.modules.course.service.PostCourseService;
import com.jeeplus.modules.course.service.UserCourseRegService;
import com.jeeplus.modules.course.service.UserCourseService;
import com.jeeplus.modules.sys.entity.ApprovalRecord;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.ApprovalRecordService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 课程管理Controller
 * @author panjp
 * @version 2017-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/course/courseInfo")
public class CourseInfoController extends BaseController {

	@Autowired
	private CourseInfoService courseInfoService;
	@Autowired
	private PostCourseService postCourseService;
	@Autowired
	private OfficeCourseService officeCourseService;
	@Autowired
	private UserCourseService userCourseSercice;
	
	@Autowired
	private CourseInfoConditionService courseInfoConditionService;

	@Autowired
	private SystemService systemService;
	@Autowired
	private UserCourseService userCourseService;
	@Autowired
	private ApprovalRecordService approvalRecordService;
	
	@Autowired
	private UserCourseRegService userCourseRegService;
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
	@RequiresPermissions("course:courseInfo:list")
	@RequestMapping(value = {"shList"})
	public String shList(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CourseInfo> page = courseInfoService.findPage(new Page<CourseInfo>(request, response), courseInfo); 
		model.addAttribute("page", page);
		return "modules/course/courseInfoshList";
	}
	
	
	/**
	 * 课程信息详情页面
	 */
	@RequiresPermissions("course:courseInfo:list")
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
		return "modules/course/showResourceInfo";
	}
	
	/**
	 * 查看不選學員
	 */
	@RequiresPermissions("course:courseInfo:list")
	@RequestMapping(value = {"showUserCourseList"})
	public String showUserCourseList(UserCourse userCourse, String id,String type,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserCourse> page = userCourseSercice.showUserCourseList(new Page<UserCourse>(request, response), userCourse);
		model.addAttribute("page", page);
		model.addAttribute("userCourse", userCourse);
		return "modules/course/showUserCourseList";
	}
	
	
	/**
	 * 查看组织课程列表
	 */
	@RequiresPermissions("course:courseInfo:list")
	@RequestMapping(value = {"showOfficeCourseList"})
	public String showOfficeCourseList(OfficeCourse officeCourse, HttpServletRequest request, HttpServletResponse response, Model model,String id ,String type) {
		officeCourse.setCourseId(id);
		officeCourse.setType(type);
		Page<OfficeCourse> page = officeCourseService.showOfficeCourseLlist(new Page<OfficeCourse>(request, response), officeCourse); 
		model.addAttribute("page", page);
		return "modules/course/showOfficeCourseList";
	}

	/**
	 * 审核课程查看必选选学组织
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"showshCourseOfficeCourseList"})
	public String showOfficeCourseList(Office office, HttpServletRequest request, HttpServletResponse response, Model model,String id ,String type) {
		Page<Office> page = officeCourseService.showshCourseOfficeCourseList(new Page<Office>(request, response), office); 
		model.addAttribute("page", page);
		return "modules/course/showshCourseOfficeCourseList";
	}

	/**
	 * 课程管理列表页面
	 */
	@RequiresPermissions("course:courseInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CourseInfo> page = courseInfoService.findPage(new Page<CourseInfo>(request, response), courseInfo); 
		model.addAttribute("page", page);
		model.addAttribute("roleCode", UserUtils.getUser().getRole().getEnname());
		return "modules/course/courseInfoList";
	}

	/**
	 * 复制课程列表
	 */
	@RequiresPermissions("user")
	@RequestMapping("copyCourseInfoList")
	public String copyCourseInfoList(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		courseInfo.setResourceStatus("0");
		Page<CourseInfo> page = courseInfoService.courseInfoIsShSuccess(new Page<CourseInfo>(request, response), courseInfo); 
		model.addAttribute("page", page);
		return "modules/course/copyCourseInfoList";
	}
	
	
	
	
	/**
	 * 查看，增加，编辑课程管理表单页面
	 */
	@RequiresPermissions(value={"course:courseInfo:view","course:courseInfo:add","course:courseInfo:edit","user"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CourseInfo courseInfo, Model model) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/course/courseInfoForm";
	}

	/**
	 * 查看，增加，编辑课程管理表单页面讲师
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formTeacher")
	public String formTeacher(CourseInfo courseInfo, Model model) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/course/formTeacher";
	}
	
	@RequestMapping("findCourseInfoById")
	public void findCourseInfoById(String id, HttpServletRequest request, HttpServletResponse response) {
		CourseInfo courseInfo = courseInfoService.get(id);
		String jsonObject = JSONObject.toJSONString(courseInfo);
		try {
			response.getWriter().print(jsonObject);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 保存课程管理
	 */
	@RequiresPermissions(value={"course:courseInfo:add","course:courseInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CourseInfo courseInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, courseInfo)){
			return form(courseInfo, model);
		}
		courseInfoService.save(courseInfo);//保存
		addMessage(redirectAttributes, "保存课程管理成功");
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
	}
	/**
	 * 讲师保存课程管理
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherSave")
	public String teacherSave(CourseInfo courseInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		courseInfoService.save(courseInfo);//保存
		addMessage(redirectAttributes, "保存课程管理成功");
		return "redirect:"+Global.getAdminPath()+"/course/tcourseInfo/list";
		
	}
	/**
	 * 删除课程管理
	 */
	@RequiresPermissions("course:courseInfo:del")
	@RequestMapping(value = "delete")
	public String delete(CourseInfo courseInfo, RedirectAttributes redirectAttributes) {
		courseInfoService.delete(courseInfo);
		addMessage(redirectAttributes, "删除课程管理成功");
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
	}
	/**
	 * 删除课程管理
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherDelCourseInfo")
	public String teacherDelCourseInfo(CourseInfo courseInfo, RedirectAttributes redirectAttributes) {
		courseInfoService.delete(courseInfo);
		addMessage(redirectAttributes, "删除课程成功");
		return "redirect:"+Global.getAdminPath()+"/course/tcourseInfo/findMyCourseInfo/?repage";
	}
	/**
	 * 发布课程管理
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "fbCourseInfoById")
	public String fbCourseInfoById(CourseInfo courseInfo, RedirectAttributes redirectAttributes) {
		courseInfoService.fbCourseInfoById(courseInfo.getId());
		addMessage(redirectAttributes, "发布课程成功");
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
	}
	
	/**
	 * 批量删除课程管理
	 */
	@RequiresPermissions("course:courseInfo:del")
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
	 * 导出excel文件
	 */
	@RequiresPermissions("course:courseInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "课程管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CourseInfo> page = courseInfoService.findPage(new Page<CourseInfo>(request, response, -1), courseInfo);
    		new ExportExcel("课程管理", CourseInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出课程管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("course:courseInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String edit = request.getParameter("edit");
		String type = request.getParameter("type");//导入类型
		try {
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if(HeadCst.headValid(ei)){
				request.setAttribute("message", "您的导入模板不正确，请到账号管理功能模块下载模板数据");
			}else{
				String id = request.getParameter("id");//学习活动的id
				//删除学员活动
				//userActivityService.deleteUserActivityByActivId(id,type);
				if(null != edit){//修改的时候，删除之前的数据
					Map map = new HashMap();
					map.put("courseId", id);
					map.put("type",type);
					userCourseService.deleteUserCourseByCourID(map);
				}
				int successNum = 0;
				int failureNum = 0;
				StringBuilder failureMsg = new StringBuilder();
				
				List<User> list = ei.getDataList(User.class);
				if(null != list && list.size()>1){
					for(int i=0;i<list.size();i++){
						try{
							String loginName = list.get(i).getLoginName();
							User userExits = systemService.getUserByLoginName(loginName);//如果存在
							if(null != userExits && !UserUtils.USER_TEACHER_ENNAME.equals(userExits.getRole().getEnname())){
								int result = courseInfoService.saveForImport(type,userExits.getId(),id,edit);
								if(result==1){
									successNum++;
								}else{
									failureNum++;
								}
							}else{
								System.out.println(loginName + "不存在");
								failureNum++;
							}
						}catch(ConstraintViolationException ex){
							failureNum++;
						}catch (Exception ex) {
							failureNum++;
						}
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条学员记录。");
				}
				request.setAttribute("message", "已成功导入 "+successNum+" 条学员记录"+failureMsg);
			}
		} catch (Exception e) {
			request.setAttribute("message", "导入学员信息失败！失败信息："+e.getMessage());
		}
		if("1".equals(type)){
			return "modules/course/courseInfoFormImport";//进入选学导入页面
		}else{
			return "modules/course/courseInfoFormImportXx";//进入选学导入页面
		}
    }
	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("user")
    @RequestMapping(value = "teacherImport", method=RequestMethod.POST)
    public String teacherImport(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String edit = request.getParameter("edit");
		String type = request.getParameter("type");//导入类型
		try {
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if(HeadCst.headValid(ei)){
				request.setAttribute("message", "您的导入模板不正确，请到账号管理功能模块下载模板数据");
			}else{
				String id = request.getParameter("id");//学习活动的id
				//删除学员活动
				//userActivityService.deleteUserActivityByActivId(id,type);
				if(null != edit){//修改的时候，删除之前的数据
					Map map = new HashMap();
					map.put("courseId", id);
					map.put("type",type);
					userCourseService.deleteUserCourseByCourID(map);
				}
				int successNum = 0;
				int failureNum = 0;
				StringBuilder failureMsg = new StringBuilder();
				
				List<User> list = ei.getDataList(User.class);
				if(null != list && list.size()>1){
					for(int i=0;i<list.size();i++){
						try{
							String loginName = list.get(i).getLoginName();
							User userExits = systemService.getUserByLoginName(loginName);//如果存在
							if(null != userExits && !UserUtils.USER_TEACHER_ENNAME.equals(userExits.getRole().getEnname())){
								int result = courseInfoService.saveForImport(type,userExits.getId(),id,edit);
								if(result==1){
									successNum++;
								}else{
									failureNum++;
								}
							}else{
								System.out.println(loginName + "不存在");
								failureNum++;
							}
						}catch(ConstraintViolationException ex){
							failureNum++;
						}catch (Exception ex) {
							failureNum++;
						}
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条学员记录。");
				}
				request.setAttribute("message", "已成功导入 "+successNum+" 条学员记录"+failureMsg);
			}
		} catch (Exception e) {
//			addMessage(redirectAttributes, "导入学员信息失败！失败信息："+e.getMessage());
			request.setAttribute("message", "导入学员信息失败！失败信息："+e.getMessage());
		}
		if("1".equals(type)){
			return "modules/course/teacherCourseInfoFormImport";//进入选学导入页面
		}else{
			return "modules/course/teacherCourseInfoFormImportXx";//进入选学导入页面
		}
    }
	
	/**
	 * 下载导入课程管理数据模板
	 */
	@RequiresPermissions("course:courseInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "课程管理数据导入模板.xlsx";
    		List<CourseInfo> list = Lists.newArrayList(); 
    		new ExportExcel("课程管理数据", CourseInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
    }
	
	/**
	 * 课程管理界面，查看
	 */
	@RequiresPermissions("course:courseInfo:view")
	@RequestMapping(value = {"managecourseview"})
	public String manageCourseView(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model,String id ,String type) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/course/courseInfoManageView";
	}
	/**
	 * 课程管理界面，机构管理员查看
	 */
	@RequiresPermissions("course:courseInfo:view")
	@RequestMapping(value = {"managecourseviewForJgSystem"})
	public String manageCourseViewForJgSystem(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model,String id ,String type) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/course/courseInfoManageViewForJgSystem";
	}
	
	/**
	 * 课程管理界面，查看
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"teacherManageCourseView"})
	public String teacherManageCourseView(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model,String id ,String type) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/tcourse/teacherManageCourseView";
	}
	/**********************************************课程修改拆分，重构 2017-05-10 ygq*************************************************/
	/**
	 * 查看，增加，编辑课程管理表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formBase")
	public String formBase(CourseInfo courseInfo, Model model) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/course/courseInfoFormBase";
	}
	/**
	 * 查看，增加，编辑课程管理表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormBase")
	public String teacherFormBase(CourseInfo courseInfo, Model model) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/course/courseInfoTeacherFormBase";
	}
	/**
	 * 查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formImport")
	public String formImport(CourseInfo courseInfo, Model model) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/course/courseInfoFormImport";
	}
	
	/**
	 * 教师端查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormImport")
	public String teacherFormImport(CourseInfo courseInfo, Model model) {
		model.addAttribute("courseInfo", courseInfo);
		return "modules/course/teacherCourseInfoFormImport";
	}
	/**
	 * 查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formImportXx")
	public String formImportXx(CourseInfo courseInfo, Model model) {
		model.addAttribute("courseInfo", courseInfo);
		if("1".equals(courseInfo.getIsImportXxUser())){
			return "modules/course/courseInfoFormImportXx";//进入选学导入页面
		}else{
			return "modules/course/courseInfoFormXx";//进入选学页面
		}
	}
	
	/**
	 * 教师端查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormImportXx")
	public String teacherFormImportXx(CourseInfo courseInfo, Model model) {
		model.addAttribute("courseInfo", courseInfo);
		if("1".equals(courseInfo.getIsImportXxUser())){
			return "modules/course/teacherCourseInfoFormImportXx";//进入选学导入页面
		}else{
			return "modules/course/teacherCourseInfoFormXx";//进入选学页面
		}
	}
	/**
	 * 查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formBxXx")
	public String formBxXx(CourseInfo courseInfo,String type,String edit,HttpServletRequest request, HttpServletResponse response, Model model) {
		String courseId = courseInfo.getId();
		//获取查询条件
		Map<String,String> mapPa = new HashMap<String,String>();
		mapPa.put("courseId",courseId );
		mapPa.put("type", type);
		CourseInfoCondition condition = courseInfoConditionService.findActivityConditionByActivityId(mapPa);
		if(null==condition){
			condition = new CourseInfoCondition();
			condition.setType(type);
			condition.setCourseId(courseId);
		}
		//组织与学习活动
		OfficeCourse officeCourse = new OfficeCourse();
		officeCourse.setCourseId(courseId);
		officeCourse.setType(type);
		List<OfficeCourse> list = officeCourseService.findList(officeCourse);
		if(null != list && list.size()>0){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<list.size();i++){
				OfficeCourse oa = list.get(i);
				String id = oa.getOfficeId();
				sb.append(id + ",");
			}
			condition.setOfficIds( sb.toString());
		}
		//岗位与学习活动
		PostCourse postCourse = new PostCourse();
		postCourse.setCourseId(courseId);
		postCourse.setType(type);
		List<PostCourse> listPost = postCourseService.showPpostCourseList(postCourse);
		StringBuffer sb = new StringBuffer();
		StringBuffer sbShow = new StringBuffer();
		String postIds = "";
		if(null != listPost && listPost.size()>0){
			for(int i=0;i<listPost.size();i++){
				PostCourse pt = listPost.get(i);
				sb.append(pt.getPostLevel()+",");
				sbShow.append(pt.getPostName() + "--" + pt.getPostLevelName()+ "  ");
				if(i==listPost.size()-1){
					postIds = sb.substring(0, sb.length()-1);
				}
			}
			condition.setPostIds(postIds);
			condition.setUserBxPost(sbShow.toString());
		}
		model.addAttribute("condition", condition);
		model.addAttribute("edit",edit);
		return "modules/course/courseInfoFormBx";
	
	}
	
	/**
	 * 教师端查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormBxXx")
	public String teacherFormBxXx(CourseInfo courseInfo,String type,String edit,HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("courseInfo", courseInfo);

		String courseId = courseInfo.getId();
		//获取查询条件
		Map<String,String> mapPa = new HashMap<String,String>();
		mapPa.put("courseId",courseId );
		mapPa.put("type", type);
		CourseInfoCondition condition = courseInfoConditionService.findActivityConditionByActivityId(mapPa);
		if(null==condition){
			condition = new CourseInfoCondition();
			condition.setType(type);
		}
		//组织与学习活动
		OfficeCourse officeCourse = new OfficeCourse();
		officeCourse.setCourseId(courseId);
		officeCourse.setType(type);
		List<OfficeCourse> list = officeCourseService.findList(officeCourse);
		if(null != list && list.size()>0){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<list.size();i++){
				OfficeCourse oa = list.get(i);
				String id = oa.getOfficeId();
				sb.append(id + ",");
			}
			condition.setOfficIds( sb.toString());
		}
		//岗位与学习活动
		PostCourse postCourse = new PostCourse();
		postCourse.setCourseId(courseId);
		postCourse.setType(type);
		List<PostCourse> listPost = postCourseService.showPpostCourseList(postCourse);
		StringBuffer sb = new StringBuffer();
		StringBuffer sbShow = new StringBuffer();
		String postIds = "";
		if(null != listPost && listPost.size()>0){
			for(int i=0;i<listPost.size();i++){
				PostCourse pt = listPost.get(i);
				sb.append(pt.getPostLevel()+",");
				sbShow.append(pt.getPostName() + "--" + pt.getPostLevelName()+ "  ");
				if(i==listPost.size()-1){
					postIds = sb.substring(0, sb.length()-1);
				}
			}
			condition.setPostIds(postIds);
			condition.setUserBxPost(sbShow.toString());
		}
		model.addAttribute("condition", condition);
		model.addAttribute("edit",edit);
		return "modules/course/teacherCourseInfoFormBx";
	}
	/**
	 * 保存学习活动
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveBase")
	public String saveBase(CourseInfo courseInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, courseInfo)){
			return form(courseInfo, model);
		}
		if(!courseInfo.getIsNewRecord()){//编辑表单保存
			CourseInfo t = courseInfoService.get(courseInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(courseInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			courseInfoService.saveBase(courseInfo);//保存
			addMessage(redirectAttributes, "修改学习活动成功");//修改页面直接跳转
			return "redirect:"+Global.getAdminPath()+"/course/courseInfo/list/?repage";
		}else{//新增表单保存
			String courseInfoId = IdGen.uuid();
			courseInfo.setIsNewRecord(true);
			courseInfo.setId(courseInfoId);
			courseInfo.setIsCreateAdmin("1");
			courseInfoService.saveBase(courseInfo);//保存
			//判断当前用户是否为管理员如果是管理员则自动审核
			String userRoleEnName = UserUtils.getUser().getRole().getEnname();
			if(UserUtils.USER_SYSTEM_ROLE_ENNAME.equals(userRoleEnName)){//自动提交审核
				ApprovalRecord ap = new ApprovalRecord();
				ap.setIsNewRecord(true);
				ap.setId(IdGen.uuid());
				ap.setResourceId(courseInfoId);
				ap.setStatus("3");
				ap.setOpinion("管理员提交自动审核通过。");
				approvalRecordService.save(ap);
			}
			String isImportUser = courseInfo.getIsImportUser();
			addMessage(redirectAttributes, "添加学习活动成功");
			//通过excel导入学员信息
			if("1".equals(isImportUser)){
				return "modules/course/courseInfoFormImport";
			}else{
				return "redirect:"+Global.getAdminPath()+"/course/courseInfo/formBxXx?id="+courseInfo.getId()+"&type=1";
			}
		}
	}
	
	/**
	 * 发布课程
	 */
	@RequiresPermissions(value={"course:courseInfo:add","course:courseInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "fbCourseInfo")
	public String fbCourseInfo(String resourceId, Model model, RedirectAttributes redirectAttributes) throws Exception{
		ApprovalRecord appr = new ApprovalRecord();
		appr.setResourceId(resourceId);
		 appr = approvalRecordService.findByResourceId(appr);
		if(appr.getId() !=null ){
			appr.setStatus("0");
			approvalRecordService.save(appr);
			addMessage(redirectAttributes, "发布课程成功!");
		}else{
			addMessage(redirectAttributes, "发布课程失败!");
		}
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/shList?repage";
	}
	
	
	/**
	 * 教师端保存保存课程
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherSaveBase")
	public String teacherSaveBase(CourseInfo courseInfo, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, courseInfo)){
			return form(courseInfo, model);
		}
		if(!courseInfo.getIsNewRecord()){//编辑表单保存
			CourseInfo t = courseInfoService.get(courseInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(courseInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			courseInfoService.saveBase(courseInfo);//保存
			addMessage(redirectAttributes, "修改学习活动成功");//修改页面直接跳转
			return "redirect:"+Global.getAdminPath()+"/course/tcourseInfo/findMyCourseInfo?repage";
		}else{//新增表单保存
			String courseInfoId = IdGen.uuid();
			courseInfo.setIsNewRecord(true);
			courseInfo.setId(courseInfoId);
			courseInfoService.saveBase(courseInfo);//保存
			//判断当前用户是否为管理员如果是管理员则自动审核
			String userRoleEnName = UserUtils.getUser().getRole().getEnname();
			if(UserUtils.USER_SYSTEM_ROLE_ENNAME.equals(userRoleEnName)){//自动提交审核
				ApprovalRecord ap = new ApprovalRecord();
				ap.setIsNewRecord(true);
				ap.setId(IdGen.uuid());
				ap.setResourceId(courseInfoId);
				ap.setStatus("0");
				ap.setOpinion("管理员提交自动审核通过。");
				approvalRecordService.save(ap);
			}
			String isImportUser = courseInfo.getIsImportUser();
			addMessage(redirectAttributes, "添加学习活动成功");
			//通过excel导入学员信息
			if("1".equals(isImportUser)){
				return "modules/course/teacherCourseInfoFormImport";
			}else{
				return "redirect:"+Global.getAdminPath()+"/course/courseInfo/teacherFormBxXx?id="+courseInfo.getId()+"&type=1";
			}
		}
	}
	/**
	 * 保存学习活动-必学
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveBx")
	public String saveBx(CourseInfo courseInfo,String type,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
			courseInfoService.saveBx(courseInfo,type,request,courseInfoConditionService);//保存
			if("1".equals(type)){
				addMessage(redirectAttributes, "添加必选学员成功");
			}else if("0".equals(type)){
				addMessage(redirectAttributes, "添加选学学员成功");
			}else{
				addMessage(redirectAttributes, "添加学员失败");
			}
			String edit = request.getParameter("edit");
			if(null != edit && "1".equals(edit)){
				return "redirect:"+Global.getAdminPath()+"/course/courseInfo/";
			}else{
				if("1".equals(courseInfo.getIsImportXxUser())){
					return "modules/course/courseInfoFormImportXx";
				}else{
					if("0".equals(type)){
						return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
					}else if("1".equals(type)){
						return "redirect:"+Global.getAdminPath()+"/course/courseInfo/formBxXx?id="+courseInfo.getId()+"&type=0";
					}
				}
			}
			return "redirect:"+Global.getAdminPath()+"/course/courseInfo/";
	}
	
	/**
	 * 教师端保存学习活动-必学
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherSaveBx")
	public String teacherSaveBx(CourseInfo courseInfo,String type,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		courseInfoService.saveBx(courseInfo,type,request,courseInfoConditionService);//保存
		if("1".equals(type)){
			addMessage(redirectAttributes, "添加必选学员成功");
		}else if("0".equals(type)){
			addMessage(redirectAttributes, "添加选学学员成功");
		}else{
			addMessage(redirectAttributes, "添加学员失败");
		}
		String edit = request.getParameter("edit");
		if(null != edit && "1".equals(edit)){
			return "redirect:"+Global.getAdminPath()+"/course/tcourseInfo/findMyCourseInfo?repage";
		}else{
			if("1".equals(courseInfo.getIsImportXxUser())){
				return "modules/course/teacherCourseInfoFormImportXx";
			}else{
				if("0".equals(type)){
					return "redirect:"+Global.getAdminPath()+"/course/tcourseInfo/findMyCourseInfo?repage";
				}else if("1".equals(type)){
					return "redirect:"+Global.getAdminPath()+"/course/courseInfo/teacherFormBxXx?id="+courseInfo.getId()+"&type=0";
				}
			}
		}
		return "redirect:"+Global.getAdminPath()+"/course/tcourseInfo/findMyCourseInfo?repage";
	}
	
	/**
	 * 保存学习活动-选学
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveXx")
	public String saveXx(CourseInfo CourseInfo,Office office,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		courseInfoService.saveXx(CourseInfo,request,courseInfoConditionService);//保存
		addMessage(redirectAttributes, "添加可选学员成功");
		return "redirect:"+Global.getAdminPath()+"/course/courseInfo/?repage";
	}
	/**
	 * 教学端保存学习活动-选学
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherSaveXx")
	public String teacherSaveXx(CourseInfo CourseInfo,Office office,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		courseInfoService.saveXx(CourseInfo,request,courseInfoConditionService);//保存
		addMessage(redirectAttributes, "添加可选学员成功");
		return "redirect:"+Global.getAdminPath()+"/course/tcourseInfo/findMyCourseInfo/?repage";
	}
	
	
	/**
	 * 机构管理员学员管理
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"showListCourRegUserForJgSystem"})
	public String showListCourRegUserForJgSystem(@RequestParam(required=false) String courseId,User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setOfficeId(courseId);
		user.setId(UserUtils.getUser().getId());
		Page<User> page = userCourseSercice.findUserListForJgSystem(new Page<User>(request, response), user); 
		CourseInfo courserInfo = courseInfoService.get(courseId);
		model.addAttribute("page", page);
		model.addAttribute("courseId", courseId);
		model.addAttribute("user", user);
		model.addAttribute("courserInfo", courserInfo);
		return "modules/course/showListCourRegUserForJgSystem";
	}
	
	/**
	 * 机构管理员报名学员
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"findAddCourseUserListForJgSystem"})
	public String findAddCourseUserListForJgSystem(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setId(UserUtils.getUser().getId());
		Page<User> page = systemService.findAddCourseUserListForJgSystem(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        model.addAttribute("userActivityId", user.getUserActivityId());
		return "modules/course/findAddCourseUserListForJgSystem";
	}
	
	/**
	 *机构管理员报名学员课程
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("addCourseUserForJgSystem")
	@ResponseBody
	public String addCourseUserForJgSystem(HttpServletRequest request, HttpServletResponse response, Model model,String ids,String courserId) {
		String str = "0";
		try {
			if(null!=ids && !"".equals(ids.trim())){
				String id[] = ids.split(",");
				for (int i = 0; i < id.length; i++) {
					UserCourseReg userReg = new UserCourseReg();
					userReg.setCourseId(courserId);
					userReg.setIsNewRecord(true);
					userReg.setBmUserId(id[i]);
					userReg.setAuditState("1");
					userReg.setId(IdGen.uuid());
					userCourseRegService.saveForSystem(userReg);
				}
			}
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}