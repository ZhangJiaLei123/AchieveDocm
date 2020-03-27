/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.TUserGrade;

/**
 * 用户等级管理DAO接口
 * @author 潘兴武
 * @version 2018-01-04
 */
@MyBatisDao
public interface TUserGradeDao extends CrudDao<TUserGrade> {

	
}