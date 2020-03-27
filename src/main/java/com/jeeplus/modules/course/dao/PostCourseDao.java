/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.course.entity.PostCourse;

/**
 * 岗位课程DAO接口
 * @author panjp
 * @version 2017-03-24
 */
@MyBatisDao
public interface PostCourseDao extends CrudDao<PostCourse> {

	public List<PostCourse>findPostCourse(PostCourse postCourse);
	
	public void deletePostCourseByCourseId(@Param("courseId")String courseId,@Param("type")String type);
}