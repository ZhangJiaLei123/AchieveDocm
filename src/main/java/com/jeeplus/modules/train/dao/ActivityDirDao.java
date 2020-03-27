/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.ActivityDir;

/**
 * 活动章节目录DAO接口
 * @author wsp
 * @version 2017-04-02
 */
@MyBatisDao
public interface ActivityDirDao extends CrudDao<ActivityDir> {
	
	public List<ActivityDir>getListForLessionId(ActivityDir activityDir);
	
	public List<ActivityDir>getListAllDirForLessionId(ActivityDir activityDir);
	
	public List<ActivityDir>findListUserScore(ActivityDir activityDir);
	

	
}