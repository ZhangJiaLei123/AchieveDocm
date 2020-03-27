/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.TrainProgram;

/**
 * 培训计划DAO接口
 * @author panjp
 * @version 2017-03-24
 */
@MyBatisDao
public interface TrainProgramDao extends CrudDao<TrainProgram> {
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(TrainProgram entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(TrainProgram entity);
	
	public void updateRelease(String id);
	
}