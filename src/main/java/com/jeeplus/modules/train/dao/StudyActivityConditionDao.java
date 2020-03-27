/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.StudyActivityCondition;

/**
 * 查询条件DAO接口
 * @author ygq
 * @version 2017-04-26
 */
@MyBatisDao
public interface StudyActivityConditionDao extends CrudDao<StudyActivityCondition> {
	//根据查询
	public StudyActivityCondition findActivityConditionByActivityId(Map map);
	
	//
	public int deleteByActivityIdAndType(Map map);
	
}