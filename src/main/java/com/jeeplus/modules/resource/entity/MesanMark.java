package com.jeeplus.modules.resource.entity;

import com.jeeplus.common.persistence.DataEntity;

public class MesanMark extends DataEntity<MesanMark>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double score;//用户评分
	private String mesanId;//资料id
	private String userId;//用户id
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getMesanId() {
		return mesanId;
	}
	public void setMesanId(String mesanId) {
		this.mesanId = mesanId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
