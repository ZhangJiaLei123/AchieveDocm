/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 活动报名审核表Entity
 * @author wsp
 * @version 2017-04-02
 */
public class UserActivityReg extends DataEntity<UserActivityReg> {
	
	private static final long serialVersionUID = 1L;
	private String activityId;		// 活动id
	private String auditState;		// 审核状态(0:没有通过；1:通过；2：没有通过)
	private String auditTime;		// 审核时间
	private String auditUser;		// 审核人
	private String auditAdvice;		// 审核建议
	private String bmUserId;//用户ID
	
	private String userName;
	
	public String getBmUserId() {
		return bmUserId;
	}

	public void setBmUserId(String bmUserId) {
		this.bmUserId = bmUserId;
	}

	public UserActivityReg() {
		super();
	}

	public UserActivityReg(String id){
		super(id);
	}

	@Length(min=1, max=64, message="活动id长度必须介于 1 和 64 之间")
	@ExcelField(title="活动id", align=2, sort=7)
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=64, message="审核状态(0:没有通过；1:通过；2：没有通过)长度必须介于 0 和 64 之间")
	@ExcelField(title="审核状态(0:没有通过；1:通过；2：没有通过)", align=2, sort=8)
	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	
	@ExcelField(title="审核时间", align=2, sort=9)
	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	
	@Length(min=0, max=64, message="审核人长度必须介于 0 和 64 之间")
	@ExcelField(title="审核人", align=2, sort=10)
	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	
	@Length(min=0, max=255, message="审核建议长度必须介于 0 和 255 之间")
	@ExcelField(title="审核建议", align=2, sort=11)
	public String getAuditAdvice() {
		return auditAdvice;
	}

	public void setAuditAdvice(String auditAdvice) {
		this.auditAdvice = auditAdvice;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}