/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户等级管理Entity
 * @author 潘兴武
 * @version 2018-01-04
 */
public class TUserGrade extends DataEntity<TUserGrade> {
	
	private static final long serialVersionUID = 1L;
	private String gradeName;		// 等级名称
	private String startScore;		// 等级起始分数
	private String endScore;		// 等级结束分数
	private Date createTime;		// 创建时间
	private Date modTime;		// 修改时间
	
	public TUserGrade() {
		super();
	}

	public TUserGrade(String id){
		super(id);
	}

	@ExcelField(title="等级名称", align=2, sort=1)
	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	@ExcelField(title="等级起始分数", align=2, sort=2)
	public String getStartScore() {
		return startScore;
	}

	public void setStartScore(String startScore) {
		this.startScore = startScore;
	}
	
	@ExcelField(title="等级结束分数", align=2, sort=3)
	public String getEndScore() {
		return endScore;
	}

	public void setEndScore(String endScore) {
		this.endScore = endScore;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=4)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="修改时间", align=2, sort=5)
	public Date getModTime() {
		return modTime;
	}

	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}
	
}