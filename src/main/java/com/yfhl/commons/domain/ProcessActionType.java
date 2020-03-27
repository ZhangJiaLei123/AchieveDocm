package com.yfhl.commons.domain;

/**
 * 业务流程操作类型
 * 
 * @author ZHH
 *
 */
public enum ProcessActionType {
	NONE("NONE"), SAVE("添加"), MODIFY("修改"), DELETE("删除");
	/** 操作描述 */
	private String actionDesc;

	/**
	 * 
	 * @return
	 */
	public String getActionDesc() {
		return this.actionDesc;
	}

	/**
	 * 私有构造函数
	 * 
	 * @param description
	 *            操作描述
	 */
	private ProcessActionType(String description) {
		this.actionDesc = description;
	}
}
