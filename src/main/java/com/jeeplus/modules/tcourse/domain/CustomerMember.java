package com.jeeplus.modules.tcourse.domain;

import com.yfhl.commons.domain.BizEntity;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年3月13日
 * @Description: 用户账户表
 */
public class CustomerMember extends BizEntity{

	private static final long serialVersionUID = 1L;

	public CustomerMember(){}
	
	//用户编码，也就是cifId
	private String userCode;
	
	//mt4交易帐号
	private String mtId;
	
	//交易杠杆
	private String tradeLeverage;
	
	//账户类型，取值于字典
	private String accountType;
	
	//开户来源。''1:外汇官网 2:个人账户中心 3: 微信接口 4: 外汇后台 5:移动端 6:战国策
	private String openAccountSource;
	
	//所属代理商组别，与返佣相关
	private String agentIbGroup;
	
	//隐藏状态，控制个人账户中心列表显示。默认：0。0：未隐藏；1：已隐藏
	private String hiddenFlag;
	
	//只读标志，控制mt4交易。默认：0。0：正常；1：只读。如果未上传地址证明，帐号则为只读状态
	private String readonlyFlag;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMtId() {
		return mtId;
	}

	public void setMtId(String mtId) {
		this.mtId = mtId;
	}

	public String getTradeLeverage() {
		return tradeLeverage;
	}

	public void setTradeLeverage(String tradeLeverage) {
		this.tradeLeverage = tradeLeverage;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getOpenAccountSource() {
		return openAccountSource;
	}

	public void setOpenAccountSource(String openAccountSource) {
		this.openAccountSource = openAccountSource;
	}

	public String getAgentIbGroup() {
		return agentIbGroup;
	}

	public void setAgentIbGroup(String agentIbGroup) {
		this.agentIbGroup = agentIbGroup;
	}

	public String getHiddenFlag() {
		return hiddenFlag;
	}

	public void setHiddenFlag(String hiddenFlag) {
		this.hiddenFlag = hiddenFlag;
	}

	public String getReadonlyFlag() {
		return readonlyFlag;
	}

	public void setReadonlyFlag(String readonlyFlag) {
		this.readonlyFlag = readonlyFlag;
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer();
		s.append(",userCode=").append(userCode);
		s.append(",mtId=").append(mtId);
		s.append(",tradeLeverage=").append(tradeLeverage);
		s.append(",accountTpye=").append(accountType);
		s.append(",openAccountSource=").append(openAccountSource);
		s.append(",agentIbGroup=").append(agentIbGroup);
		s.append(",hiddenFlag=").append(hiddenFlag);
		s.append(",readonlyFlag=").append(readonlyFlag);
		s.append(super.toString());
		return s.toString();
	}
}
