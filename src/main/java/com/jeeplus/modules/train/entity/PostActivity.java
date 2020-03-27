/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 岗位活动Entity
 * @author 岗位活动
 * @version 2017-03-26
 */
public class PostActivity extends DataEntity<PostActivity> {
	
	private static final long serialVersionUID = 1L;
	private String postId;		// 岗位ID
	private String activityId;		// 活动ID
	private String type;		// 关系类别
	private String postName;//岗位名称
	private String postLevelName;//岗位级别
	
	public PostActivity() {
		super();
	}

	public PostActivity(String id){
		super(id);
	}

	@Length(min=0, max=64, message="岗位ID长度必须介于 0 和 64 之间")
	@ExcelField(title="岗位ID", align=2, sort=7)
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	@Length(min=0, max=64, message="活动ID长度必须介于 0 和 64 之间")
	@ExcelField(title="活动ID", align=2, sort=8)
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=10, message="关系类别长度必须介于 0 和 10 之间")
	@ExcelField(title="关系类别", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostLevelName() {
		return postLevelName;
	}

	public void setPostLevelName(String postLevelName) {
		this.postLevelName = postLevelName;
	}
	
}