/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.resource.entity.ResourceDir;
import com.jeeplus.modules.resource.dao.ResourceDirDao;

/**
 * 资源目录Service
 * @author panjp
 * @version 2017-03-19
 */
@Service
@Transactional(readOnly = true)
public class ResourceDirService extends CrudService<ResourceDirDao, ResourceDir> {

	public ResourceDir get(String id) {
		return super.get(id);
	}
	
	public List<ResourceDir> findList(ResourceDir resourceDir) {
		return super.findList(resourceDir);
	}
	
	public Page<ResourceDir> findPage(Page<ResourceDir> page, ResourceDir resourceDir) {
		return super.findPage(page, resourceDir);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceDir resourceDir) {
		super.save(resourceDir);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceDir resourceDir) {
		super.delete(resourceDir);
	}
	
	
	
	
}