/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.OfficeType;

/**
 * 机构分类DAO接口
 * @author panjp
 * @version 2017-03-25
 */
@MyBatisDao
public interface OfficeTypeDao extends CrudDao<OfficeType> {
	public List<OfficeType> findChildrenOfficeType(OfficeType officeType);
	
	public List<Map> findOfficeByTypeId(String typeId);
	/***
	 * 查询组织类别树
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月22日 上午10:09:56
	  * @return
	 */
	public List<Map> findTreeOffice();
	
}