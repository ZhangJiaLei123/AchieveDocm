/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.question.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 问卷题目Entity
 * @author wsp
 * @version 2017-03-26
 */
public class QuestionProblem extends DataEntity<QuestionProblem> {
	
	private static final long serialVersionUID = 1L;
	private String questionId;		// 问卷ID
	private String proModel;		// 问卷模板（1单选，2多选，3判断）
	private String proSterm;		// 问卷主干
	private String proAnswer;		// 问卷答案
	private String proQueOne;		// 答案1
	private String proQueTwo;		// 答案2
	private String proQueThree;		// 答案3
	private String proQueFour;		// 答案4
	private String proSort;		// 问题排序
	
	//获取用户答题信息
	private String releaseId;
	private String userId;
	private String answer;	//答案
	
	//答题统计
	private String totalNum;
	private int totalNumA;
	private int totalNumB;
	private int totalNumC;
	private int totalNumD;
	
	public QuestionProblem() {
		super();
	}

	public QuestionProblem(String id){
		super(id);
	}

	@Length(min=1, max=64, message="问卷ID长度必须介于 1 和 64 之间")
	@ExcelField(title="问卷ID", align=2, sort=7)
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	@Length(min=1, max=2, message="问卷模板（1单选，2多选，3判断）长度必须介于 1 和 2 之间")
	@ExcelField(title="问卷模板（1单选，2多选，3判断）", dictType="", align=2, sort=8)
	public String getProModel() {
		return proModel;
	}

	public void setProModel(String proModel) {
		this.proModel = proModel;
	}
	
	@Length(min=1, max=2000, message="问卷主干长度必须介于 1 和 2000 之间")
	@ExcelField(title="问卷主干", align=2, sort=9)
	public String getProSterm() {
		return proSterm;
	}

	public void setProSterm(String proSterm) {
		this.proSterm = proSterm;
	}
	
	@Length(min=1, max=64, message="问卷答案长度必须介于 1 和 64 之间")
	@ExcelField(title="问卷答案", align=2, sort=10)
	public String getProAnswer() {
		return proAnswer;
	}

	public void setProAnswer(String proAnswer) {
		this.proAnswer = proAnswer;
	}
	
	@Length(min=0, max=2000, message="答案1长度必须介于 0 和 2000 之间")
	@ExcelField(title="答案1", align=2, sort=11)
	public String getProQueOne() {
		return proQueOne;
	}

	public void setProQueOne(String proQueOne) {
		this.proQueOne = proQueOne;
	}
	
	@Length(min=0, max=2000, message="答案2长度必须介于 0 和 2000 之间")
	@ExcelField(title="答案2", align=2, sort=12)
	public String getProQueTwo() {
		return proQueTwo;
	}

	public void setProQueTwo(String proQueTwo) {
		this.proQueTwo = proQueTwo;
	}
	
	@Length(min=0, max=2000, message="答案3长度必须介于 0 和 2000 之间")
	@ExcelField(title="答案3", align=2, sort=13)
	public String getProQueThree() {
		return proQueThree;
	}

	public void setProQueThree(String proQueThree) {
		this.proQueThree = proQueThree;
	}
	
	@Length(min=0, max=2000, message="答案4长度必须介于 0 和 2000 之间")
	@ExcelField(title="答案4", align=2, sort=14)
	public String getProQueFour() {
		return proQueFour;
	}

	public void setProQueFour(String proQueFour) {
		this.proQueFour = proQueFour;
	}
	
	@ExcelField(title="问题排序", align=2, sort=15)
	public String getProSort() {
		return proSort;
	}

	public void setProSort(String proSort) {
		this.proSort = proSort;
	}

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public int getTotalNumA() {
		return totalNumA;
	}

	public void setTotalNumA(int totalNumA) {
		this.totalNumA = totalNumA;
	}

	public int getTotalNumB() {
		return totalNumB;
	}

	public void setTotalNumB(int totalNumB) {
		this.totalNumB = totalNumB;
	}

	public int getTotalNumC() {
		return totalNumC;
	}

	public void setTotalNumC(int totalNumC) {
		this.totalNumC = totalNumC;
	}

	public int getTotalNumD() {
		return totalNumD;
	}

	public void setTotalNumD(int totalNumD) {
		this.totalNumD = totalNumD;
	}
		
}