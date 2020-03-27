/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.resource.entity.ResourceInfo;
import com.jeeplus.modules.resource.dao.ResourceInfoDao;
import com.jeeplus.modules.sys.utils.FileUtils;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 资源管理Service
 * @author panjp
 * @version 2017-03-19
 */
@Service
@Transactional(readOnly = true)
public class ResourceInfoService extends CrudService<ResourceInfoDao, ResourceInfo> {

	@Autowired
	private ResourceInfoDao resourceInfoDao;
	
	public ResourceInfo get(String id) {
		return super.get(id);
	}
	
	public List<ResourceInfo> findList(ResourceInfo resourceInfo) {
		return super.findList(resourceInfo);
	}
	
	public Page<ResourceInfo> findPage(Page<ResourceInfo> page, ResourceInfo resourceInfo) {
		//BaseService.dataScopeFileForOffice(resourceInfo);
		return super.findPage(page, resourceInfo);
	}
	public Page<ResourceInfo> findSelResourceInfoPage(Page<ResourceInfo> page, ResourceInfo resourceInfo) {
		//BaseService.dataScopeFileForOffice(resourceInfo);
		resourceInfo.setPage(page);
		page.setList(resourceInfoDao.findSelResourceInfoPage(resourceInfo));
		return page;
	}
	
	public Page<ResourceInfo> findMyResourcePage(Page<ResourceInfo> page, ResourceInfo resourceInfo) {
		resourceInfo.setPage(page);
		page.setList(resourceInfoDao.findMyResourcePage(resourceInfo));
		return page;
	}
	
	public Page<ResourceInfo> findResourceInfoIsShSuccess(Page<ResourceInfo> page, ResourceInfo resourceInfo) {
		/*String userRoleEnName = UserUtils.getUser().getRoleEnName();
		if(!UserUtils.USER_SYSTEM_ROLE_ENNAME.equals(userRoleEnName)){//如果是管理员角色则不设置权限
			resourceInfo.setLoginUserId(UserUtils.getUser().getId());
		}*/
		resourceInfo.setPage(page);
		page.setList(resourceInfoDao.findResourceInfoIsShSuccess(resourceInfo));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(ResourceInfo resourceInfo) {
		String url =  resourceInfo.getResourceUrl();
		if(null!=url && !"".equals(url.trim())){
			String fileName = url.substring(url.lastIndexOf(".")+1,url.length()).toUpperCase();
			if("PDF".equals(fileName) || "PPT".equals(fileName) || "DOC".equals(fileName) ||"DOCX".equals(fileName) ||"PPTX".equals(fileName)){//如果是可以在线预览文件则生成预览文件
				String swfStr = resourceInfo.getResourceSwfUrl();
				if(null==swfStr || "".equals(swfStr.trim())){//如果未生成预览文件则生成
					try {
						swfStr = FileUtils.fileToSwf(resourceInfo.getResourceUrl());
						//生成成功后更新文件
						resourceInfo.setResourceSwfUrl(swfStr);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		super.save(resourceInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourceInfo resourceInfo) {
		super.delete(resourceInfo);
	}
	
	@Transactional(readOnly = false)
	public void isPublic(String id,int isPublic) {
		resourceInfoDao.isPublic(id, isPublic);
	}
	
}