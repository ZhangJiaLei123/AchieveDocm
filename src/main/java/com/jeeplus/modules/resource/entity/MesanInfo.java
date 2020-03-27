/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资料信息Entity
 * @author panjp
 * @version 2017-03-20
 */
public class MesanInfo extends DataEntity<MesanInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 资料名称
	private String mesanUrl;		// 资料路径
	private String approvalStatus; //审核状态
	private String activityId;//活动ID
	private String activityDirId;//活动目录Id
	private String isCreateAdmin;//是否管理员添加
	private String mesanType;//资料分类
	private String mesanTypeName;//资料分类名称
	
	private String mesanCode;//资料编码
	private String isViewTop;//是否置顶
	private String swfUrl;//在线预览路径
	private Double evaluateScore;//用户评分
	private Integer count;//总共评论数目
	private Integer downLoadCount;//下载次数
	private String browseTime;// 浏览时长
	private Integer browseCount;//浏览次数
	private String userId;//用户ID
	private String downLoadStatus;
	
	private String contentDesc;//内容概要
	
	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public String getDownLoadStatus() {
		return downLoadStatus;
	}

	public void setDownLoadStatus(String downLoadStatus) {
		this.downLoadStatus = downLoadStatus;
	}

	public String getMesanTypeName() {
		return mesanTypeName;
	}

	public void setMesanTypeName(String mesanTypeName) {
		this.mesanTypeName = mesanTypeName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}

	public Double getEvaluateScore() {
		return evaluateScore;
	}

	public void setEvaluateScore(Double evaluateScore) {
		this.evaluateScore = evaluateScore;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSwfUrl() {
		return swfUrl;
	}

	public void setSwfUrl(String swfUrl) {
		this.swfUrl = swfUrl;
	}

	public String getIsCreateAdmin() {
		return isCreateAdmin;
	}

	public void setIsCreateAdmin(String isCreateAdmin) {
		this.isCreateAdmin = isCreateAdmin;
	}

	public MesanInfo() {
		super();
	}

	public MesanInfo(String id){
		super(id);
	}

	public String getMesanType() {
		return mesanType;
	}

	public void setMesanType(String mesanType) {
		this.mesanType = mesanType;
	}

	public String getMesanCode() {
		return mesanCode;
	}

	public void setMesanCode(String mesanCode) {
		this.mesanCode = mesanCode;
	}

	@Length(min=1, max=200, message="资料名称长度必须介于 1 和 200 之间")
	@ExcelField(title="资料名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=300, message="资料路径长度必须介于 0 和 64 之间")
	@ExcelField(title="资料路径", align=2, sort=8)
	public String getMesanUrl() {
		return mesanUrl;
	}

	public void setMesanUrl(String mesanUrl) {
		this.mesanUrl = mesanUrl;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityDirId() {
		return activityDirId;
	}

	public void setActivityDirId(String activityDirId) {
		this.activityDirId = activityDirId;
	}

	public String getIsViewTop() {
		return isViewTop;
	}

	public void setIsViewTop(String isViewTop) {
		this.isViewTop = isViewTop;
	}

	public Integer getDownLoadCount() {
		return downLoadCount;
	}

	public void setDownLoadCount(Integer downLoadCount) {
		this.downLoadCount = downLoadCount;
	}

	public String getBrowseTime() {
		if(browseTime !=null && !"".equals(browseTime) && !"0".equals(browseTime)){
			int t =(int)Float.parseFloat(browseTime);
			return (t/3600)+"时"+(t%3600)/60+"分";
		}else{
			return null;
		}
	}

	public void setBrowseTime(String browseTime) {
		this.browseTime = browseTime;
	}
	
}