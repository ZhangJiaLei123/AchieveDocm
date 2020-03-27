/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 授课时间功能Entity
 * @author wsp
 * @version 2017-04-03
 */
public class LessonTime extends DataEntity<LessonTime> {
	
	private static final long serialVersionUID = 1L;
	private String studyId;		// 学习活动表id
	private Date lessonStartTime;		// 课程开始时间
	private Date lessonEndTime;		// 课程结束时间
	
	private String activityDirIds; //活动id
	private List<java.util.HashMap<String, List<ActivityDir>>> activityDirList;//章节的list
	private List<ActivityDir> listActivityDir;
	
	public LessonTime() {
		super();
	}

	public LessonTime(String id){
		super(id);
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public Date getLessonStartTime() {
		return lessonStartTime;
	}

	public void setLessonStartTime(Date lessonStartTime) {
		this.lessonStartTime = lessonStartTime;
	}

	public Date getLessonEndTime() {
		return lessonEndTime;
	}

	public void setLessonEndTime(Date lessonEndTime) {
		this.lessonEndTime = lessonEndTime;
	}

	public String getActivityDirIds() {
		return activityDirIds;
	}

	public void setActivityDirIds(String activityDirIds) {
		this.activityDirIds = activityDirIds;
	}

	public List<java.util.HashMap<String, List<ActivityDir>>> getActivityDirList() {
		return activityDirList;
	}

	public void setActivityDirList(List<java.util.HashMap<String, List<ActivityDir>>> activityDirList) {
		this.activityDirList = activityDirList;
	}

	public List<ActivityDir> getListActivityDir() {
		return listActivityDir;
	}

	public void setListActivityDir(List<ActivityDir> listActivityDir) {
		this.listActivityDir = listActivityDir;
	}
	
	
	
}