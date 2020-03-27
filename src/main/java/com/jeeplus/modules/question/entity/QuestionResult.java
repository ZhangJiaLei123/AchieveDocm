/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户答题信息表Entity
 * @author wsp
 * @version 2017-03-26
 */
public class QuestionResult extends DataEntity<QuestionResult> {
	
	private static final long serialVersionUID = 1L;
	private String releaseId;		// 发布id
	private String problemId;		// 问题id
	private String userId;		// 用户id
	private String resAnswer;		// 
	private String userName;    //用户名
	
	private String activityDirId;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public QuestionResult() {
		super();
	}

	public QuestionResult(String id){
		super(id);
	}

	@Length(min=1, max=64, message="发布id长度必须介于 1 和 64 之间")
	@ExcelField(title="发布id", align=2, sort=7)
	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}
	
	@Length(min=0, max=64, message="问题id长度必须介于 0 和 64 之间")
	@ExcelField(title="问题id", align=2, sort=8)
	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}
	
	@Length(min=0, max=64, message="用户id长度必须介于 0 和 64 之间")
	@ExcelField(title="用户id", align=2, sort=9)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="长度必须介于 0 和 64 之间")
	@ExcelField(title="", align=2, sort=10)
	public String getResAnswer() {
		return resAnswer;
	}

	public void setResAnswer(String resAnswer) {
		this.resAnswer = resAnswer;
	}

	public String getActivityDirId() {
		return activityDirId;
	}

	public void setActivityDirId(String activityDirId) {
		this.activityDirId = activityDirId;
	}
	
	
}