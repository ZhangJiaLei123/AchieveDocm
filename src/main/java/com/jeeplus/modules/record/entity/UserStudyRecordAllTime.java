/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.record.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * recordEntity
 * @author w
 * @version 2017-06-29
 */
public class UserStudyRecordAllTime extends DataEntity<UserStudyRecordAllTime> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String time;		// 时间，两分钟乘以2
	
	public UserStudyRecordAllTime() {
		super();
	}

	public UserStudyRecordAllTime(String id){
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
	
	@Length(min=0, max=64, message="时间，两分钟乘以2长度必须介于 0 和 64 之间")
	@ExcelField(title="时间，两分钟乘以2", align=2, sort=8)
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}