/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 学员活动Entity
 * @author panjp
 * @version 2017-03-26
 */
public class UserActivity extends DataEntity<UserActivity> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 学员ID
	private String activityId;		// 活动ID
	private String type;		// 关系类别
	
	private String searchContent;
	
	public UserActivity() {
		super();
	}

	public UserActivity(String id){
		super(id);
	}

	@Length(min=0, max=64, message="学员ID长度必须介于 0 和 64 之间")
	@ExcelField(title="学员ID", align=2, sort=7)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="活动ID长度必须介于 0 和 64 之间")
	@ExcelField(title="活动ID", align=2, sort=8)
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=64, message="关系类别长度必须介于 0 和 64 之间")
	@ExcelField(title="关系类别", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	
	
}