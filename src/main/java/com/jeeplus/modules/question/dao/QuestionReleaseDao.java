/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.question.entity.QuestionRelease;

/**
 * 问卷发布DAO接口
 * @author wsp
 * @version 2017-03-26
 */
@MyBatisDao
public interface QuestionReleaseDao extends CrudDao<QuestionRelease> {

	
}