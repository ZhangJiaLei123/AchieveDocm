/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.course.entity.PostCourse;
import com.jeeplus.modules.course.dao.PostCourseDao;

/**
 * 岗位课程Service
 * @author panjp
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class PostCourseService extends CrudService<PostCourseDao, PostCourse> {
	@Autowired
	PostCourseDao postCourseDao;
	public PostCourse get(String id) {
		return super.get(id);
	}
	
	public List<PostCourse> findList(PostCourse postCourse) {
		return super.findList(postCourse);
	}
	
	public Page<PostCourse> findPage(Page<PostCourse> page, PostCourse postCourse) {
		return super.findPage(page, postCourse);
	}
	
	@Transactional(readOnly = false)
	public void save(PostCourse postCourse) {
		super.save(postCourse);
	}
	
	@Transactional(readOnly = false)
	public void delete(PostCourse postCourse) {
		super.delete(postCourse);
	}
	
	public List<PostCourse> showPpostCourseList(PostCourse postCourse) {
		return postCourseDao.findPostCourse(postCourse);
	}
	
	@Transactional(readOnly = false)
	public void deletePostCourseByCourseId(String courseId,String type) {
		postCourseDao.deletePostCourseByCourseId(courseId,type);
	}
	
	
	
}