/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 问卷信息Entity
 * @author panjp
 * @version 2017-03-24
 */
public class QuestionInfo extends DataEntity<QuestionInfo> {
	
	private static final long serialVersionUID = 1L;
	private String queName;		// 问卷名称
	private String status;		// 文件状态
	private int pNum;//问题数量 
	
	private String activityDirId;
	private String activityId;
	private String qreleaseId;
	
	public QuestionInfo() {
		super();
	}

	public QuestionInfo(String id){
		super(id);
	}

	@Length(min=0, max=255, message="问卷名称长度必须介于 0 和 255 之间")
	@ExcelField(title="问卷名称", align=2, sort=7)
	public String getQueName() {
		return queName;
	}

	public void setQueName(String queName) {
		this.queName = queName;
	}
	
	@Length(min=0, max=10, message="文件状态长度必须介于 0 和 10 之间")
	@ExcelField(title="文件状态", align=2, sort=8)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getpNum() {
		return pNum;
	}

	public void setpNum(int pNum) {
		this.pNum = pNum;
	}

	public String getActivityDirId() {
		return activityDirId;
	}

	public void setActivityDirId(String activityDirId) {
		this.activityDirId = activityDirId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getQreleaseId() {
		return qreleaseId;
	}

	public void setQreleaseId(String qreleaseId) {
		this.qreleaseId = qreleaseId;
	}
	
}