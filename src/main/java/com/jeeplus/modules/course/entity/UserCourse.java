/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 学员课程关系表Entity
 * @author panjp
 * @version 2017-03-24
 */
public class UserCourse extends DataEntity<UserCourse> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 学员ID
	private String courseId;		// 课程ID
	private String type;		// 关系类型
	
	private String officeName;//机构名称
	private String userName;//用户名称
	private String postName;//岗位名称
	private String postLevel;//岗位等级
	
	private String searchContent;
	
	
	public UserCourse() {
		super();
	}

	public UserCourse(String id){
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
	
	@Length(min=0, max=64, message="课程ID长度必须介于 0 和 64 之间")
	@ExcelField(title="课程ID", align=2, sort=8)
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@Length(min=0, max=10, message="关系类型长度必须介于 0 和 10 之间")
	@ExcelField(title="关系类型", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	
}