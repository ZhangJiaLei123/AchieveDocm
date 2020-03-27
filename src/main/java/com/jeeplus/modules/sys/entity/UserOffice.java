/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import java.beans.Transient;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户组织关系Entity
 * @author panjp
 * @version 2017-03-28
 */
public class UserOffice extends DataEntity<UserOffice> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String officeId;		// 组织ID
	private String roleId;//角色ID
	private String officeStr;
	
	private String verifyUrl;//用户如果是内训师，则认证资料url存在
	
	@Transient
	public String getVerifyUrl() {
		return verifyUrl;
	}

	public void setVerifyUrl(String verifyUrl) {
		this.verifyUrl = verifyUrl;
	}

	public UserOffice() {
		super();
	}

	public UserOffice(String id){
		super(id);
	}

	@Length(min=0, max=64, message="用户ID长度必须介于 0 和 64 之间")
	@ExcelField(title="用户ID", align=2, sort=7)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="组织ID长度必须介于 0 和 64 之间")
	@ExcelField(title="组织ID", align=2, sort=8)
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeStr() {
		return officeStr;
	}

	public void setOfficeStr(String officeStr) {
		this.officeStr = officeStr;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}