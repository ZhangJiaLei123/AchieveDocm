/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.course.entity.OfficeCourse;

/**
 * 组织课程关系DAO接口
 * @author panjp
 * @version 2017-03-24
 */
@MyBatisDao
public interface OfficeCourseDao extends CrudDao<OfficeCourse> {
	
	public List<OfficeCourse>findOfficeCourse(OfficeCourse officeCourse);
	
	public List<OfficeCourse>showshCourseOfficeCourseList(OfficeCourse officeCourse);
	
	public void deleteOfficeCourseByCourseId(@Param("courseId")String courseId,@Param("type")String type);

	
}