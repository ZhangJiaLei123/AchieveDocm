package com.yfhl.commons.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月16日 上午11:03:17 
 * @Description: 
 */
public class AbsEntity extends Entity{

	/** @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 上午11:03:31
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日 上午11:10:52
	 * 默认构造函数
	 */
	public AbsEntity(){}
	
	/** 版本锁(用于数据库乐观锁) */
	private long entityVersion = 1L;
	/** 版本密文 */
	private String versionSigner = null;
	/** 数据指纹 */
	private String entityFinger;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	
	public long getEntityVersion() {
		return entityVersion;
	}
	public void setEntityVersion(long entityVersion) {
		this.entityVersion = entityVersion;
	}
	public String getVersionSigner() {
		return versionSigner;
	}
	public void setVersionSigner(String versionSigner) {
		this.versionSigner = versionSigner;
	}
	public String getEntityFinger() {
		return entityFinger;
	}
	public void setEntityFinger(String entityFinger) {
		this.entityFinger = entityFinger;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 上午11:29:02
	 * @Description :获取唯一属性值，同getId()
	 * @return
	 * String
	 */
	public String getUniqueCode() {
		if (getId() == null) {
			return null;
		}
		return getId();
	}
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 上午11:22:32
	 * @Description ：克隆对象，复制属性值
	 * @param orig ： 源对象
	 * void
	 */
	public void clone(AbsEntity orig){
		try {
			PropertyUtils.copyProperties(this, orig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 上午11:24:21
	 * @Description :克隆对象，复制属性值
	 * @param dest 目标对象
	 * void
	 */
	public void cloneTo(AbsEntity dest){
		try {
			PropertyUtils.copyProperties(dest, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重写toString方法
	 */
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(",createTime=").append(createTime);
		s.append(",updateTime=").append(updateTime);
		s.append(",entityVersion=").append(entityVersion);
		s.append(",versionSigner=").append(versionSigner);
		s.append(",entityFinger=").append(entityFinger);
		return s.toString();
	}

	
	
}
