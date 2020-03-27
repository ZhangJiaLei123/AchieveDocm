/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.question.entity.QuestionInfo;

/**
 * 问卷信息DAO接口
 * @author panjp
 * @version 2017-03-24
 */
@MyBatisDao
public interface QuestionInfoDao extends CrudDao<QuestionInfo> {
	
	public List<QuestionInfo> findSelQuesInfoPage(QuestionInfo questionInfo);
}