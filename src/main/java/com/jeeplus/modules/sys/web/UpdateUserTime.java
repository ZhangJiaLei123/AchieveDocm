package com.jeeplus.modules.sys.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jeeplus.modules.course.entity.CourseInfo;
import com.jeeplus.modules.course.entity.UserCourse;
import com.jeeplus.modules.course.service.CourseInfoService;
import com.jeeplus.modules.course.service.UserCourseService;
import com.jeeplus.modules.sys.entity.SelectUser;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.train.entity.StudyActivity;
import com.jeeplus.modules.train.entity.UserActivity;
import com.jeeplus.modules.train.service.StudyActivityService;
import com.jeeplus.modules.train.service.UserActivityService;

@Service @Lazy(false)
public class UpdateUserTime{
	@Autowired
	private StudyActivityService studyActivityService;
	
	@Autowired
	private CourseInfoService courseInfoService;
	
	@Autowired
	private UserCourseService userCourseService;

	@Autowired
	private UserActivityService userActivityService;

	@Autowired
	private SystemService systemService;
	
	
	public static final Logger log = Logger.getLogger(UpdateUserTime.class);
	
	//@Scheduled(cron="0 0 2 * * ?")//凌晨两点执行
	@Scheduled(cron="0 0/10 * * * ?")//每十分钟执行一次
	   public void updateUser() {  
		log.info("====================开始更新课程学员信息======================");
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setStuEndTime(new Date());
		//查询所有未结束的课程
		List<CourseInfo> courseList = courseInfoService.findList(courseInfo);
		for (int i = 0; i < courseList.size(); i++) {
			CourseInfo obj = courseList.get(i);
			SelectUser su = new SelectUser();
			su.setCourseId(obj.getId());
			if("0".equals(obj.getIsImportUser())){//必选学员是选择则根据条件更新学员
				su.setSearchType("1");
				List<SelectUser> listUsers = systemService.findListForOfficeAndPostCourse(su);
				List<UserCourse> listAct = new ArrayList<UserCourse>();
				if(null != listUsers && listUsers.size()>0){
					for (int j = 0;j < listUsers.size(); j++) {
						SelectUser selU = listUsers.get(j);
						UserCourse userCourser = new UserCourse();
						userCourser.setCourseId(obj.getId());
						userCourser.setType("1");
						userCourser.setUserId(selU.getId());
						UserCourse selUserCour = userCourseService.get(userCourser);
						if(null == selUserCour){
							UserCourse uc = new UserCourse();
							uc.setId(UUID.randomUUID().toString());
							uc.setUserId(listUsers.get(j).getId());
							uc.setCourseId(obj.getId());
							uc.setType("1");
							uc.setIsNewRecord(true);
							uc.setCreateDate(new Date());
							uc.setUpdateDate(new Date());
							listAct.add(uc);
							//如果已经通过导入途径导入选学了，则删除掉
							Map<String,String>  mapParam = new HashMap<String,String> ();
							mapParam.put("userId", listUsers.get(j).getId());
							mapParam.put("courseId", obj.getId());
							mapParam.put("type", "0");
							userCourseService.deleteUserCourseByCourID(mapParam);
						}
					}
					
					if(null != listAct && listAct.size()>0){
						userCourseService.saveBatch(listAct);
					}
			}
			
			
		}
		if("0".equals(obj.getIsImportUser())){//选学学员是选择则更新选学学员
			su.setSearchType("0");
			List<SelectUser> listUsers = systemService.findListForOfficeAndPostCourse(su);
			if(null != listUsers && listUsers.size()>0){
				for (int j = 0; j < listUsers.size(); j++) {
					UserCourse uc = new UserCourse();
					uc.setUserId(listUsers.get(j).getId());
					uc.setCourseId(obj.getId());
					UserCourse ucTest = userCourseService.get(uc);
					if(null == ucTest){//如果已经在必选学员中了，则不再保存
						uc.setId(UUID.randomUUID().toString());
						uc.setType("0");
						uc.setIsNewRecord(true);
						uc.setCreateDate(new Date());
						uc.setUpdateDate(new Date());
						userCourseService.save(uc);
					}
				}
			}
			
		}
	 }  
		log.info("====================更新课程学员信息结束======================");
		log.info("====================更新学习活动学员信息结束======================");
		StudyActivity studyActivity = new StudyActivity();
		studyActivity.setStudyEndTime(new Date());
		List<StudyActivity> lsStudy = studyActivityService.findList(studyActivity);
		for (int j = 0; j < lsStudy.size(); j++) {
			StudyActivity obj = lsStudy.get(j);
			SelectUser su = new SelectUser();
			su.setActivityId(obj.getId());
			if("0".equals(obj.getIsImportUser())){//必选学员是选择则根据条件更新学员
				su.setSearchType("1");
				List<SelectUser> listUsers = systemService.findListForOfficeAndPost(su);
				List<UserActivity> listAct = new ArrayList<UserActivity>();
				if(null != listUsers && listUsers.size()>0){
					for (int k = 0; k < listUsers.size(); k++) {
						SelectUser selU = listUsers.get(k);
						UserActivity uc = new UserActivity();
						uc.setActivityId(obj.getId());
						uc.setType("1");
						uc.setUserId(selU.getId());
						UserActivity selUserActive = userActivityService.get(uc);
						if(null == selUserActive){
							UserActivity ucNew = new UserActivity();
							ucNew.setId(UUID.randomUUID().toString());
							ucNew.setUserId(listUsers.get(k).getId());
							ucNew.setActivityId(obj.getId());
							ucNew.setType("1");
							ucNew.setCreateDate(new Date());
							ucNew.setUpdateDate(new Date());
							ucNew.setIsNewRecord(true);
							listAct.add(ucNew);
							//如果已经通过导入途径导入选学了，则删除掉
							Map<String,String> mapParam = new HashMap<String,String>();
							mapParam.put("userId", listUsers.get(k).getId());
							mapParam.put("activId", obj.getId());
							mapParam.put("type", "0");
							userActivityService.deleteUserActivityByActivId(mapParam);
						}
					}
					
					if(null != listAct && listAct.size()>0){
						userActivityService.saveBatch(listAct);
					}
			}
			
			
		}
		if("0".equals(obj.getIsImportUser())){//选学学员是选择则更新选学学员
			su.setSearchType("0");
			List<SelectUser> listUsers = systemService.findListForOfficeAndPost(su);
			if(null != listUsers && listUsers.size()>0){
				for (int m = 0; m < listUsers.size(); m++) {
					UserActivity uc = new UserActivity();
					uc.setUserId(listUsers.get(m).getId());
					uc.setActivityId(obj.getId());
					UserActivity ucTest = userActivityService.get(uc);
					if(null == ucTest){//如果已经在必选学员中了，则不再保存
						uc.setId(UUID.randomUUID().toString());
						uc.setType("0");
						uc.setCreateDate(new Date());
						uc.setUpdateDate(new Date());
						uc.setIsNewRecord(true);
						userActivityService.save(uc);
					}
				}
			}
			
		}
	 }
	}
	 

}
