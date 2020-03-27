/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.course.entity.CourseInfoCondition;
import com.jeeplus.modules.course.entity.UserCourseReg;
import com.jeeplus.modules.sys.entity.ApprovalRecord;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.ApprovalRecordService;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.train.entity.OfficeActivity;
import com.jeeplus.modules.train.entity.PostActivity;
import com.jeeplus.modules.train.entity.StudyActivity;
import com.jeeplus.modules.train.entity.StudyActivityCondition;
import com.jeeplus.modules.train.entity.UserActivityReg;
import com.jeeplus.modules.train.service.OfficeActivityService;
import com.jeeplus.modules.train.service.PostActivityService;
import com.jeeplus.modules.train.service.SearchTempService;
import com.jeeplus.modules.train.service.StudyActivityConditionService;
import com.jeeplus.modules.train.service.StudyActivityService;
import com.jeeplus.modules.train.service.UserActivityRegService;
import com.jeeplus.modules.train.service.UserActivityService;
/**
 * 学习活动Controller
 * @author panjp
 * @version 2017-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/train/studyActivity")
public class StudyActivityController extends BaseController {

	@Autowired
	private StudyActivityService studyActivityService;
	@Autowired
	private OfficeActivityService officeActivityService;
	@Autowired
	private PostActivityService postActivityService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SearchTempService searchTempService;
	@Autowired
	private  StudyActivityConditionService studyActivityConditionService;
	
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private ApprovalRecordService approvalRecordService;
	@Autowired
	private UserActivityRegService userActivityRegService;
	@Autowired
	@ModelAttribute
	public StudyActivity get(@RequestParam(required=false) String id) {
		StudyActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studyActivityService.get(id);
		}
		if (entity == null){
			entity = new StudyActivity();
		}
		return entity;
	}
	
	/**
	 * 学习活动列表页面
	 */
	@RequiresPermissions("train:studyActivity:list")
	@RequestMapping(value = {"list"})
	public String list(StudyActivity studyActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudyActivity> page = studyActivityService.findPage(new Page<StudyActivity>(request, response), studyActivity); 
		model.addAttribute("page", page);
		model.addAttribute("roleCode", UserUtils.getUser().getRole().getEnname());
		return "modules/train/studyActivityList";
	}
	/**
	 * 审核学习活动列表页面
	 */
	@RequiresPermissions("train:studyActivity:list")
	@RequestMapping("shStudyActivity")
	public String shStudyActivity(StudyActivity studyActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudyActivity> page = studyActivityService.findPage(new Page<StudyActivity>(request, response), studyActivity); 
		model.addAttribute("page", page);
		return "modules/train/shStudyActivityList";
	}
	/**
	 * 查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "form")
	public String form(StudyActivity studyActivity, Model model) {
		model.addAttribute("studyActivity", studyActivity);
		return "modules/train/studyActivityForm";
	}
	
	
	/**
	 * 查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formBase")
	public String formBase(StudyActivity studyActivity, Model model) {
		model.addAttribute("studyActivity", studyActivity);
		return "modules/train/studyActivityFormBase";
	}
	
	/**
	 * 教师端查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormBase")
	public String teacherFormBase(StudyActivity studyActivity, Model model) {
		model.addAttribute("studyActivity", studyActivity);
		return "modules/train/teacherStudyActivityFormBase";
	}
	
	
	/**
	 * 查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formImport")
	public String formImport(StudyActivity studyActivity, Model model) {
		model.addAttribute("studyActivity", studyActivity);
		return "modules/train/studyActivityFormImport";
	}
	
	/**
	 * 教师端查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormImport")
	public String teacherFormImport(StudyActivity studyActivity, Model model) {
		model.addAttribute("studyActivity", studyActivity);
		return "modules/train/teacherStudyActivityFormImport";
	}
	/**
	 * 查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formImportXx")
	public String formImportXx(StudyActivity studyActivity, Model model) {
		model.addAttribute("studyActivity", studyActivity);
		if("1".equals(studyActivity.getIsImportXxUser())){
			return "modules/train/studyActivityFormImportXx";//进入选学导入页面
		}else{
			//return "modules/train/studyActivityFormXx";//进入选学页面
			return "redirect:"+Global.getAdminPath()+"/train/studyActivity/formBxXx?id="+studyActivity.getId()+"&type=0"; 
		}
	}
	
	/**
	 * 教师端查看，增加，编辑学习活动表单页面选学
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormImportXx")
	public String teacherFormImportXx(StudyActivity studyActivity, Model model) {
		model.addAttribute("studyActivity", studyActivity);
		if("1".equals(studyActivity.getIsImportXxUser())){
			return "modules/train/teacherStudyActivityFormImportXx";//进入选学导入页面
		}else{
			return "modules/train/teacherStudyActivityFormXx";//进入选学页面
		}
	}

	/**
	 * 查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formBxXx")
	public String formBxXx(StudyActivity studyActivity,String type,String edit,HttpServletRequest request, HttpServletResponse response, Model model) {
		//获取查询条件
		Map<String,String> mapPa = new HashMap<String,String>();
		mapPa.put("studyActivityId", studyActivity.getId());
		mapPa.put("type", type);
		StudyActivityCondition condition = studyActivityConditionService.findActivityConditionByActivityId(mapPa);
		if(null==condition){
			condition = new StudyActivityCondition();
			condition.setType(type);
			condition.setStudyActivityId(studyActivity.getId());
		}
		//查询组织
		//组织与学习活动
		OfficeActivity officeActivity = new OfficeActivity();
		officeActivity.setActivityId(studyActivity.getId());
		officeActivity.setType(type);
		List<OfficeActivity> list = officeActivityService.findOfficeListMap(officeActivity);
		if(null != list && list.size()>0){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<list.size();i++){
				OfficeActivity oa = list.get(i);
				String id = oa.getId();
				sb.append(id + ",");
			}
			condition.setOfficIds(sb.toString());
		}
		//岗位与学习活动
		PostActivity postActivity = new PostActivity();
		postActivity.setActivityId(studyActivity.getId());
		postActivity.setType(type);
		List<PostActivity> listPost = postActivityService.showPostActiveList(postActivity);
		StringBuffer sb = new StringBuffer();
		StringBuffer sbShow = new StringBuffer();
		String postIds = "";
		if(null != listPost && listPost.size()>0){
			for(int i=0;i<listPost.size();i++){
				PostActivity pt = listPost.get(i);
				sb.append(pt.getId()+",");
				sbShow.append(pt.getPostName() + "--" + pt.getPostLevelName()+ "  ");
				if(i==listPost.size()-1){
					postIds = sb.substring(0, sb.length()-1);
				}
			}
			condition.setPostIds(postIds);
			condition.setUserBxPost(sbShow.toString());
		}
		model.addAttribute("condition", condition);
		model.addAttribute("edit", edit);
		//判断哪些数据需要回重新显示
		return "modules/train/studyActivityFormBx";
	}
	
	/**
	 *教师端 查看，增加，编辑学习活动表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormBxXx")
	public String teacherFormBxXx(StudyActivity studyActivity,String type,String edit,HttpServletRequest request, HttpServletResponse response, Model model) {
		//获取查询条件
		Map<String,String> mapPa = new HashMap<String,String>();
		mapPa.put("studyActivityId", studyActivity.getId());
		mapPa.put("type", type);
		StudyActivityCondition condition = studyActivityConditionService.findActivityConditionByActivityId(mapPa);
		if(null==condition){
			condition = new StudyActivityCondition();
			condition.setType(type);
			condition.setStudyActivityId(studyActivity.getId());
		}
		//查询组织
		//组织与学习活动
		OfficeActivity officeActivity = new OfficeActivity();
		officeActivity.setActivityId(studyActivity.getId());
		officeActivity.setType(type);
		List<OfficeActivity> list = officeActivityService.findOfficeListMap(officeActivity);
		if(null != list && list.size()>0){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<list.size();i++){
				OfficeActivity oa = list.get(i);
				String id = oa.getId();
				sb.append(id + ",");
			}
			condition.setOfficIds(sb.toString());
		}
		//岗位与学习活动
		PostActivity postActivity = new PostActivity();
		postActivity.setActivityId(studyActivity.getId());
		postActivity.setType(type);
		List<PostActivity> listPost = postActivityService.showPostActiveList(postActivity);
		StringBuffer sb = new StringBuffer();
		StringBuffer sbShow = new StringBuffer();
		String postIds = "";
		if(null != listPost && listPost.size()>0){
			for(int i=0;i<listPost.size();i++){
				PostActivity pt = listPost.get(i);
				sb.append(pt.getId()+",");
				sbShow.append(pt.getPostName() + "--" + pt.getPostLevelName()+ "  ");
				if(i==listPost.size()-1){
					postIds = sb.substring(0, sb.length()-1);
				}
			}
			condition.setPostIds(postIds);
			condition.setUserBxPost(sbShow.toString());
		}
		model.addAttribute("condition", condition);
		model.addAttribute("edit", edit);
		//判断哪些数据需要回重新显示
		return "modules/train/teacherStudyActivityFormBx";
	}
	
	
	@RequestMapping(value = {"selOfficeList"})
	public String selOfficeList(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		String isCheckAllBx = request.getParameter("isCheckAllBx");
		Page<Office>  page = new Page<Office>(request, response);//如果前面选择了全选，则后面不选择了。如果前面没选择全选，则后面正常的加判断条件
		if(null == isCheckAllBx || "".equals(isCheckAllBx)){//没有选择全选
			String officeIdsBx = request.getParameter("officeIdsBx");
			if(null != officeIdsBx && !"".equals(officeIdsBx) && officeIdsBx.length()>0){
				officeIdsBx = officeIdsBx.substring(0, officeIdsBx.length()-1);
				String [] officeIdsBxs = officeIdsBx.split(",");//组织的ids
				//使用exists，改用创建一个临时的中间表，先将数据添加到中间表里，然后再进行查询
				List<Map> lists = new ArrayList<Map>();
				for(int i=0;i<officeIdsBxs.length;i++){
					if( officeIdsBxs[i] != null && !"".equals(officeIdsBxs[i])){
						Map map = new HashMap();
						map.put("searchCond", officeIdsBxs[i]);
						map.put("type","1");
						lists.add(map);
					}
				}
				searchTempService.save(lists);//保存查询条件
				//联查中间表的数据
				page.setPageSize(4);
				office.setPage(page);
				page.setList(officeService.findListForOverOneThousand(office));
				//删除临时表的数据
				searchTempService.delete();
			}else{//首次进来，默认情况(?)
				page.setPageSize(4);
				if(office == null){
					office = new Office();
					office.setCurrentUser(UserUtils.getUser());
				}
				page = officeService.findPage(page, office); 
			}
		}
		model.addAttribute("page", page);
		return "modules/train/studyActivityFormBx";
	}
	/**
	 * 保存学习活动
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save")
	public String save(StudyActivity studyActivity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, studyActivity)){
			return form(studyActivity, model);
		}
		studyActivityService.save(studyActivity);//保存
		addMessage(redirectAttributes, "保存学习活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/studyActivity/list/?repage";
	}
	/**
	 * 保存学习活动
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveBase")
	public String saveBase(StudyActivity studyActivity, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, studyActivity)){
			return form(studyActivity, model);
		}
		if(!studyActivity.getIsNewRecord()){//编辑表单保存
			StudyActivity t = studyActivityService.get(studyActivity.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(studyActivity, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			studyActivityService.saveBase(studyActivity);//保存
			addMessage(redirectAttributes, "修改学习活动成功");//修改页面直接跳转
			return "redirect:"+Global.getAdminPath()+"/train/studyActivity/list/?repage";
		}else{//新增表单保存
			String studyActId = IdGen.uuid();
			studyActivity.setId(studyActId);
			studyActivity.setIsNewRecord(true);
			studyActivity.setIsCreateAdmin("1");
			studyActivityService.saveBase(studyActivity);//保存
			//判断当前用户是否为管理员如果是管理员则自动审核
			String userRoleEnName = UserUtils.getUser().getRole().getEnname();
			if(UserUtils.USER_SYSTEM_ROLE_ENNAME.equals(userRoleEnName)){//自动提交审核
				ApprovalRecord ap = new ApprovalRecord();
				ap.setIsNewRecord(true);
				ap.setId(IdGen.uuid());
				ap.setResourceId(studyActId);
				ap.setStatus("3");
				ap.setOpinion("管理员提交自动审核通过。");
				approvalRecordService.save(ap);
			}
			addMessage(redirectAttributes, "添加学习活动成功");
			String isImportUser = request.getParameter("isImportUser");
			//通过excel导入学员信息
			if("1".equals(isImportUser)){
				return "modules/train/studyActivityFormImport";
			}else{
				return "redirect:"+Global.getAdminPath()+"/train/studyActivity/formBxXx?id="+studyActivity.getId()+"&type=1";
			}
		}
	}
	
	/**
	 * 保存课程管理
	 */
	@RequiresPermissions(value={"train:studyActivity:edit"})
	@RequestMapping(value = "fbStudyActivity")
	public String fbStudyActivity(String resourceId, Model model, RedirectAttributes redirectAttributes) throws Exception{
		ApprovalRecord appr = new ApprovalRecord();
		appr.setResourceId(resourceId);
		 appr = approvalRecordService.findByResourceId(appr);
		if(appr.getId() !=null ){
			appr.setStatus("0");
			approvalRecordService.save(appr);
			addMessage(redirectAttributes, "发布活动成功!");
		}else{
			addMessage(redirectAttributes, "发布活动失败!");
		}
		return "redirect:"+Global.getAdminPath()+"/train/studyActivity/shStudyActivity";
	}
	/**
	 * 教师端保存学习活动
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherSaveBase")
	public String teacherSaveBase(StudyActivity studyActivity, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if (!beanValidator(model, studyActivity)){
			return form(studyActivity, model);
		}
		if(!studyActivity.getIsNewRecord()){//编辑表单保存
			StudyActivity t = studyActivityService.get(studyActivity.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(studyActivity, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			studyActivityService.saveBase(studyActivity);//保存
			addMessage(redirectAttributes, "修改学习活动成功");//修改页面直接跳转
			return "redirect:"+Global.getAdminPath()+"/myTeacherStudyActivity?repage";
		}else{//新增表单保存
				String studyActId = IdGen.uuid();
				studyActivity.setId(studyActId);
				studyActivity.setIsNewRecord(true);
				studyActivityService.saveBase(studyActivity);//保存
				
				//判断当前用户是否为管理员如果是管理员则自动审核
				String userRoleEnName = UserUtils.getUser().getRole().getEnname();
				if(UserUtils.USER_SYSTEM_ROLE_ENNAME.equals(userRoleEnName)){//自动提交审核
					ApprovalRecord ap = new ApprovalRecord();
					ap.setIsNewRecord(true);
					ap.setId(IdGen.uuid());
					ap.setResourceId(studyActId);
					ap.setStatus("0");
					ap.setOpinion("管理员提交自动审核通过。");
					approvalRecordService.save(ap);
				}
				addMessage(redirectAttributes, "添加学习活动成功");
				String isImportUser = request.getParameter("isImportUser");
				model.addAttribute("studyActivity", studyActivity);
			//通过excel导入学员信息
			if("1".equals(isImportUser)){
				return "modules/train/teacherStudyActivityFormImport";
			}else{
				return "redirect:"+Global.getAdminPath()+"/train/studyActivity/teacherFormBxXx?id="+studyActivity.getId()+"&type=1";
			}
		}
	}
	
	/**
	 * 保存学习活动-必学
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveBx")
	public String saveBx(StudyActivity studyActivity,String type,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
			studyActivityService.saveBx(studyActivity,type,request,studyActivityConditionService);//保存
			if("1".equals(type)){
				addMessage(redirectAttributes, "添加必必学员成功");
			}else if("0".equals(type)){
				addMessage(redirectAttributes, "添加必选学员成功");
			}else{
				addMessage(redirectAttributes, "添加学员失败");
			}
			String edit = request.getParameter("edit");//
			if(null != edit && "1".equals(edit)){
				return "redirect:"+Global.getAdminPath()+"/train/studyActivity/list/?repage";
			}else{
				if("1".equals(studyActivity.getIsImportXxUser())){
					return "modules/train/studyActivityFormImportXx";
				}else{
					if("0".equals(type)){
						return "redirect:"+Global.getAdminPath()+"/train/studyActivity/list/?repage";
					}else if("1".equals(type)){
						return "redirect:"+Global.getAdminPath()+"/train/studyActivity/formBxXx?id="+studyActivity.getId()+"&type=0";
					}
				}
			}
			return "redirect:"+Global.getAdminPath()+"/train/studyActivity/list/?repage";
	}
	
	/**
	 * 教师端保存学习活动-必学
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherSaveBx")
	public String teacherSaveBx(StudyActivity studyActivity,String type,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
			studyActivityService.saveBx(studyActivity,type,request,studyActivityConditionService);//保存
			if("1".equals(type)){
				addMessage(redirectAttributes, "添加必必学员成功");
			}else if("0".equals(type)){
				addMessage(redirectAttributes, "添加必选学员成功");
			}else{
				addMessage(redirectAttributes, "添加学员失败");
			}
			String edit = request.getParameter("edit");//
			if(null != edit && "1".equals(edit)){
				return "redirect:"+Global.getAdminPath()+"/myTeacherStudyActivity?repage";
			}else{
				//20170701 type=1这种情况已经废弃
				if("1".equals(studyActivity.getIsImportXxUser())){
					return "modules/train/teacherStudyActivityFormImportXx";
				}else{
					if("0".equals(type)){
						return "redirect:"+Global.getAdminPath()+"/myTeacherStudyActivity?repage";
					}else if("1".equals(type)){
						return "redirect:"+Global.getAdminPath()+"/train/studyActivity/teacherFormBxXx?id="+studyActivity.getId()+"&type=0";
					}
				}
			}
			return "redirect:"+Global.getAdminPath()+"/myTeacherStudyActivity?repage";
	}
	
	/**
	 * 教师端保存学习活动-选学
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherSaveXx")
	public String teacherSaveXx(StudyActivity studyActivity,Office office,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		studyActivityService.saveXx(studyActivity,request,studyActivityConditionService);//保存
		addMessage(redirectAttributes, "添加可选学员成功");
		return "redirect:"+Global.getAdminPath()+"/myTeacherStudyActivity/?repage";
	}
	
	/**
	 * 保存学习活动-选学
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveXx")
	public String saveXx(StudyActivity studyActivity,Office office,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		studyActivityService.saveXx(studyActivity,request,studyActivityConditionService);//保存
		addMessage(redirectAttributes, "添加可选学员成功");
		return "redirect:"+Global.getAdminPath()+"/train/studyActivity/list/?repage";
	}
	/**
	 * 查询必学学员和可以报名的学院
	 */
