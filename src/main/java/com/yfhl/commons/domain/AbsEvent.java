package com.yfhl.commons.domain;

/**
 * 操作事件支持类
 * 
 * @author ZHH
 *
 */
public class AbsEvent extends AbsEntity {

	/**	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 默认构造函数
	 */
	public AbsEvent() {

	}

	/**
	 * 覆盖方法
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}
}
