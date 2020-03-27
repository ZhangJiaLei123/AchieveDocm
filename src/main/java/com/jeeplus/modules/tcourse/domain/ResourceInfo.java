/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tcourse.domain;

import java.util.Date;


/**
 * 资源管理Entity
 * @author panjp
 * @version 2017-03-19
 */
public class ResourceInfo {

	private static final long serialVersionUID = 1L;
	
	private String remarks;	// 备注
	private String createBy;	// 创建者
	private Date createDate;	// 创建日期
	private String updateBy;	// 更新者
	private Date updateDate;	// 更新日期
	private String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	private String id;
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
	private String isSubmit;
	private String isCreateAdmin;//是否管理员添加
	
	public String getIsCreateAdmin() {
		return isCreateAdmin;
	}
	public void setIsCreateAdmin(String isCreateAdmin) {
		this.isCreateAdmin = isCreateAdmin;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceDir() {
		return resourceDir;
	}
	public void setResourceDir(String resourceDir) {
		this.resourceDir = resourceDir;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public String getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsSubmit() {
		return isSubmit;
	}
	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}
	
	
	
}