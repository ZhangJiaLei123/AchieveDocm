/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 活动目录资源分配中间表Entity
 * @author wsp
 * @version 2017-04-03
 */
public class DistribuTemp extends DataEntity<DistribuTemp> {
	
	private static final long serialVersionUID = 1L;
	private String activityDirId;		// 活动目录id
	private String typeId;		// 类型:1.资料2.资源3.问卷
	private String refId;		// 关联的资料，资源，问卷的ID
	private int resSort;		// 资源显示顺序
	private float resScore;		// 资源分数
	private String activityId;		// 学习活动id
	
	private String resName;
	
	public DistribuTemp() {
		super();
	}

	public DistribuTemp(String id){
		super(id);
	}

	@Length(min=0, max=64, message="活动目录id长度必须介于 0 和 64 之间")
	@ExcelField(title="活动目录id", align=2, sort=7)
	public String getActivityDirId() {
		return activityDirId;
	}

	public void setActivityDirId(String activityDirId) {
		this.activityDirId = activityDirId;
	}
	
	@Length(min=0, max=2, message="问卷长度必须介于 0 和 2 之间")
	@ExcelField(title="类型:1.资料2.资源3.问卷", align=2, sort=8)
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=0, max=64, message="关联的资料，资源，问卷的ID长度必须介于 0 和 64 之间")
	@ExcelField(title="关联的资料，资源，问卷的ID", align=2, sort=9)
	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	
	
	public int getResSort() {
		return resSort;
	}

	public void setResSort(int resSort) {
		this.resSort = resSort;
	}

	public float getResScore() {
		return resScore;
	}

	public void setResScore(float resScore) {
		this.resScore = resScore;
	}

	@Length(min=0, max=64, message="学习活动id长度必须介于 0 和 64 之间")
	@ExcelField(title="学习活动id", align=2, sort=12)
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}
	
	
}