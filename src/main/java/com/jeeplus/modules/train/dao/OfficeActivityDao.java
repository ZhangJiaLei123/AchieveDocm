/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.OfficeActivity;

/**
 * 组织活动DAO接口
 * @author panjp
 * @version 2017-03-26
 */
@MyBatisDao
public interface OfficeActivityDao extends CrudDao<OfficeActivity> {

	public void deleteOfficeActivityByActivId(@Param("activId") String activId,@Param("type") String type);
	
	public List<OfficeActivity> findOfficeListMap( OfficeActivity officeActivity);
	
	public int findUserOfficeTypeCount(@Param("activId")String activId,@Param("type") String type);
	
	public int findUserCount(@Param("activId")String activId,@Param("type") String type);
}