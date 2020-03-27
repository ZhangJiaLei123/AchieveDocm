/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.record.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.record.entity.UserStudyRecordAllTime;
import com.jeeplus.modules.sys.entity.User;

/**
 * recordDAO接口
 * @author w
 * @version 2017-06-29
 */
@MyBatisDao
public interface UserStudyRecordAllTimeDao extends CrudDao<UserStudyRecordAllTime> {
	public UserStudyRecordAllTime getByUserId(UserStudyRecordAllTime userStudyRecordAllTime);
	
	public List<User> statisStudentOnlineTime(User user);
}