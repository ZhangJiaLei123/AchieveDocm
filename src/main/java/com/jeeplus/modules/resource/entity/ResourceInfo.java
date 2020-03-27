/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.entity;


import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资源管理Entity
 * @author panjp
 * @version 2017-03-19
 */
public class ResourceInfo extends DataEntity<ResourceInfo> {


	private static final long serialVersionUID = 1L;
	private String name;		// 资源名称
	private String resourceType;		// 资源类别
	private String resourceDir;		// 资源目录
	private String resourceUrl;		// 资源路径
	private String isRelease;		// 是否管理员添加 
	private String isPublic;		// 是否公开
	private String resourceCode;//资源编号
	private String resourceDirName;//资源目录名称
	
	private String resourceStatus;//资源状态
	
	private String resourceSwfUrl;//在线预览路径
	
	private String isSubmit;//教师端是否提交（审核）0：没有 1：已经提交
	
	private String activityDirId;
	private String activityId;
	
	private String isCreateAdmin;//是否管理员添加
	
	public String getIsCreateAdmin() {
		return isCreateAdmin;
	}

	public void setIsCreateAdmin(String isCreateAdmin) {
		this.isCreateAdmin = isCreateAdmin;
	}

	public ResourceInfo() {
		super();
	}

	public ResourceInfo(String id){
		super(id);
	}

	@ExcelField(title="资源名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="资源类别", align=2, sort=8)
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	@ExcelField(title="资源目录", align=2, sort=9)
	public String getResourceDir() {
		return resourceDir;
	}

	public void setResourceDir(String resourceDir) {
		this.resourceDir = resourceDir;
	}
	
	@ExcelField(title="资源路径", align=2, sort=10)
	public String getResourceUrl() {
		return resourceUrl;
	}



	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	@ExcelField(title="是否发布", dictType="resource_release", align=2, sort=11)
	public String getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}
	
	@ExcelField(title="是否公开", dictType="resource_public", align=2, sort=12)
	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getResourceDirName() {
		return resourceDirName;
	}

	public void setResourceDirName(String resourceDirName) {
		this.resourceDirName = resourceDirName;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getResourceSwfUrl() {
		return resourceSwfUrl;
	}

	public void setResourceSwfUrl(String resourceSwfUrl) {
		this.resourceSwfUrl = resourceSwfUrl;
	}

	public String getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	public String getActivityDirId() {
		return activityDirId;
	}

	public void setActivityDirId(String activityDirId) {
		this.activityDirId = activityDirId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
}