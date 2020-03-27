/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 计划活动Entity
 * @author panjp
 * @version 2017-03-24
 */
public class ProgramActivity extends DataEntity<ProgramActivity> {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;		// 活动名称
	private String space;		// 活动地点
	private Integer userCount;		// 活动人数
	private String proTime;		// 活动时间
	private String trainProgramId;		// 所属计划
	
	private String userFllow;//用户是否已经关注的标记
	
	private int countGzUser;//关注人数
	
	public ProgramActivity() {
		super();
	}

	public ProgramActivity(String id){
		super(id);
	}

	@Length(min=0, max=200, message="活动名称长度必须介于 0 和 200 之间")
	@ExcelField(title="活动名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="活动地点长度必须介于 0 和 200 之间")
	@ExcelField(title="活动地点", align=2, sort=8)
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}
	
	@ExcelField(title="活动人数", align=2, sort=9)
	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	
	public String getProTime() {
		return proTime;
	}

	public void setProTime(String proTime) {
		this.proTime = proTime;
	}

	@Length(min=0, max=64, message="所属计划长度必须介于 0 和 64 之间")
	@ExcelField(title="所属计划", align=2, sort=11)
	public String getTrainProgramId() {
		return trainProgramId;
	}

	public void setTrainProgramId(String trainProgramId) {
		this.trainProgramId = trainProgramId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserFllow() {
		return userFllow;
	}

	public void setUserFllow(String userFllow) {
		this.userFllow = userFllow;
	}

	public int getCountGzUser() {
		return countGzUser;
	}

	public void setCountGzUser(int countGzUser) {
		this.countGzUser = countGzUser;
	}
	
	
}