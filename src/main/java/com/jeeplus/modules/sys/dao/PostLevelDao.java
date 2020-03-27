/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.PostLevel;

/**
 * 岗位级别DAO接口
 * @author panjp
 * @version 2017-03-19
 */
@MyBatisDao
public interface PostLevelDao extends CrudDao<PostLevel> {
	/***
	 * 查询名称是否存在
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月17日 下午6:22:31
	  * @param name
	  * @return
	 */
	Integer  findNameExists(String name);
	/***
	 * 根据postInfoId查询postLevel
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月19日 下午4:15:42
	  * @param map
	  * @return
	 */
	List<PostLevel> selectedPostLevelList(Map map);
	/***
	 * 
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月19日 下午4:33:08
	  * @param postinfoId
	  * @return
	 */
	int deletePostLevelByPostInfoId(String postInfoId);
	
	int findUserListByPostLevelId(PostLevel postLevel);
}