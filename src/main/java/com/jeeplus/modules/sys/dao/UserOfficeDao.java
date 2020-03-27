/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.UserOffice;

/**
 * 用户组织关系DAO接口
 * @author panjp
 * @version 2017-03-28
 */
@MyBatisDao
public interface UserOfficeDao extends CrudDao<UserOffice> {

	public List<Map> findAllRole();
	public void deleteUserRole(String userId);
	public void deleteUserOffice(String userId);
	public void saveUserRole(@Param("userId") String userId,@Param("roleId") String roleId);
}