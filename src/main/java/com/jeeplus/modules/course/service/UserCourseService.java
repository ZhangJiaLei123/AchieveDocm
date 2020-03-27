/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.course.entity.UserCourse;
import com.jeeplus.modules.course.dao.UserCourseDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.train.entity.UserActivity;

/**
 * 学员课程关系表Service
 * @author panjp
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class UserCourseService extends CrudService<UserCourseDao, UserCourse> {
	@Autowired
	UserCourseDao userCoursedao;
	
	public UserCourse get(String id) {
		return super.get(id);
	}
	
	public List<UserCourse> findList(UserCourse userCourse) {
		return super.findList(userCourse);
	}
	
	public Page<UserCourse> findPage(Page<UserCourse> page, UserCourse userCourse) {
		return super.findPage(page, userCourse);
	}
	
	@Transactional(readOnly = false)
	public void save(UserCourse userCourse) {
		super.save(userCourse);
	}
	
	@Transactional(readOnly = false)
	public void saveBatch(List<UserCourse> lists) {
		dao.saveBatch(lists);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserCourse userCourse) {
		super.delete(userCourse);
	}
	@Transactional(readOnly = false)
	public void deleteById(String id) {
		userCoursedao.deleteById(id);
	}
	public Page<UserCourse> showUserCourseList(Page<UserCourse> page, UserCourse userCourse) {
		userCourse.setPage(page);
		page.setList(userCoursedao.findUserCourse(userCourse));
		return page;
	}
	public Page<UserCourse> findUserCourseInfoList(Page<UserCourse> page, UserCourse userCourse) {
		userCourse.setPage(page);
		page.setList(userCoursedao.findUserCourseInfoList(userCourse));
		return page;
	}
	
	public Page<UserCourse> findOfficeListMap(Page<UserCourse> page, UserCourse userCourse){
		userCourse.setPage(page);
		page.setList(userCoursedao.findOfficeListMap(userCourse));
		return page;
	}

	public int findUserOfficeTypeCount(String courseId,String type){
		return userCoursedao.findUserOfficeTypeCount(courseId,type);
	}
	
	public int findUserCount(String courseId,String type){
		return userCoursedao.findUserCount(courseId,type);
	}
	
	public List<Map> findUserByOfficeId(String  officeId){
		return userCoursedao.findUserByOfficeId(officeId);
	}
	
	@Transactional(readOnly = false)
	public void deleteUserCourseByCourID(Map map) {
		userCoursedao.deleteUserCourseByCourID(map);
	}
	
	public Page<User> findUserList(Page<User> page, User user) {
		user.setPage(page);
		page.setList(userCoursedao.findUserList(user));
		return page;
	}
	
	public Page<User> findUserListForJgSystem(Page<User> page, User user) {
		user.setPage(page);
		page.setList(userCoursedao.findUserListForJgSystem(user));
		return page;
	}
	public Page<User> findBmUserList(Page<User> page, User user) {
		user.setPage(page);
		page.setList(userCoursedao.findBmUserList(user));
		return page;
	}
	
	//根据课程id，得到课程人数
	public int getTotalCountByCourseId(UserCourse userCourse){
		return userCoursedao.getTotalCountByCourseId(userCourse);
	}
	
	@Transactional(readOnly = false)
	public void deleteByUserIdAndCourseId(UserCourse userCourse) {
		userCoursedao.deleteByUserIdAndCourseId(userCourse);
	}
	
	public List<UserCourse> findListByEndTime(UserCourse userCourse) {
		return userCoursedao.findListByEndTime(userCourse);
	}
}