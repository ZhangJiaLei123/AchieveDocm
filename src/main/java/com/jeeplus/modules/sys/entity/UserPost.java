/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户岗位关系Entity
 * @author panjp
 * @version 2017-03-28
 */
public class UserPost extends DataEntity<UserPost> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String postId;		// 岗位ID
	private String postType;		// 职位类别
	private String postLevel;		// 岗位等级
	private String isDefault;		// 是否第一岗位
	private Integer orderNum;//序号
	private String postName;
	private String postTypeName;
	private String postLevelName;
	
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public UserPost() {
		super();
	}

	public UserPost(String id){
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
	
	@Length(min=0, max=64, message="岗位ID长度必须介于 0 和 64 之间")
	@ExcelField(title="岗位ID", align=2, sort=8)
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	@Length(min=0, max=64, message="职位类别长度必须介于 0 和 64 之间")
	@ExcelField(title="职位类别", align=2, sort=9)
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	
	@Length(min=0, max=64, message="岗位等级长度必须介于 0 和 64 之间")
	@ExcelField(title="岗位等级", align=2, sort=10)
	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}
	
	@Length(min=0, max=10, message="是否第一岗位长度必须介于 0 和 10 之间")
	@ExcelField(title="是否第一岗位", align=2, sort=11)
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}



	public String getPostTypeName() {
		return postTypeName;
	}

	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}

	public String getPostLevelName() {
		return postLevelName;
	}

	public void setPostLevelName(String postLevelName) {
		this.postLevelName = postLevelName;
	}
	
}