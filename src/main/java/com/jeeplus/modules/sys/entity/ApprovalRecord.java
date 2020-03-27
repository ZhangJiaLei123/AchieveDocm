/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 审批意见Entity
 * @author panjp
 * @version 2017-03-20
 */
public class ApprovalRecord extends DataEntity<ApprovalRecord> {
	
	private static final long serialVersionUID = 1L;
	private String status;		// 审批状态
	private String opinion;		// 审批意见
	private String resourceId;  //记录源ID
	
	public ApprovalRecord() {
		super();
	}

	public ApprovalRecord(String id){
		super(id);
	}

	@Length(min=1, max=2, message="审批状态长度必须介于 1 和 2 之间")
	@ExcelField(title="审批状态", align=2, sort=7)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=300, message="审批意见长度必须介于 1 和 300 之间")
	@ExcelField(title="审批意见", align=2, sort=8)
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
}