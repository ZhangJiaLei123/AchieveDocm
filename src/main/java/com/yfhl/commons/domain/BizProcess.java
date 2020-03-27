package com.yfhl.commons.domain;

import java.util.Date;

public class BizProcess extends AbsEntity {
	/** */
	private static final long serialVersionUID = 1L;

	public static String STATUS_INIT = "0";

	public static String STATUS_FINISHED = "99";

	public static String STATUS_SUB_INIT = "00";

	/** 不允许任何结束 */
	public static int ALLOW_END_NONE = 0;
	/** 允许功能结束 */
	public static int ALLOW_END_FUNC = 10;
	/** 允许流程结束 */
	public static int ALLOW_END_PROC = 90;

	/** 附加说明 */
	private String assDesc;
	/** 数据内容 */
	private String dataContent;
	/** 数据ID */
	private String dataId;
	/** 数据名称 */
	private String dataName;
	/** 数据类型 */
	private String dataType;
	/** 交易代码 */
	private String dealCode;
	/** 交易名称 */
	private String dealName;
	/** 失效标志 */
	private String disabledFlag;
	/** 实体类别 */
	private String entityType;
	/** 功能代码 */
	private String funcCode;
	/** 功能名称 */
	private String funcName;
	/** 模块代码 */
	private String moduleCode;
	/** 操作者代码 */
	private String operCode;
	/** 操作者ID */
	private String operId;
	/** 操作者名称 */
	private String operName;
	/** 操作者所属组织代码 */
	private String operOrgCode;
	/** 操作者所属组织ID */
	private String operOrgId;
	/** 操作者所属组织ID */
	private String operOrgName;
	/** 处理代码 */
	private String processCode;
	/** 处理名称 */
	private String processName;
	/** 关联代码 */
	private String refCode;
	/** 备注 */
	private String remark;
	/** IP地址 */
	private String ipAddress;
	/** URL */
	private String url;

	/** 处理状态 */
	private String procStatus;
	/** 处理状态描述 */
	private String procStatusDesc;

	/** 处理子状态 */
	private String procSubStatus = STATUS_SUB_INIT;

	/** 驳回标记 */
	private String rejectFlag;

	/** 创建时间 */
	private Date createTime;

	/** 修改时间 */
	private Date updateTime;

	/**
	 * 默认构造函数
	 */
	public BizProcess() {

	}

	/**
	 * 复制处理对象数据
	 * 
	 * @param bizProcess
	 */
	public void clone(BizProcess bizProcess) {
		if (bizProcess == null) {
			return;
		}
		this.setId(bizProcess.getId());
		this.entityType = bizProcess.getEntityType();
		this.moduleCode = bizProcess.getModuleCode();
		this.funcCode = bizProcess.getFuncCode();
		this.funcName = bizProcess.getFuncName();
		this.assDesc = bizProcess.getAssDesc();
		this.dataContent = bizProcess.getDataContent();
		this.dataName = bizProcess.getDataName();
		this.dataId = bizProcess.getDataId();
		this.dealCode = bizProcess.getDealCode();
		this.dealName = bizProcess.getDealName();
		this.processCode = bizProcess.getProcessCode();
		this.processName = bizProcess.getProcessName();
		this.refCode = bizProcess.getRefCode();
		this.remark = bizProcess.getRemark();
		this.procStatus = bizProcess.getProcStatus();
		this.procSubStatus = bizProcess.getProcSubStatus();
		this.operId = bizProcess.getOperId();
		this.operCode = bizProcess.getOperCode();
		this.operName = bizProcess.getOperName();
		this.operOrgId = bizProcess.getOperOrgId();
		this.operOrgCode = bizProcess.getOperOrgCode();
		this.operOrgName = bizProcess.getOperOrgName();
		this.disabledFlag = bizProcess.getDisabledFlag();
		this.rejectFlag = bizProcess.getRejectFlag();
		this.url = bizProcess.getUrl();
		this.ipAddress = bizProcess.getIpAddress();
		this.setCreateTime(new java.util.Date());
		this.setUpdateTime(new java.util.Date());
	}

	/**
	 * 辅助说明
	 * 
	 * @return
	 */
	public String getAssDesc() {
		return assDesc;
	}

	/**
	 * 创建时间
	 * 
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 数据内容
	 */
	public String getDataContent() {
		return dataContent;
	}

	/**
	 * 数据ID
	 * 
	 * @return
	 */
	public String getDataId() {
		return dataId;
	}

	/**
	 * 数据名称
	 * 
	 * @return
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * 数据类型
	 * 
	 * @return
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 交易代码
	 * 
	 * @return
	 */
	public String getDealCode() {
		return dealCode;
	}

	/**
	 * 交易名称
	 * 
	 * @return
	 */
	public String getDealName() {
		return dealName;
	}

	/**
	 * 失效标志
	 * 
	 * @return
	 */
	public String getDisabledFlag() {
		return disabledFlag;
	}

	/**
	 * 实体类别
	 * 
	 * @return
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * 功能代码
	 * 
	 * @return
	 */
	public String getFuncCode() {
		return funcCode;
	}

