/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 问卷发布Entity
 * @author wsp
 * @version 2017-03-26
 */
public class QuestionRelease extends DataEntity<QuestionRelease> {
	
	private static final long serialVersionUID = 1L;
	private Date startTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String questionInfoId;		// 问卷id
	private String userName;        //新增用户名
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public QuestionRelease() {
		super();
	}

	public QuestionRelease(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始时间不能为空")
	@ExcelField(title="开始时间", align=2, sort=7)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	@ExcelField(title="结束时间", align=2, sort=8)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=1, max=64, message="问卷id长度必须介于 1 和 64 之间")
	@ExcelField(title="问卷id", align=2, sort=9)
	public String getQuestionInfoId() {
		return questionInfoId;
	}

	public void setQuestionInfoId(String questionInfoId) {
		this.questionInfoId = questionInfoId;
	}
	
}