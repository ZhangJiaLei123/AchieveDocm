/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 活动章节目录Entity
 * @author wsp
 * @version 2017-04-02
 */
public class ActivityDir extends DataEntity<ActivityDir> {
	
	private static final long serialVersionUID = 1L;
	private String activityId;		// 活动id
	private String dirName;		// 目录名称
	private String parentId;		// 父级id（目录分为章节目录，只有节目录有）
	private int coutRes;
	private int resSorce;//分数
	private List<ActivityDir> listActivityDir;
	private String lessionTimeId;//活动课程时间ID
	private String userId;
	private int userSorce;
	
	public ActivityDir() {
		super();
	}

	public ActivityDir(String id){
		super(id);
	}

	@Length(min=0, max=64, message="活动id长度必须介于 0 和 64 之间")
	@ExcelField(title="活动id", align=2, sort=7)
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=64, message="目录名称长度必须介于 0 和 64 之间")
	@ExcelField(title="目录名称", align=2, sort=8)
	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	
	@Length(min=0, max=64, message="父级id（目录分为章节目录，只有节目录有）长度必须介于 0 和 64 之间")
	@ExcelField(title="父级id（目录分为章节目录，只有节目录有）", align=2, sort=9)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getCoutRes() {
		return coutRes;
	}

	public void setCoutRes(int coutRes) {
		this.coutRes = coutRes;
	}

	public int getResSorce() {
		return resSorce;
	}

	public void setResSorce(int resSorce) {
		this.resSorce = resSorce;
	}

	public List<ActivityDir> getListActivityDir() {
		return listActivityDir;
	}

	public void setListActivityDir(List<ActivityDir> listActivityDir) {
		this.listActivityDir = listActivityDir;
	}

	public String getLessionTimeId() {
		return lessionTimeId;
	}

	public void setLessionTimeId(String lessionTimeId) {
		this.lessionTimeId = lessionTimeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getUserSorce() {
		return userSorce;
	}

	public void setUserSorce(int userSorce) {
		this.userSorce = userSorce;
	}
	
}