/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.PostInfo;

/**
 * 岗位管理DAO接口
 * @author panjp
 * @version 2017-03-19
 */
@MyBatisDao
public interface PostInfoDao extends CrudDao<PostInfo> {
	
	public List<PostInfo> findPostList(PostInfo postInfo);
	
	public List<Map> findPostListByType(String postType);
	
	public List<Map> findPostLevelByPost(String postId);
	/***
	 * 查询岗位列表的信息，用于页面信息的展示
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月25日 下午9:40:08
	  * @param map
	  * @return
	 */
	public List<PostInfo> findPostListForSearch(PostInfo postInfo);
	
}