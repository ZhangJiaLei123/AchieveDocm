/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 授课时间与章节表Entity
 * @author wsp
 * @version 2017-04-03
 */
public class LessonDIr extends DataEntity<LessonDIr> {
	
	private static final long serialVersionUID = 1L;
	private String lessonTimeId;		// 授课时间表id
	private String activityDirId;		// 活动章节表id
	
	public LessonDIr() {
		super();
	}

	public LessonDIr(String id){
		super(id);
	}

	@Length(min=0, max=64, message="授课时间表id长度必须介于 0 和 64 之间")
	@ExcelField(title="授课时间表id", align=2, sort=7)
	public String getLessonTimeId() {
		return lessonTimeId;
	}

	public void setLessonTimeId(String lessonTimeId) {
		this.lessonTimeId = lessonTimeId;
	}
	
	@Length(min=0, max=64, message="活动章节表id长度必须介于 0 和 64 之间")
	@ExcelField(title="活动章节表id", align=2, sort=8)
	public String getActivityDirId() {
		return activityDirId;
	}

	public void setActivityDirId(String activityDirId) {
		this.activityDirId = activityDirId;
	}
	
}