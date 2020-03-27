/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.question.entity.QuestionResult;

/**
 * 用户答题信息表DAO接口
 * @author wsp
 * @version 2017-03-26
 */
@MyBatisDao
public interface QuestionResultDao extends CrudDao<QuestionResult> {
	public List<QuestionResult> findPageGroupRid(QuestionResult questionResult);
	
}