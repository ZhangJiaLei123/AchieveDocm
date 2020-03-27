package com.jeeplus.modules.tcourse.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.tcourse.service.StudyActivityTeacherService;
import com.jeeplus.modules.train.entity.ActivityDir;
import com.jeeplus.modules.train.entity.LessonTime;
import com.jeeplus.modules.train.entity.ProgramActivity;
import com.jeeplus.modules.train.entity.ProgramFocus;
import com.jeeplus.modules.train.entity.StudyActivity;
import com.jeeplus.modules.train.entity.TrainProgram;
import com.jeeplus.modules.train.entity.UserActivity;
import com.jeeplus.modules.train.entity.UserActivityReg;
import com.jeeplus.modules.train.service.ActivityDirService;
import com.jeeplus.modules.train.service.LessonTimeService;
import com.jeeplus.modules.train.service.ProgramActivityService;
import com.jeeplus.modules.train.service.ProgramFocusService;
import com.jeeplus.modules.train.service.StudyActivityService;
import com.jeeplus.modules.train.service.TrainProgramService;
import com.jeeplus.modules.train.service.UserActivityRegService;
import com.jeeplus.modules.train.service.UserActivityService;
import com.yfhl.commons.web.BaseController;
/***
 * 学习活动Action
 * @author Panjp
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/")
public class TstudyActivityController extends BaseController{
	@Autowired
	private StudyActivityTeacherService iStudyActivityService;
	@Autowired
	private StudyActivityService studyActivityService;

	@Autowired
	private ProgramActivityService programActivityService;
	
	@Autowired
	private TrainProgramService trainProgramService;
	

	@Autowired
	private ProgramFocusService programFocusService;
	
	@Autowired
	private UserActivityRegService userActivityRegService;
	
	@Autowired
	private LessonTimeService lessonTimeService;
	
	@Autowired
	private ActivityDirService activityDirService;
	
	@Autowired 
	UserActivityService userActivityService;
	
	@Autowired
	SystemService systemService;
	/**
	 * 学习活动计划查看
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("studyActivityList")
	public String studyActivityList(TrainProgram trainProgram, HttpServletRequest request, HttpServletResponse response, Model model) {
		trainProgram.setStatus("0");//审核通过
		Page<TrainProgram> page = trainProgramService.findPage(new Page<TrainProgram>(request, response), trainProgram); 
		model.addAttribute("page", page);
		return "/studyActive/studyActivityList";
	}
	/**
	 * 关注学习活动计划
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("gzActivityList")
	public String gzActivityList(TrainProgram trainProgram, HttpServletRequest request, HttpServletResponse response, Model model) {
		trainProgram.setStatus("0");//审核通过
		Page<TrainProgram> page = trainProgramService.findPage(new Page<TrainProgram>(request, response), trainProgram); 
		model.addAttribute("page", page);
		return "/studyActive/gzActivityList";
	}
	/**
	 * 学习活动计划查看
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("viewStudyActivityList")
	public String viewStudyActivityList(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		TrainProgram trainProgram = trainProgramService.get(id);
		List<ProgramActivity> proGramList = new ArrayList<ProgramActivity>();
		if(null !=id && !"".equals(id)){
			ProgramActivity obj = new ProgramActivity();
			obj.setTrainProgramId(id);
			obj.setUserFllow(UserUtils.getUser().getId());
			proGramList = programActivityService.findGzList(obj);
			trainProgram.setProGramList(proGramList);
		}
		model.addAttribute("trainProgram", trainProgram);
		return "/studyActive/viewStudyActivityList";
	}
	/**
	 *关注学习计划
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("gzStudyActivity")
	public void gzStudyActivity(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		String str = "0";
		try {
			ProgramFocus programFocus = new ProgramFocus();
			programFocus.setIsNewRecord(true);
			programFocus.setUserId(UserUtils.getUser().getId());
			programFocus.setProgramActivityId(id);
			programFocus.setId(IdGen.uuid());
			programFocusService.save(programFocus);
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
	 *取消关注学习计划
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("qxGzStudyActivity")
	public void qxGzStudyActivity(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		String str = "0";
		try {
			ProgramFocus programFocus = new ProgramFocus();
			programFocus.setUserId(UserUtils.getUser().getId());
			programFocus.setProgramActivityId(id);
			ProgramFocus temp = programFocusService.get(programFocus);
			if (temp != null) {
				programFocusService.delete(temp);
				str = "1";
			}
			
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
	 *教师删除活动
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherDelStuActivity")
	public void teacherDelStuActivity(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		String str = "0";
		try {
			studyActivityService.delete(studyActivityService.get(id));
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
	 *教师学员管理添加学员
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherAddStuActivityUser")
	public void teacherAddStuActivityUser(HttpServletRequest request, HttpServletResponse response, Model model,String ids,String userActivityId) {
		String str = "0";
		try {
			if(null!=ids && !"".equals(ids.trim())){
				String id[] = ids.split(",");
				for (int i = 0; i < id.length; i++) {
					UserActivity userActivity = new UserActivity();
					userActivity.setId(IdGen.uuid());
					userActivity.setType("1");
					userActivity.setUserId(id[i]);
					userActivity.setActivityId(userActivityId);
					userActivity.setIsNewRecord(true);
					userActivityService.save(userActivity);
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
	 *教师学员管理添加学员（直接添加）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherAddStuActivityUserInManager")
	public void teacherAddStuActivityUserInManager(HttpServletRequest request, HttpServletResponse response, Model model,String ids,String userActivityId) {
		String str = "0";
		try {
			if(null!=ids && !"".equals(ids.trim())){
				String id[] = ids.split(",");
				//删除
				for (int i = 0; i < id.length; i++) {
					//删除UserActivity表
					UserActivity userActivity = new UserActivity();
					userActivity.setUserId(id[i]);
					userActivity.setActivityId(userActivityId);
					userActivityService.deleteUserActivityByUserIdAndActivityId(userActivity);
					
					//删除
					UserActivityReg userActivityReg = new UserActivityReg();
					User createBy = new User();
					createBy.setId(id[i]);
					userActivityReg.setCreateBy(createBy);
					userActivityReg.setActivityId(userActivityId);
					userActivityRegService.deleteByUserIdAndActivityId(userActivityReg);
				}
				
				//添加
				for (int i = 0; i < id.length; i++) {
					UserActivity userActivity = new UserActivity();
					userActivity.setId(IdGen.uuid());
					userActivity.setType("1");
					userActivity.setUserId(id[i]);
					userActivity.setActivityId(userActivityId);
					userActivity.setIsNewRecord(true);
					userActivityService.save(userActivity);
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
	@RequestMapping("teacherDelStuActivityUser")
	public void teacherDelStuActivityUser(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		String str = "0";
		try {
			userActivityService.deleteUserActivityById(id);
			userActivityRegService.deleteById(id);
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
	/**
	 * 学习活动列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherSstudyActivity")
	public String teacherSstudyActivity(StudyActivity studyActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		studyActivity.setResourceStatus("0");
		Page<StudyActivity> page = studyActivityService.findTeacherStudyActivityList(new Page<StudyActivity>(request, response), studyActivity); 
		model.addAttribute("page", page);
		return "/studyActive/teacherStudyActivityList";
	}
	
	/**
	 * 我的学习活动列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping("myTeacherStudyActivity")
	public String myTeacherStudyActivity(StudyActivity studyActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudyActivity> page = studyActivityService.myTeacherStudyActivity(new Page<StudyActivity>(request, response), studyActivity); 
		model.addAttribute("page", page);
		return "/studyActive/myTeacherStudyActivityList";
	}
	/**
	 *我的学习活动查看
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormviewStudyActivity")
	public String teacherFormviewStudyActivity(String id , Model model){
		StudyActivity studyActivity = studyActivityService.get(id);
		model.addAttribute("studyActivity", studyActivity);
		return "/studyActive/teacherStudyActivityView";
	}
	
	/**
	 * 全部学习活动查看
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherFormviewALlActivity")
	public String teacherFormviewALlActivity(String id , Model model){
		StudyActivity studyActivity = studyActivityService.get(id);
		model.addAttribute("studyActivity", studyActivity);
		return "/studyActive/teacherFormviewALlActivity";
	}
	
	/**
	 * 教师端学员审批
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"techerSpStudyUser"})
	public String techerSpStudyUser(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = new User();
		user.setOfficeId(activityId);
		Page<User> page = userActivityRegService.findBmUserList(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		model.addAttribute("activityid", activityId);
		return "/studyActive/techerSpStudyUser";
	}
	
	/**
	 * 教师端学员展示
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"listbyactivityid"})
	public String listbyactivityid(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = new User();
		user.setOfficeId(activityId);
		Page<User> page = userActivityRegService.findUserList(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		model.addAttribute("activityid", activityId);
		return "/studyActive/userActivityRegList";
	}
	/**
	 * 教师端学员展示（全部活动查看）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"listAllbyactivityid"})
	public String listAllbyactivityid(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = new User();
		user.setOfficeId(activityId);
		Page<User> page = userActivityRegService.findUserList(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		model.addAttribute("activityid", activityId);
		return "/studyActive/listAllbyactivityid";
	}
	/**
	 * 教师端查看目录
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"teacherActivityDirListview"})
	public String teacherActivityDirListview(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		ActivityDir activityDir = new ActivityDir();
		activityDir.setActivityId(activityId);
		activityDir.setParentId("0");
		Page<ActivityDir> page = activityDirService.findPage(new Page<ActivityDir>(request, response), activityDir); 
		List<ActivityDir> tempActivityDirList = page.getList();
		
		List<java.util.HashMap<String, List<ActivityDir>>> resturnList =  new ArrayList<java.util.HashMap<String,List<ActivityDir>>>();
		Iterator<ActivityDir> itParent = tempActivityDirList.iterator();
		while(itParent.hasNext()){
			ActivityDir tmp = itParent.next();
			List<ActivityDir> listParentActivityDir = new ArrayList<ActivityDir>();
			listParentActivityDir.add(tmp);
			
			ActivityDir temp = new ActivityDir();
			temp.setActivityId(tmp.getActivityId());
			temp.setParentId(tmp.getId());
			List<ActivityDir> listSubActivityDir = activityDirService.findList(temp) ;
			
			java.util.HashMap<String, List<ActivityDir>> hashMap = new java.util.HashMap<String, List<ActivityDir>>();
			hashMap.put("parent", listParentActivityDir);
			hashMap.put("son", listSubActivityDir);
			resturnList.add(hashMap);
		}
		
		model.addAttribute("resturnList", resturnList);
		model.addAttribute("activityId", activityDir.getActivityId());
		model.addAttribute("page", page);
		return "/studyActive/teacherActivityDirListview";
	}
	
	/**
	 * 学习活动成绩详情查看
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"studyActiceScoreView"})
	public String studyActiceScoreView(@RequestParam(required=false) String activityId,String userId, HttpServletRequest request, HttpServletResponse response, Model model) {
		ActivityDir activityDir = new ActivityDir();
		activityDir.setActivityId(activityId);
		activityDir.setParentId("0");
		activityDir.setUserId(userId);
		Page<ActivityDir> page = activityDirService.findListUserScorePage(new Page<ActivityDir>(request, response), activityDir); 
		List<ActivityDir> tempActivityDirList = page.getList();
		
		List<java.util.HashMap<String, List<ActivityDir>>> resturnList =  new ArrayList<java.util.HashMap<String,List<ActivityDir>>>();
		Iterator<ActivityDir> itParent = tempActivityDirList.iterator();
		while(itParent.hasNext()){
			ActivityDir tmp = itParent.next();
			List<ActivityDir> listParentActivityDir = new ArrayList<ActivityDir>();
			listParentActivityDir.add(tmp);
			
			ActivityDir temp = new ActivityDir();
			temp.setActivityId(tmp.getActivityId());
			temp.setParentId(tmp.getId());
			temp.setUserId(userId);
			List<ActivityDir> listSubActivityDir = activityDirService.findListUserScore(temp) ;
			
			java.util.HashMap<String, List<ActivityDir>> hashMap = new java.util.HashMap<String, List<ActivityDir>>();
			hashMap.put("parent", listParentActivityDir);
			hashMap.put("son", listSubActivityDir);
			resturnList.add(hashMap);
		}
		
		model.addAttribute("resturnList", resturnList);
		model.addAttribute("activityId", activityDir.getActivityId());
		model.addAttribute("page", page);
		return "/studyActive/studyActiceScoreView";
	}
	
	/**
	 * 教师端查看目录(所有活动)
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"teacherAllActivityDirListview"})
	public String teacherAllActivityDirListview(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		ActivityDir activityDir = new ActivityDir();
		activityDir.setActivityId(activityId);
		activityDir.setParentId("0");
		Page<ActivityDir> page = activityDirService.findPage(new Page<ActivityDir>(request, response), activityDir); 
		List<ActivityDir> tempActivityDirList = page.getList();
		
		List<java.util.HashMap<String, List<ActivityDir>>> resturnList =  new ArrayList<java.util.HashMap<String,List<ActivityDir>>>();
		Iterator<ActivityDir> itParent = tempActivityDirList.iterator();
		while(itParent.hasNext()){
			ActivityDir tmp = itParent.next();
			List<ActivityDir> listParentActivityDir = new ArrayList<ActivityDir>();
			listParentActivityDir.add(tmp);
			
			ActivityDir temp = new ActivityDir();
			temp.setActivityId(tmp.getActivityId());
			temp.setParentId(tmp.getId());
			List<ActivityDir> listSubActivityDir = activityDirService.findList(temp) ;
			
			java.util.HashMap<String, List<ActivityDir>> hashMap = new java.util.HashMap<String, List<ActivityDir>>();
			hashMap.put("parent", listParentActivityDir);
			hashMap.put("son", listSubActivityDir);
			resturnList.add(hashMap);
		}
		
		model.addAttribute("resturnList", resturnList);
		model.addAttribute("activityId", activityDir.getActivityId());
		model.addAttribute("page", page);
		return "/studyActive/teacherAllActivityDirListview";
	}
	/**
	 * 教师端成绩展示
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"teacherListViewScore"})
	public String teacherListViewScore(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		ActivityDir activityDir = new ActivityDir();
		activityDir.setActivityId(activityId);
		activityDir.setParentId("0");
		Page<ActivityDir> page = activityDirService.findPage(new Page<ActivityDir>(request, response), activityDir); 
		List<ActivityDir> tempActivityDirList = page.getList();
		
		List<java.util.HashMap<String, List<ActivityDir>>> resturnList =  new ArrayList<java.util.HashMap<String,List<ActivityDir>>>();
		Iterator<ActivityDir> itParent = tempActivityDirList.iterator();
		while(itParent.hasNext()){
			ActivityDir tmp = itParent.next();
			List<ActivityDir> listParentActivityDir = new ArrayList<ActivityDir>();
			listParentActivityDir.add(tmp);
			
			ActivityDir temp = new ActivityDir();
			temp.setActivityId(tmp.getActivityId());
			temp.setParentId(tmp.getId());
			List<ActivityDir> listSubActivityDir = activityDirService.findList(temp) ;
			
			java.util.HashMap<String, List<ActivityDir>> hashMap = new java.util.HashMap<String, List<ActivityDir>>();
			hashMap.put("parent", listParentActivityDir);
			hashMap.put("son", listSubActivityDir);
			resturnList.add(hashMap);
		}
		
		model.addAttribute("resturnList", resturnList);
		model.addAttribute("activityId", activityDir.getActivityId());
		model.addAttribute("page", page);
		return "/studyActive/teacherListViewScore";
	}
	/**
	 * 教师端成绩展示(全部活动查看)
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"teacherAllListViewScore"})
	public String teacherAllListViewScore(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		ActivityDir activityDir = new ActivityDir();
		activityDir.setActivityId(activityId);
		activityDir.setParentId("0");
		Page<ActivityDir> page = activityDirService.findPage(new Page<ActivityDir>(request, response), activityDir); 
		List<ActivityDir> tempActivityDirList = page.getList();
		
		List<java.util.HashMap<String, List<ActivityDir>>> resturnList =  new ArrayList<java.util.HashMap<String,List<ActivityDir>>>();
		Iterator<ActivityDir> itParent = tempActivityDirList.iterator();
		while(itParent.hasNext()){
			ActivityDir tmp = itParent.next();
			List<ActivityDir> listParentActivityDir = new ArrayList<ActivityDir>();
			listParentActivityDir.add(tmp);
			
			ActivityDir temp = new ActivityDir();
			temp.setActivityId(tmp.getActivityId());
			temp.setParentId(tmp.getId());
			List<ActivityDir> listSubActivityDir = activityDirService.findList(temp) ;
			
			java.util.HashMap<String, List<ActivityDir>> hashMap = new java.util.HashMap<String, List<ActivityDir>>();
			hashMap.put("parent", listParentActivityDir);
			hashMap.put("son", listSubActivityDir);
			resturnList.add(hashMap);
		}
		
		model.addAttribute("resturnList", resturnList);
		model.addAttribute("activityId", activityDir.getActivityId());
		model.addAttribute("page", page);
		return "/studyActive/teacherAllListViewScore";
	}
	
	/**
	 * 教师端查看时间
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"teacherLessonTimeList"})
	public String teacherLessonTimeList(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		LessonTime lessonTime = new LessonTime();
		lessonTime.setStudyId(activityId);
		Page<LessonTime> page = lessonTimeService.findPage(new Page<LessonTime>(request, response,-1), lessonTime); 
		//增加章节信息
		List<LessonTime> tempList = page.getList();
		Iterator<LessonTime> itsLessonTime = tempList.iterator();
		while(itsLessonTime.hasNext()){
			LessonTime tempLessonTime = itsLessonTime.next();
			ActivityDir dir = new ActivityDir();
			dir.setLessionTimeId(tempLessonTime.getId());
			dir.setParentId("0");
			List<ActivityDir> activityDirList =activityDirService.getListForLessionId(dir);
			if(null!=activityDirList && !activityDirList.isEmpty()){
				for (int i = 0; i < activityDirList.size(); i++) {
					ActivityDir indexDir = activityDirList.get(i);
					ActivityDir dirT = new ActivityDir();
					dirT.setLessionTimeId(tempLessonTime.getId());
					dirT.setParentId(indexDir.getId());
					List<ActivityDir> activityDirIndex =activityDirService.getListForLessionId(dirT);
					indexDir.setListActivityDir(activityDirIndex);
				}
			}else{
				dir.setParentId("");
				activityDirList = activityDirService.getListForLessionId(dir);
			}
			tempLessonTime.setListActivityDir(activityDirList);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("lessonTime", lessonTime);
		return "/studyActive/teacherLessonTimeList";
	}
	/**
	 * 教师端查看时间（所有活动）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"teacherAllLessonTimeList"})
	public String teacherAllLessonTimeList(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		LessonTime lessonTime = new LessonTime();
		lessonTime.setStudyId(activityId);
		Page<LessonTime> page = lessonTimeService.findPage(new Page<LessonTime>(request, response,-1), lessonTime); 
		//增加章节信息
		List<LessonTime> tempList = page.getList();
		Iterator<LessonTime> itsLessonTime = tempList.iterator();
		while(itsLessonTime.hasNext()){
			LessonTime tempLessonTime = itsLessonTime.next();
			ActivityDir dir = new ActivityDir();
			dir.setLessionTimeId(tempLessonTime.getId());
			dir.setParentId("0");
			List<ActivityDir> activityDirList =activityDirService.getListForLessionId(dir);
			if(null!=activityDirList && !activityDirList.isEmpty()){
				for (int i = 0; i < activityDirList.size(); i++) {
					ActivityDir indexDir = activityDirList.get(i);
					ActivityDir dirT = new ActivityDir();
					dirT.setLessionTimeId(tempLessonTime.getId());
					dirT.setParentId(indexDir.getId());
					List<ActivityDir> activityDirIndex =activityDirService.getListForLessionId(dirT);
					indexDir.setListActivityDir(activityDirIndex);
				}
			}else{
				dir.setParentId("");
				activityDirList = activityDirService.getListForLessionId(dir);
			}
			tempLessonTime.setListActivityDir(activityDirList);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("lessonTime", lessonTime);
		return "/studyActive/teacherAllLessonTimeList";
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
	@RequestMapping(value = {"findAddStudyUserList"})
	public String findAddStudyUserList(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findAddStudyUserList(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        model.addAttribute("userActivityId", user.getUserActivityId());
		return "/studyActive/findAddStudyUserList";
	}
	
	/**
	 * 教师端学员展示导出
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"listbyactivityidExport"})
	public String listbyactivityidExport(@RequestParam(required=false) String activityId, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes, Model model) {
		try {
			User user = new User();
			user.setOfficeId(activityId);
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = userActivityRegService.findUserList(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response,request, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "/studyActive/userActivityRegList";
	}
}
