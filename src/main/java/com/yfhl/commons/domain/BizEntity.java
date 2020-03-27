package com.yfhl.commons.domain;
/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月16日 上午11:30:55 
 * @Description: 
 */
public class BizEntity extends AbsEntity {

	/** @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 上午11:31:01
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日 上午11:32:25
	 * 默认构造函数
	 */
	public BizEntity(){}
	
	/** 启用标志	默认：1	0：禁用 ，1：启用 */
	private String enabledFlag;
	/** 删除标志	默认：0	0：未删除 ，1：已删除  */
	private String deletedFlag;
	
	public String getEnabledFlag() {
		if(enabledFlag == null){
			return GlobalConstants.FLAG_ENABLED;
		}
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public String getDeletedFlag() {
		if(enabledFlag == null){
			return GlobalConstants.FLAG_NOT_DELETED;
		}
		return deletedFlag;
	}
	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
	

	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 上午11:46:12
	 * @Description :判断是否启用
	 * @return
	 * boolean
	 */
	public boolean isEnabled(){
		if(enabledFlag == null){
			return false;
		}
		return GlobalConstants.FLAG_ENABLED.equals(enabledFlag);
	}
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 上午11:47:55
	 * @Description :判断是否删除
	 * @return
	 * boolean
	 */
	public boolean isDeleted(){
		if(deletedFlag == null){
			return false;
		}
		return GlobalConstants.FLAG_DELETED.equals(deletedFlag);
	}
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 上午11:52:12
	 * @Description :字符向布尔值转换
	 * @param value
	 * @return
	 * boolean
	 */
	protected boolean parseBoolean(String value){
		if(value == null || "".equals(value.trim())){
			return Boolean.FALSE.booleanValue();
		}
		return "1".equals(value.trim());
	}
	
	/**
	 * 重写toString方法
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(",enabledFlag=").append(enabledFlag);
		s.append(",deletedFlag=").append(deletedFlag);
		s.append(super.toString());
		return s.toString();
	}
}
