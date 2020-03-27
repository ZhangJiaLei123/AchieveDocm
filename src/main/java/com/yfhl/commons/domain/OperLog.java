package com.yfhl.commons.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * LOGIN：登录；LOGOUT：注销；OPER：菜单操作
 * 
 * @author Administrator
 *
 */
public class OperLog extends BizEntity implements Cloneable {
	public static final String LOGIN = "LOGIN";
	public static final String LOGOUT = "LOGOUT";
	public static final String OPER = "OPER";

	private static Map<String, String> descMap = new HashMap<String, String>();
	static {
		descMap.put(LOGIN, "登录");
		descMap.put(LOGOUT, "注销");
		descMap.put(OPER, "操作");
	}

	/**  */
	private static final long serialVersionUID = 1L;
	/** 操作类型，LOGIN：登录；LOGOUT：注销；OPER：菜单操作 */
	private String operType;
	/** 操作内容 */
	private String operDesc;
	/** IP地址 */
	private String ipAddress;
	/** 操作者ID */
	private String operId;
	/** 操作者代码 */
	private String operCode;
	/** 操作者名称 */
	private String operName;
	/** 组织ID */
	private String orgId;
	/** 组织代码 */
	private String orgCode;
	/** 组织名称 */
	private String orgName;
	/** 辅助标记 */
	private String assFlag;
	/** 备注 */
	private String remark;
	/** SUCCESS：成功；FAIL：失败 */
	private String operFlag = GlobalConstants.FLAG_SUCCESS;
	/** 关联代码 */
	private String refCode;

	/**
	 * 默认构造函数
	 */
	public OperLog() {

	}

	/**
	 * 用处理流程对象构造日志对象
	 * 
	 * @param bizProcess
	 */
	public OperLog(BizProcess bizProcess) {
		setIpAddress(bizProcess.getIpAddress());
		setOperId(bizProcess.getOperId());
		setOperCode(bizProcess.getOperCode());
		setOperName(bizProcess.getOperName());
		setOrgId(bizProcess.getOperOrgId());
		setOrgCode(bizProcess.getOperOrgCode());
		setOrgName(bizProcess.getOperOrgName());
		setOperDesc(bizProcess.getDealName());
		setRefCode(bizProcess.getDataId());
	}

	/**
	 *
	 */
	public void castOperDesc() {
		if (this.operType != null) {
			this.operDesc = descMap.get(this.operType);
		}
	}

	/**
	 * 辅助标记
	 * 
	 * @return
	 */
	public String getAssFlag() {
		return assFlag;
	}

	/**
	 * IP地址
	 * 
	 * @return
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * 操作者代码 <br>
	 * Return the value associated with the column: OPER_CODE
	 */
	public String getOperCode() {
		return operCode;
	}

	/**
	 * 操作内容 <br>
	 * Return the value associated with the column: OPER_DESC
	 */
	public String getOperDesc() {
		return operDesc;
	}

	/**
	 * SUCCESS：成功；FAIL：失败
	 */
	public String getOperFlag() {
		return operFlag;
	}

	/**
	 * 操作者ID<br>
	 * Return the value associated with the column: OPER_ID
	 */
	public String getOperId() {
		return operId;
	}

	/**
	 * 操作者名称 <br>
	 * Return the value associated with the column: OPER_NAME
	 */
	public String getOperName() {
		return operName;
	}

	/**
	 * 操作类型，LOGIN：登录；LOGOUT：注销；OPER：菜单操作 <br>
	 * Return the value associated with the column: OPER_TYPE
	 */
	public String getOperType() {
		return operType;
	}

	/**
	 * 操作者所属组织代码<br>
	 * Return the value associated with the column: ORG_CODE
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 操作者所属组织Id<br>
	 * Return the value associated with the column: ORG_ID
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * 操作者所属组织名称<br>
	 * Return the value associated with the column: ORG_NAME
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 关联代码<br>
	 * 登录、注销时，NONE；菜单时，保存对应的上下文代码 <br>
	 * Return the value associated with the column: REF_CODE
	 */
	public String getRefCode() {
		return refCode;
	}

	/**
	 * 备注<br>
	 * Return the value associated with the column: REMARK
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 辅助标记
	 * 
	 * @param assFlag
	 */
	public void setAssFlag(String assFlag) {
		this.assFlag = assFlag;
	}

	/**
	 * IP地址
	 * 
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * 操作者代码 <br>
	 * Set the value related to the column: OPER_CODE
	 * 
	 * @param operCode
	 *            the OPER_CODE value
	 */
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	/**
	 * 操作内容<br>
	 * Set the value related to the column: OPER_DESC
	 * 
	 * @param operDesc
	 *            the OPER_DESC value
	 */
	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}

	/**
	 * SUCCESS：成功；FAIL：失败
	 * 
	 * @param operFlag
	 */
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	/**
	 * 操作者ID <br>
	 * Set the value related to the column: OPER_ID
	 * 
	 * @param operId
	 *            the OPER_ID value
	 */
	public void setOperId(String operId) {
		this.operId = operId;
	}

	/**
	 * 操作者名称<br>
	 * Set the value related to the column: OPER_NAME
	 * 
	 * @param operName
	 *            the OPER_NAME value
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}

	/**
	 * 操作类型，LOGIN：登录；LOGOUT：注销；OPER：菜单操作<br>
	 * Set the value related to the column: OPER_TYPE
	 * 
	 * @param operType
	 *            the OPER_TYPE value
	 */
	public void setOperType(String operType) {
		this.operType = operType;
	}

	/**
	 * 操作者所属组织代码<br>
	 * 
	 * @param orgCode
	 *            the ORG_CODE value
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 操作者所属组织Id<br>
	 * 
	 * @param orgId
	 *            the ORG_ID value
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 操作者所属组织名称<br>
	 * 
	 * @param orgName
	 *            the ORG_NAME value
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 关联代码<br>
	 * 
	 * @param refCode
	 *            the REF_CODE value
	 */
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	/**
	 * 备注<br>
	 * 
	 * @param remark
	 *            the REMARK value
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 覆盖toStirng
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(",remark=").append(remark);
		s.append(",operId=").append(this.operId);
		s.append(",operCode=").append(this.operCode);
		s.append(",operName=").append(this.operName);
		s.append(",operOrgId=").append(orgId);
		s.append(",operOrgCode=").append(orgCode);
		s.append(",operOrgName=").append(orgName);
		s.append(";").append(super.toString());
		return s.toString();
	}
}
