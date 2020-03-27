/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.UserPost;

/**
 * 用户岗位关系DAO接口
 * @author panjp
 * @version 2017-03-28
 */
@MyBatisDao
public interface UserPostDao extends CrudDao<UserPost> {
	
public int deleteByUserId(String userId);
public List<Map> findListMap(UserPost userPost);
	
}