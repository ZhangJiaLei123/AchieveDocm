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
import com.jeeplus.modules.course.entity.UserCourseReg;
import com.jeeplus.modules.course.dao.UserCourseRegDao;

/**
 * 课程报名表Service
 * @author wsp
 * @version 2017-03-30
 */
@Service
@Transactional(readOnly = true)
public class UserCourseRegService extends CrudService<UserCourseRegDao, UserCourseReg> {

	@Autowired
	UserCourseRegDao userCourseRegDao;
	
	public UserCourseReg get(String id) {
		return super.get(id);
	}
	public UserCourseReg getById(String id) {
		return userCourseRegDao.getById(id);
	}
	
	public List<UserCourseReg> findList(UserCourseReg userCourseReg) {
		return super.findList(userCourseReg);
	}
	
	public Page<UserCourseReg> findPage(Page<UserCourseReg> page, UserCourseReg userCourseReg) {
		return super.findPage(page, userCourseReg);
	}
	
	@Transactional(readOnly = false)
	public void save(UserCourseReg userCourseReg) {
		super.save(userCourseReg);
	}
	@Transactional(readOnly = false)
	public void saveForSystem(UserCourseReg userCourseReg) {
		userCourseRegDao.saveForSystem(userCourseReg);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserCourseReg userCourseReg) {
		super.delete(userCourseReg);
	}
	@Transactional(readOnly = false)
	public void deleteById(String id) {
		userCourseRegDao.deleteById(id);
	}
	@Transactional(readOnly = false)
	public void deleteByUserIdAndCourseId(UserCourseReg userCourseReg) {
		userCourseRegDao.deleteByUserIdAndCourseId(userCourseReg);
	}
	
	
	
}