/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.dao;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.course.entity.UserCourseReg;

/**
 * 课程报名表DAO接口
 * @author wsp
 * @version 2017-03-30
 */
@MyBatisDao
public interface UserCourseRegDao extends CrudDao<UserCourseReg> {

	public void deleteById(@Param("id")String id);
	
	public UserCourseReg getById(@Param("id")String id);
	
	public void saveForSystem(UserCourseReg userCourseReg);
	
	public void deleteByUserIdAndCourseId(UserCourseReg userCourseReg);
}