//	@RequiresPermissions("course:userCourse:list")
	@RequestMapping(value = {"listStudentForActive"})
	public String listStudentForActive(String activityid,String type, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		OfficeActivity officeActivity = new OfficeActivity();
		officeActivity.setActivityId(activityid);
		officeActivity.setType(type);
		Page<OfficeActivity> page = officeActivityService.findOfficeListMap(new Page<OfficeActivity>(request, response), officeActivity); 
		
		//根据课程id和类型id，查找所有的人员
		int officeTypeCount = officeActivityService.findUserOfficeTypeCount(activityid, type);
		int userCount = officeActivityService.findUserCount(activityid, type);
		
		PostActivity postActivity = new PostActivity();
		postActivity.setActivityId(activityid);
		postActivity.setType(type);
		List<PostActivity> postList = postActivityService.showPostActiveList(postActivity);
		
		model.addAttribute("officeTypeCount",officeTypeCount);
		model.addAttribute("userCount",userCount);
		model.addAttribute("postList",postList);
		model.addAttribute("page", page);
		
		return "modules/course/studentActivityManageViewStudent";
	}
	/**
	 * 删除学习活动
	 */
	@RequiresPermissions("train:studyActivity:del")
	@RequestMapping(value = "delete")
	public String delete(StudyActivity studyActivity, RedirectAttributes redirectAttributes) {
		studyActivityService.delete(studyActivity);
		addMessage(redirectAttributes, "删除学习活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/studyActivity/list/?repage";
	}
	/**
	 * 删除学习活动
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherDelStudyActity")
	public String teacherDelStudyActity(StudyActivity studyActivity, RedirectAttributes redirectAttributes) {
		studyActivityService.delete(studyActivity);
		addMessage(redirectAttributes, "删除学习活动成功");
		return "redirect:"+Global.getAdminPath()+"/myTeacherStudyActivity/?repage";
	}
	
	/**
	 * 批量删除学习活动
	 */
	@RequiresPermissions("train:studyActivity:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			studyActivityService.delete(studyActivityService.get(id));
		}
		addMessage(redirectAttributes, "删除学习活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/studyActivity/list/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:studyActivity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(StudyActivity studyActivity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学习活动"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StudyActivity> page = studyActivityService.findPage(new Page<StudyActivity>(request, response, -1), studyActivity);
    		new ExportExcel("学习活动", StudyActivity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学习活动记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/studyActivity/?repage";
    }
	
	/**
	 * 导入Excel数据
	 */
	@RequiresPermissions("train:studyActivity:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String edit = request.getParameter("edit");
		String type = request.getParameter("type");//导入类型
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if(HeadCst.headValid(ei)){
				request.setAttribute("message", "您的导入模板不正确，请到账号管理功能模块下载模板数据");
			}else{
				List<User> list = ei.getDataList(User.class);
				String id = request.getParameter("id");//学习活动的id
				Map map = new HashMap();
				map.put("activId", id);
				map.put("type",type);
				if(null != edit){//修改的时候，删除之前的数据
					//删除学员活动
					userActivityService.deleteUserActivityByActivId(map);
				}
				if(null != list && list.size()>1){
					for(int i=0;i<list.size();i++){
						try{
							String loginName = list.get(i).getLoginName();
							User userExits = systemService.getUserByLoginName(loginName);//如果存在
							if(null != userExits && !UserUtils.USER_TEACHER_ENNAME.equals(userExits.getRole().getEnname())){
								int result = studyActivityService.saveForImport(type,userExits.getId(),id,edit);
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
			return "modules/train/studyActivityFormImport";
		}else{
			return "modules/train/studyActivityFormImportXx";
		}
    }

	/**
	 * 导入Excel数据
	 */
	@RequiresPermissions("user")
    @RequestMapping(value="teacherImport", method=RequestMethod.POST)
    public String teacherImport(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String edit = request.getParameter("edit");
		String type = request.getParameter("type");//导入类型
		int successNum = 0;
		int failureNum = 0;
		try {
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if(HeadCst.headValid(ei)){
				request.setAttribute("message", "您的导入模板不正确，请到账号管理功能模块下载模板数据");
			}else{
				List<User> list = ei.getDataList(User.class);
				String id = request.getParameter("id");//学习活动的id
				Map map = new HashMap();
				map.put("activId", id);
				map.put("type",type);
				if(null != edit && !"".equals(edit)){//修改的时候，删除之前的数据
					//删除学员活动
					userActivityService.deleteUserActivityByActivId(map);
				}
				if(null != list && list.size()>1){
					for(int i=0;i<list.size();i++){
						try{
							String loginName = list.get(i).getLoginName();
							User userExits = systemService.getUserByLoginName(loginName);//如果存在
							if(null != userExits && !UserUtils.USER_TEACHER_ENNAME.equals(userExits.getRole().getEnname())){
								int result = studyActivityService.saveForImport(type,userExits.getId(),id,edit);
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
			return "modules/train/teacherStudyActivityFormImport";
		}else{
			return "modules/train/teacherStudyActivityFormImportXx";
		}
	
    }

	
	
	/**
	 * 下载导入学习活动数据模板
	 */
	@RequiresPermissions("train:studyActivity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学习活动数据导入模板.xlsx";
    		List<StudyActivity> list = Lists.newArrayList(); 
    		new ExportExcel("学习活动数据", StudyActivity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/studyActivity/?repage";
    }
	
	/**
	 * 查看
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formview")
	public String formView(StudyActivity studyActivity, Model model) {
		model.addAttribute("studyActivity", studyActivity);
		return "modules/train/studyActivityView";
	}
	
	/**
	 * 全部学习活动查看
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormviewALlActivity")
	public String teacherFormviewALlActivity(String id , Model model){
		StudyActivity studyActivity = studyActivityService.get(id);
		model.addAttribute("studyActivity", studyActivity);
		return "modules/train/teacherFormviewALlActivity";
	}
	
	/**
	 *我的学习活动查看
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormviewStudyActivity")
	public String teacherFormviewStudyActivity(String id , Model model){
		StudyActivity studyActivity = studyActivityService.get(id);
		model.addAttribute("studyActivity", studyActivity);
		return "modules/train/teacherStudyActivityView";
	}
	
	/**
	 *机构管理员查看活动
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formviewStudyActivityForJgSystem")
	public String formviewStudyActivityForJgSystem(String id , Model model){
		StudyActivity studyActivity = studyActivityService.get(id);
		model.addAttribute("studyActivity", studyActivity);
		return "modules/train/studyActivityViewForJgSystem";
	}
	
	/**
	 * 机构管理员学员展示
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"listbyactivityidForJgSystem"})
	public String listbyactivityidForJgSystem(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = new User();
		user.setId(UserUtils.getUser().getId());
		user.setOfficeId(activityId);
		Page<User> page = userActivityRegService.findUserListForJgSystem(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		model.addAttribute("activityid", activityId);
		return "modules/train/listbyactivityidForJgSystem";
	}
	/**
	 * 机构管理员报名
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"findBmStudyUserListForJgSystem"})
	public String findBmStudyUserListForJgSystem(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setId(UserUtils.getUser().getId());
		Page<User> page = systemService.findBmStudyUserListForJgSystem(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        model.addAttribute("userActivityId", user.getUserActivityId());
		return "modules/train/findBmStudyUserListForJgSystem";
	}
	/**
	 *机构管理员报名学员学习活动
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("addStudyActiveUserForJgSystem")
	@ResponseBody
	public String addStudyActiveUserForJgSystem(HttpServletRequest request, HttpServletResponse response, Model model,String ids,String userActivityId) {
		String str = "0";
		try {
			if(null!=ids && !"".equals(ids.trim())){
				String id[] = ids.split(",");
				for (int i = 0; i < id.length; i++) {
					UserActivityReg userReg = new UserActivityReg();
					userReg.setActivityId(userActivityId);
					userReg.setIsNewRecord(true);
					userReg.setBmUserId(id[i]);
					userReg.setAuditState("1");
					userReg.setId(IdGen.uuid());
					userActivityRegService.saveForSystem(userReg);
				}
			}
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}