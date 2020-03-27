/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.train.entity.UserActivityReg;

/**
 * 活动报名审核表DAO接口
 * @author wsp
 * @version 2017-04-02
 */
@MyBatisDao
public interface UserActivityRegDao extends CrudDao<UserActivityReg> {

	public List<User> findUserList(User user);
	
	public List<User> findUserListForJgSystem(User user);
	
	public List<User> findBmUserList(User user);
	public void deleteById(@Param("id")String id);
	
	public void saveForSystem(UserActivityReg userActivityReg);
	
	//根据用户id和activityid，获取UserActivityReg信息
	public UserActivityReg getByUserIdAndActivityId(UserActivityReg userActivityReg);
	
	public void deleteByUserIdAndActivityId(UserActivityReg userActivityReg);
	
}