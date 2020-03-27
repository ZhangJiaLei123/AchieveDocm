/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.LessionImportUser;
import com.jeeplus.modules.train.entity.UserActivity;

/**
 * 学员活动DAO接口
 * @author panjp
 * @version 2017-03-26
 */
@MyBatisDao
public interface UserActivityDao extends CrudDao<UserActivity> {

	public void deleteUserActivityByActivId(Map map);
	public void deleteUserActivityById(@Param("id")String id);
	/***
	 * 批量添加数据
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月6日 下午5:06:58
	  * @param list
	 */
	public void saveBatch(List<UserActivity> list);
	/***
	 * 判断该学员是否已经在必填学员中存在了
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月16日 上午10:10:31
	  * @param map
	  * @return
	 */
	UserActivity getIsBtExists(UserActivity userActivity);
	
	public void saveActiveUser(LessionImportUser lessionUser);
	
	public void deleteActiveUser(LessionImportUser lessionUser);
	
	public List<UserActivity> findListByEndTime(UserActivity userActivity);
	
	public void deleteUserActivityByUserIdAndActivityId(UserActivity userActivity);
	
}