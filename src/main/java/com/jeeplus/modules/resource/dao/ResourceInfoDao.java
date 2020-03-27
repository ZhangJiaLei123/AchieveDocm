/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.resource.entity.ResourceInfo;

/**
 * 资源管理DAO接口
 * @author panjp
 * @version 2017-03-19
 */
@MyBatisDao
public interface ResourceInfoDao extends CrudDao<ResourceInfo> {

	public int isPublic(String id,int isPublic);
	
	public List<ResourceInfo> findResourceInfoIsShSuccess(ResourceInfo resourceInfo);
	public List<ResourceInfo> findMyResourcePage(ResourceInfo resourceInfo);
	public List<ResourceInfo> findSelResourceInfoPage(ResourceInfo resourceInfo);
	
	
}