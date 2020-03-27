/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.dao;

import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.course.entity.CourseInfoCondition;

/**
 * 查询条件DAO接口
 * @author ygq
 * @version 2017-04-26
 */
@MyBatisDao
public interface CourseInfoConditionDao extends CrudDao<CourseInfoCondition> {
	//根据查询
	public CourseInfoCondition findActivityConditionByActivityId(Map map);
	
	//
	public int deleteByActivityIdAndType(Map map);
	
}