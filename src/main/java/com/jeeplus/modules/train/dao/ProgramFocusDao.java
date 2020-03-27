/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.ProgramFocus;

/**
 * 用户关注活动计划表DAO接口
 * @author wsp
 * @version 2017-04-08
 */
@MyBatisDao
public interface ProgramFocusDao extends CrudDao<ProgramFocus> {

	
}