package com.yfhl.commons.domain;

import java.io.Serializable;
import java.util.Objects;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月16日 上午10:13:00 
 * @Description: 
 */
public class Entity implements Serializable,Cloneable{
	
	/** @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 上午10:33:13
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日 上午11:32:46
	 * 默认构造函数
	 */
	public Entity(){}
	
	/**
	 * 实体id
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 重写hashCode方法
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	/**
	 * 重写equals方式
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Entity other = (Entity) obj;

		return Objects.equals(id, other.id);
	}

	/**
	 * 重写clone方式
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		try{
			return super.clone();
		}catch(CloneNotSupportedException cse){
			cse.printStackTrace();
			return null;
		}
		
	}

	/**
	 * 重新toString方法
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(",id=").append(id);
		return sb.toString();
	}
}
