package com.jeeplus.modules.sys.entity;

import com.jeeplus.common.persistence.DataEntity;

public class LessionImportUser extends DataEntity<PostType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private String userId;
	 private String activeId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	
}
