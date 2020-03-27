/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 岗位课程Entity
 * @author panjp
 * @version 2017-03-24
 */
public class PostCourse extends DataEntity<PostCourse> {
	
	private static final long serialVersionUID = 1L;
	private String postId;		// 岗位ID
	private String courseId;		// 课程ID
	private String type;		// 关系类别
	private String postLevel;//岗位等级
	private String postName;//岗位名称
	private String postLevelName;//岗位等级名称
	
	
	public PostCourse() {
		super();
	}

	public PostCourse(String id){
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
	
	@Length(min=0, max=64, message="课程ID长度必须介于 0 和 64 之间")
	@ExcelField(title="课程ID", align=2, sort=8)
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@Length(min=0, max=10, message="关系类别长度必须介于 0 和 10 之间")
	@ExcelField(title="关系类别", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
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