	/**
	 * 功能名称
	 * 
	 * @return the funcName
	 */
	public String getFuncName() {
		return funcName;
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
	 * 模块代码
	 * 
	 * @return
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * 操作者代码
	 * 
	 * @return
	 */
	public String getOperCode() {
		return operCode;
	}

	/**
	 * 操作者ID
	 * 
	 * @return
	 */
	public String getOperId() {
		return operId;
	}

	/**
	 * 操作者名称
	 * 
	 * @return
	 */
	public String getOperName() {
		return operName;
	}

	/**
	 * 操作者所属组织代码
	 * 
	 * @return
	 */
	public String getOperOrgCode() {
		return operOrgCode;
	}

	/**
	 * 操作者所属组织ID
	 * 
	 * @return
	 */
	public String getOperOrgId() {
		return operOrgId;
	}

	/**
	 * 操作者所属组织名称
	 * 
	 * @return
	 */
	public String getOperOrgName() {
		return operOrgName;
	}

	/**
	 * 处理代码
	 * 
	 * @return
	 */
	public String getProcessCode() {
		return processCode;
	}

	/**
	 * 处理名称
	 * 
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * 处理状态
	 * 
	 * @return
	 */
	public String getProcStatus() {
		return procStatus;
	}

	/**
	 * 处理状态描述
	 * 
	 * @return
	 */
	public String getProcStatusDesc() {
		return procStatusDesc;
	}

	/**
	 * 处理子状态
	 * 
	 * @return String
	 */
	public String getProcSubStatus() {
		return procSubStatus;
	}

	/**
	 * 关联代码
	 * 
	 * @return
	 */
	public String getRefCode() {
		return refCode;
	}

	public String getRejectFlag() {
		return rejectFlag;
	}

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 修改时间
	 * 
	 * @return
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	public String getUrl() {
		return url;
	}

	/**
	 * 辅助说明
	 * 
	 * @param assDesc
	 */
	public void setAssDesc(String assDesc) {
		this.assDesc = assDesc;
	}

	/**
	 * 创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 数据内容
	 * 
	 * @param dataContent
	 */
	public void setDataContent(String dataContent) {
		this.dataContent = dataContent;
	}

	/**
	 * 数据ID
	 * 
	 * @param dataId
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	/**
	 * 数据名称
	 * 
	 * @param dataName
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * 数据类型
	 * 
	 * @param dataType
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 交易代码
	 * 
	 * @param dealCode
	 */
	public void setDealCode(String dealCode) {
		this.dealCode = dealCode;
	}

	/**
	 * 交易名称
	 * 
	 * @param dealName
	 */
	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	/**
	 * 失效标志
	 * 
	 * @param disabledFlag
	 */
	public void setDisabledFlag(String disabledFlag) {
		this.disabledFlag = disabledFlag;
	}

	/**
	 * 实体类别
	 * 
	 * @param entityType
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * 功能代码
	 * 
	 * @param funcCode
	 */
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	/**
	 * 功能名称
	 * 
	 * @param funcName
	 *            the funcName to set
	 */
	public void setFuncName(String funcName) {
		this.funcName = funcName;
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
	 * 模块代码
	 * 
	 * @param moduleCode
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	/**
	 * 操作者代码
	 * 
	 * @param operCode
	 */
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	/**
	 * 操作者ID
	 * 
	 * @param operId
	 */
	public void setOperId(String operId) {
		this.operId = operId;
	}

	/**
	 * 操作者名称
	 * 
	 * @param operName
	 */
	public void setOperName(String operName) {
		this.operName = operName;
	}

	/**
	 * 操作者所属组织代码
	 * 
	 * @param operOrgCode
	 */
	public void setOperOrgCode(String operOrgCode) {
		this.operOrgCode = operOrgCode;
	}

	/**
	 * 操作者所属组织ID
	 * 
	 * @param operOrgId
	 */
	public void setOperOrgId(String operOrgId) {
		this.operOrgId = operOrgId;
	}

	/**
	 * 操作者所属组织名称
	 * 
	 * @param operOrgName
	 */
	public void setOperOrgName(String operOrgName) {
		this.operOrgName = operOrgName;
	}

	/**
	 * 处理代码
	 * 
	 * @param processCode
	 */
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	/**
	 * 处理名称
	 * 
	 * @param processName
	 *            the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * 处理状态
	 * 
	 * @param statusFlag
	 */
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	/**
	 * 处理状态描述
	 * 
	 * @param procStatusDesc
	 */
	public void setProcStatusDesc(String procStatusDesc) {
		this.procStatusDesc = procStatusDesc;
	}

	/**
	 * 处理子状态
	 * 
	 * @param procSubStatus
	 *            void
	 */
	public void setProcSubStatus(String procSubStatus) {
		this.procSubStatus = procSubStatus;
	}

	/**
	 * 关联代码
	 * 
	 * @param refCode
	 */
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public void setRejectFlag(String rejectFlag) {
		this.rejectFlag = rejectFlag;
	}

	/**
	 * 备注
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 修改时间
	 * 
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 覆盖toStirng
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("entityType=").append(this.entityType);
		s.append(",moduleCode=").append(this.moduleCode);
		s.append(",processCode=").append(this.processCode);
		s.append(",processName=").append(this.processName);
		s.append(",funcCode=").append(this.funcCode);
		s.append(",funcName=").append(this.funcName);
		s.append(",dealCode=").append(this.dealCode);
		s.append(",remark=").append(remark);
		s.append(",operId=").append(this.operId);
		s.append(",operName=").append(this.operName);
		s.append(",operOrgId=").append(operOrgId);
		s.append(",operOrgName=").append(operOrgName);
		s.append(";").append(super.toString());
		return s.toString();
	}

}
