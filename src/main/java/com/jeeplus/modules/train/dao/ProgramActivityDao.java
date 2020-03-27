/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.ProgramActivity;

/**
 * 计划活动DAO接口
 * @author panjp
 * @version 2017-03-24
 */
@MyBatisDao
public interface ProgramActivityDao extends CrudDao<ProgramActivity> {

	public void deleteTrainProm(String trainProgramId) ;
	
	public List<ProgramActivity> findGzList(ProgramActivity programActivity);
}