/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.DistribuTemp;

/**
 * 活动目录资源分配中间表DAO接口
 * @author wsp
 * @version 2017-04-03
 */
@MyBatisDao
public interface DistribuTempDao extends CrudDao<DistribuTemp> {

	public List<DistribuTemp> findListForDir(DistribuTemp distribuTemp);
	
	public void updaStorce(@Param("id")String id,@Param("score")float score);
}