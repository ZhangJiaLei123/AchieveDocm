/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.train.entity.UserActivityReg;
import com.jeeplus.modules.train.dao.UserActivityRegDao;

/**
 * 活动报名审核表Service
 * @author wsp
 * @version 2017-04-02
 */
@Service
@Transactional(readOnly = true)
public class UserActivityRegService extends CrudService<UserActivityRegDao, UserActivityReg> {
	@Autowired
	UserActivityRegDao userActivityRegDao;
	
	public UserActivityReg get(String id) {
		return super.get(id);
	}
	
	public List<UserActivityReg> findList(UserActivityReg userActivityReg) {
		return super.findList(userActivityReg);
	}
	
	public Page<UserActivityReg> findPage(Page<UserActivityReg> page, UserActivityReg userActivityReg) {
		return super.findPage(page, userActivityReg);
	}
	
	@Transactional(readOnly = false)
	public void save(UserActivityReg userActivityReg) {
		super.save(userActivityReg);
	}
	@Transactional(readOnly = false)
	public void saveForSystem(UserActivityReg userActivityReg) {
		userActivityRegDao.saveForSystem(userActivityReg);
	}
	@Transactional(readOnly = false)
	public void deleteById(String id) {
		userActivityRegDao.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserActivityReg userActivityReg) {
		super.delete(userActivityReg);
	}
	
	public Page<User> findUserListForJgSystem(Page<User> page, User user) {
		user.setPage(page);
		page.setList(userActivityRegDao.findUserListForJgSystem(user));
		return page;
	}
	
	public Page<User> findUserList(Page<User> page, User user) {
		user.setPage(page);
		page.setList(userActivityRegDao.findUserList(user));
		return page;
	}
	public Page<User> findBmUserList(Page<User> page, User user) {
		user.setPage(page);
		page.setList(userActivityRegDao.findBmUserList(user));
		return page;
	}
	
	//根据用户id和activityid，获取UserActivityReg信息
	public UserActivityReg getByUserIdAndActivityId(UserActivityReg userActivityReg) {
		return userActivityRegDao.getByUserIdAndActivityId(userActivityReg);
	}
	
	@Transactional(readOnly = false)
	public void deleteByUserIdAndActivityId(UserActivityReg userActivityReg) {
		userActivityRegDao.deleteByUserIdAndActivityId(userActivityReg);
	}
	
}