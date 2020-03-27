/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.record.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 记录Entity
 * @author w
 * @version 2017-06-29
 */
public class UserStudyRecordMaterial extends DataEntity<UserStudyRecordMaterial> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String mesanId;		// 资料id
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	
	public UserStudyRecordMaterial() {
		super();
	}

	public UserStudyRecordMaterial(String id){
		super(id);
	}

	
	public String getMesanId() {
		return mesanId;
	}

	public void setMesanId(String mesanId) {
		this.mesanId = mesanId;
	}

	@Length(min=0, max=64, message="用户id长度必须介于 0 和 64 之间")
	@ExcelField(title="用户id", align=2, sort=7)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="开始时间", align=2, sort=8)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@ExcelField(title="结束时间", align=2, sort=9)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}