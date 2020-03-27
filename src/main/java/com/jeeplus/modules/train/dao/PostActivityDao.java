/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.PostActivity;

/**
 * 岗位活动DAO接口
 * @author 岗位活动
 * @version 2017-03-26
 */
@MyBatisDao
public interface PostActivityDao extends CrudDao<PostActivity> {

	public void deletePostActivityByActivId(@Param("activId")String activId,@Param("type")String type);
	
	public List<PostActivity> showPostActiveList(PostActivity postActivity);
}