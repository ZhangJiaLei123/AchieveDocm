package com.jeeplus.modules.sys.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.course.entity.CourseInfo;
import com.jeeplus.modules.course.service.CourseInfoService;
import com.jeeplus.modules.record.service.UserStudyRecordAllTimeService;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.service.MesanInfoService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.train.entity.StudyActivity;
import com.jeeplus.modules.train.service.StudyActivityService;

/**
 * 系统统计Controller
 * @author 
 * @version 2016-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/statis")
public class StatisticsController extends BaseController {
	@Autowired
	private MesanInfoService mesanInfoService;
	
	@Autowired
	private UserStudyRecordAllTimeService userStudyRecordAllTimeService;
	
	@Autowired
	private CourseInfoService courseInfoService;
	
	@Autowired
	private StudyActivityService studyActivityService;
	
	
	
	/**
	 *资料评分，下载次数，评论统计
	 */ 
	@RequestMapping(value = {"statisMesanList"})
	public String statisMesanList(MesanInfo mesanInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MesanInfo> page = mesanInfoService.statisMesanList(new Page<MesanInfo>(request, response), mesanInfo); 
		model.addAttribute("page", page);
		return "modules/sys/statisMesanList";
	}
	
	/**
	 *统计学员在线时长
	 */ 
	@RequestMapping(value = {"statisStudentOnlineTime"})
	public String statisStudentOnlineTime(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setRoleEnName(UserUtils.USER_STUDENT_ENNAME);
		Page<User> page = userStudyRecordAllTimeService.statisStudentOnlineTime(new Page<User>(request, response), user); 
		model.addAttribute("page", page);
		return "modules/sys/statisUserList";
	}
	/**
	 *学习课程时长详情
	 */ 
	@RequestMapping(value = {"courseStudyTime"})
	public String courseStudyTime(CourseInfo courseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CourseInfo> page = courseInfoService.courseStudyTime(new Page<CourseInfo>(request, response), courseInfo); 
		model.addAttribute("page", page);
		return "modules/sys/courseStudyTime";
	}
	/**
	 *学习活动学习时长详情
	 */ 
	@RequestMapping(value = {"activeStudyTime"})
	public String activeStudyTime(StudyActivity studyActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StudyActivity> page = studyActivityService.activeStudyTime(new Page<StudyActivity>(request, response), studyActivity); 
		model.addAttribute("page", page);
		return "modules/sys/activeStudyTime";
	}
	/**
	 *浏览资料学习活动详情
	 */ 
	@RequestMapping(value = {"mesanStudyTime"})
	public String mesanStudyTime(MesanInfo mesanInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MesanInfo> page = mesanInfoService.mesanStudyTime(new Page<MesanInfo>(request, response), mesanInfo); 
		model.addAttribute("page", page);
		return "modules/sys/mesanStudyTime";
	}
}