/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.PostType;

/**
 * 岗位类别DAO接口
 * @author ygq
 * @version 2017-04-18
 */
@MyBatisDao
public interface PostTypeDao extends CrudDao<PostType> {

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
	 * 查询所有的岗位类别
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月18日 下午7:33:29
	  * @return
	 */
	List<PostType> findAllListTypes();
}