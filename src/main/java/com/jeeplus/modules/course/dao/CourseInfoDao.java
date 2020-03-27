/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.course.entity.CourseInfo;

/**
 * 课程管理DAO接口
 * @author panjp
 * @version 2017-03-21
 */
@MyBatisDao
public interface CourseInfoDao extends CrudDao<CourseInfo> {

	public void fbCourseInfoById(@Param("id")String id);
	
	public List<CourseInfo> findTeacherPage(CourseInfo courseInfo);
	public List<CourseInfo> findMyTeacherPage(CourseInfo courseInfo);
	public List<CourseInfo> courseInfoIsShSuccess(CourseInfo courseInfo);
	
	public List<CourseInfo> findMyCourseInfoPage(CourseInfo courseInfo);
	
	public List<CourseInfo> courseStudyTime(CourseInfo courseInfo);
	
	
}