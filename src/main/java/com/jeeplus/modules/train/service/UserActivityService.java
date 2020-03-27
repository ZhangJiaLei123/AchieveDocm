/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.entity.LessionImportUser;
import com.jeeplus.modules.train.dao.UserActivityDao;
import com.jeeplus.modules.train.entity.UserActivity;

/**
 * 学员活动Service
 * @author panjp
 * @version 2017-03-26
 */
@Service
@Transactional(readOnly = true)
public class UserActivityService extends CrudService<UserActivityDao, UserActivity> {
	@Autowired
	private UserActivityDao userActivityDao;
	
	public UserActivity get(String id) {
		return super.get(id);
	}
	
	public List<UserActivity> findList(UserActivity userActivity) {
		return super.findList(userActivity);
	}
	
	public Page<UserActivity> findPage(Page<UserActivity> page, UserActivity userActivity) {
		return super.findPage(page, userActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(UserActivity userActivity) {
		super.save(userActivity);
	}
	@Transactional(readOnly = false)
	public void saveActiveUser(LessionImportUser userActivity) {
		userActivityDao.saveActiveUser(userActivity);
	}
	@Transactional(readOnly = false)
	public void deleteActiveUser(LessionImportUser userActivity) {
		userActivityDao.deleteActiveUser(userActivity);
	}
	
	@Transactional(readOnly = false)
	public void saveBatch(List<UserActivity> lists) {
		dao.saveBatch(lists);
	}
	@Transactional(readOnly = false)
	public void delete(UserActivity userActivity) {
		super.delete(userActivity);
	}
	@Transactional(readOnly = false)
	public void deleteUserActivityByActivId(Map map){
		userActivityDao.deleteUserActivityByActivId(map);
	}
	@Transactional(readOnly = false)
	public void deleteUserActivityById(String id){
		userActivityDao.deleteUserActivityById(id);
	}
	public UserActivity get(UserActivity userActivity) {
		return super.get(userActivity);
	}
	
	public UserActivity getIsBtExists(UserActivity userActivity) {
		return dao.getIsBtExists(userActivity);
	}
	
	public List<UserActivity> findListByEndTime(UserActivity userActivity) {
		return userActivityDao.findListByEndTime(userActivity);
	}
	
	@Transactional(readOnly = false)
	public void deleteUserActivityByUserIdAndActivityId(UserActivity userActivity){
		userActivityDao.deleteUserActivityByUserIdAndActivityId(userActivity);
	}
}