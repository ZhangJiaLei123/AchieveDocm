/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户关注活动计划表Entity
 * @author wsp
 * @version 2017-04-08
 */
public class ProgramFocus extends DataEntity<ProgramFocus> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String programActivityId;		// 计划活动id
	
	private String searchContent;
	
	public ProgramFocus() {
		super();
	}

	public ProgramFocus(String id){
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
	
	@Length(min=0, max=64, message="计划活动id长度必须介于 0 和 64 之间")
	@ExcelField(title="计划活动id", align=2, sort=8)
	public String getProgramActivityId() {
		return programActivityId;
	}

	public void setProgramActivityId(String programActivityId) {
		this.programActivityId = programActivityId;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	
	
	
}