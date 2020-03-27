/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.resource.entity.MesanDir;

/**
 * 资源目录DAO接口
 * @author panjp
 * @version 2017-03-19
 */
@MyBatisDao
public interface MesanDirDao extends CrudDao<MesanDir> {
	public int updateParentIds(MesanDir resourceDir);
	
}