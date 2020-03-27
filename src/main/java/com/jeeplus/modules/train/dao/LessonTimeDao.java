/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.LessonTime;

/**
 * 授课时间功能DAO接口
 * @author wsp
 * @version 2017-04-03
 */
@MyBatisDao
public interface LessonTimeDao extends CrudDao<LessonTime> {

	
}