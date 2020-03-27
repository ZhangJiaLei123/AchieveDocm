/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资料下载记录表Entity
 * @author wsp
 * @version 2017-04-10
 */
public class DownlondRecord extends DataEntity<DownlondRecord> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String mseanInfoId;		// 资料id
	private Date downlondTime;		// 下载时间
	
	public DownlondRecord() {
		super();
	}

	public DownlondRecord(String id){
		super(id);
	}

	@Length(min=0, max=64, message="用户id长度必须介于 0 和 64 之间")
	@ExcelField(title="用户id", align=2, sort=7)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="资料id长度必须介于 0 和 64 之间")
	@ExcelField(title="资料id", align=2, sort=8)
	public String getMseanInfoId() {
		return mseanInfoId;
	}

	public void setMseanInfoId(String mseanInfoId) {
		this.mseanInfoId = mseanInfoId;
	}
	
	public Date getDownlondTime() {
		return downlondTime;
	}

	public void setDownlondTime(Date downlondTime) {
		this.downlondTime = downlondTime;
	}
	
}