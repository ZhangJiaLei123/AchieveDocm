/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.record.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.record.entity.UserStudyRecordAllTime;
import com.jeeplus.modules.record.dao.UserStudyRecordAllTimeDao;
import com.jeeplus.modules.sys.entity.User;

/**
 * recordService
 * @author w
 * @version 2017-06-29
 */
@Service
@Transactional(readOnly = true)
public class UserStudyRecordAllTimeService extends CrudService<UserStudyRecordAllTimeDao, UserStudyRecordAllTime> {

	@Autowired
	private UserStudyRecordAllTimeDao userStudyRecordAllTimeDao;
	
	public UserStudyRecordAllTime get(String id) {
		return super.get(id);
	}
	
	public List<UserStudyRecordAllTime> findList(UserStudyRecordAllTime userStudyRecordAllTime) {
		return super.findList(userStudyRecordAllTime);
	}
	
	public Page<UserStudyRecordAllTime> findPage(Page<UserStudyRecordAllTime> page, UserStudyRecordAllTime userStudyRecordAllTime) {
		return super.findPage(page, userStudyRecordAllTime);
	}
	
	@Transactional(readOnly = false)
	public void save(UserStudyRecordAllTime userStudyRecordAllTime) {
		super.save(userStudyRecordAllTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserStudyRecordAllTime userStudyRecordAllTime) {
		super.delete(userStudyRecordAllTime);
	}
	
	public UserStudyRecordAllTime getByUserId(UserStudyRecordAllTime userStudyRecordAllTime) {
		return userStudyRecordAllTimeDao.getByUserId(userStudyRecordAllTime);
	}
	public Page<User> statisStudentOnlineTime(Page<User> page, User user) {
		user.setPage(page);
		page.setList(userStudyRecordAllTimeDao.statisStudentOnlineTime(user));
		return page;
	}
	
}