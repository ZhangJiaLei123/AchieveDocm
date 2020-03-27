/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 学习记录信息Entity
 * @author wsp
 * @version 2017-04-09
 */
public class StudyRecord extends DataEntity<StudyRecord> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String courseId;		// 课程id
	private String activityId;		// 活动id
	private String resourceId;		// 资源id
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	
	private String activityDirId; //活动地址id
	private String searchContent;//搜索内容
	
	public StudyRecord() {
		super();
	}

	public StudyRecord(String id){
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
	
	@Length(min=0, max=64, message="课程id长度必须介于 0 和 64 之间")
	@ExcelField(title="课程id", align=2, sort=8)
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@Length(min=0, max=64, message="活动id长度必须介于 0 和 64 之间")
	@ExcelField(title="活动id", align=2, sort=9)
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=64, message="资源id长度必须介于 0 和 64 之间")
	@ExcelField(title="资源id", align=2, sort=10)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getActivityDirId() {
		return activityDirId;
	}

	public void setActivityDirId(String activityDirId) {
		this.activityDirId = activityDirId;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	
	
}