/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.resource.entity.DownlondRecord;

/**
 * 资料下载记录表DAO接口
 * @author wsp
 * @version 2017-04-10
 */
@MyBatisDao
public interface DownlondRecordDao extends CrudDao<DownlondRecord> {

	
}