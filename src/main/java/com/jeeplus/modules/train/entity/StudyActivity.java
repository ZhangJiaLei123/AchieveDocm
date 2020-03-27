/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 学习活动Entity
 * @author panjp
 * @version 2017-03-25
 */
public class StudyActivity extends DataEntity<StudyActivity> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 活动名称
	private String space;		// 活动地点
	private String userCount;		// 人数
	private String actImg;		// 活动图片
	private String actType;		// 活动类型
	private String actSort;		// 活动类别
	private String actUser;		// 讲师
	private Date studyEndTime;		// 开课开始时间
	private Date studyStartTime;		// 开课结束时间
	private Date applyStartTime;		// 报名开始时间
	private Date applyEndTime;		// 报名结束时间

	private String isImportUser;//是否导入学员
	private String isImportXxUser;//是否导入选学学员
	
	private String userId;//用戶ID
	private String studyTime;//学习时长
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStudyTime() {
		if(studyTime !=null && !"".equals(studyTime) && !"0".equals(studyTime)){
			int t =(int)Float.parseFloat(studyTime);
			String str =  (t/3600)+"时"+(t%3600)/60+"分";
			if("0时0分".equals(str)){
				return null;
			}else{
				return str;
			}
		}else{
			return null;
		}
	}

	public void setStudyTime(String studyTime) {
		this.studyTime = studyTime;
	}

	public String getIsImportXxUser() {
		return isImportXxUser;
	}

	public void setIsImportXxUser(String isImportXxUser) {
		this.isImportXxUser = isImportXxUser;
	}

	public String getIsImportUser() {
		return isImportUser;
	}

	public void setIsImportUser(String isImportUser) {
		this.isImportUser = isImportUser;
	}
	private String isBxUser;		// 是否选择必学学员
	private String isXxUser;		// 是否选择选学学员
	private String teacher;			//讲师
	
	private String userIdsBx;//必学学员ID
	private String postIdsBx;//必学人员岗位ID
	private String officIdsBx;//必学学员机构组织ID
	
	private String userIdsXx;//选学学员ID
	private String postIdsXx;//选学人员岗位ID
	private String officIdsXx;//选学学员机构组织ID
	
	private String isCheckAllBx;//是否全选必学组织
	private String isCheckOfficeTypeBx; //全选必学组织类别
	private String isCheckOfficeLevelBx; //全选必学组织级别
	private String isCheckPostBx; //全选必学组织机构
	
	private String isCheckAllXx;//是否全选选学组织
	private String isCheckOfficeTypeXx; //全选选学组织类别
	private String isCheckOfficeLevelXx; //全选选学组织类别
	private String isCheckPostXx;//全选选学组织类别
	
	private String resourceStatus;//活动审核状态
	
	private String isCreateAdmin;//是否管理员添加
	
	public String getIsCreateAdmin() {
		return isCreateAdmin;
	}

	public void setIsCreateAdmin(String isCreateAdmin) {
		this.isCreateAdmin = isCreateAdmin;
	}

	public StudyActivity() {
		super();
	}

	public StudyActivity(String id){
		super(id);
	}

	@Length(min=0, max=200, message="活动名称长度必须介于 0 和 200 之间")
	@ExcelField(title="活动名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="活动地点长度必须介于 0 和 200 之间")
	@ExcelField(title="活动地点", align=2, sort=8)
	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}
	
	@ExcelField(title="人数", align=2, sort=9)
	public String getUserCount() {
		return userCount;
	}

	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}
	
	@Length(min=0, max=300, message="活动图片长度必须介于 0 和 300 之间")
	@ExcelField(title="活动图片", align=2, sort=10)
	public String getActImg() {
		return actImg;
	}

	public void setActImg(String actImg) {
		this.actImg = actImg;
	}
	
	@Length(min=0, max=10, message="活动类型长度必须介于 0 和 10 之间")
	@ExcelField(title="活动类型", dictType="", align=2, sort=11)
	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}
	
	@Length(min=0, max=10, message="活动类别长度必须介于 0 和 10 之间")
	@ExcelField(title="活动类别", dictType="", align=2, sort=12)
	public String getActSort() {
		return actSort;
	}

	public void setActSort(String actSort) {
		this.actSort = actSort;
	}
	
	@Length(min=0, max=64, message="讲师长度必须介于 0 和 64 之间")
	@ExcelField(title="讲师", align=2, sort=13)
	public String getActUser() {
		return actUser;
	}

	public void setActUser(String actUser) {
		this.actUser = actUser;
	}
	
	@ExcelField(title="开课开始时间", align=2, sort=14)
	public Date getStudyEndTime() {
		return studyEndTime;
	}

	public void setStudyEndTime(Date studyEndTime) {
		this.studyEndTime = studyEndTime;
	}
	
	@ExcelField(title="开课结束时间", align=2, sort=15)
	public Date getStudyStartTime() {
		return studyStartTime;
	}

	public void setStudyStartTime(Date studyStartTime) {
		this.studyStartTime = studyStartTime;
	}
	
	@ExcelField(title="报名开始时间", align=2, sort=16)
	public Date getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}
	
	@ExcelField(title="报名结束时间", align=2, sort=17)
	public Date getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	
	@Length(min=0, max=10, message="是否选择必学学员长度必须介于 0 和 10 之间")
	@ExcelField(title="是否选择必学学员", align=2, sort=18)
	public String getIsBxUser() {
		return isBxUser;
	}

	public void setIsBxUser(String isBxUser) {
		this.isBxUser = isBxUser;
	}
	
	public String getIsXxUser() {
		return isXxUser;
	}

	public void setIsXxUser(String isXxUser) {
		this.isXxUser = isXxUser;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getUserIdsBx() {
		return userIdsBx;
	}

	public void setUserIdsBx(String userIdsBx) {
		this.userIdsBx = userIdsBx;
	}

	public String getPostIdsBx() {
		return postIdsBx;
	}

	public void setPostIdsBx(String postIdsBx) {
		this.postIdsBx = postIdsBx;
	}

	public String getOfficIdsBx() {
		return officIdsBx;
	}

	public void setOfficIdsBx(String officIdsBx) {
		this.officIdsBx = officIdsBx;
	}

	public String getUserIdsXx() {
		return userIdsXx;
	}

	public void setUserIdsXx(String userIdsXx) {
		this.userIdsXx = userIdsXx;
	}

	public String getPostIdsXx() {
		return postIdsXx;
	}

	public void setPostIdsXx(String postIdsXx) {
		this.postIdsXx = postIdsXx;
	}

	public String getOfficIdsXx() {
		return officIdsXx;
	}

	public void setOfficIdsXx(String officIdsXx) {
		this.officIdsXx = officIdsXx;
	}

	public String getIsCheckAllBx() {
		return isCheckAllBx;
	}

	public void setIsCheckAllBx(String isCheckAllBx) {
		this.isCheckAllBx = isCheckAllBx;
	}

	public String getIsCheckOfficeTypeBx() {
		return isCheckOfficeTypeBx;
	}

	public void setIsCheckOfficeTypeBx(String isCheckOfficeTypeBx) {
		this.isCheckOfficeTypeBx = isCheckOfficeTypeBx;
	}

	public String getIsCheckOfficeLevelBx() {
		return isCheckOfficeLevelBx;
	}

	public void setIsCheckOfficeLevelBx(String isCheckOfficeLevelBx) {
		this.isCheckOfficeLevelBx = isCheckOfficeLevelBx;
	}

	public String getIsCheckPostBx() {
		return isCheckPostBx;
	}

	public void setIsCheckPostBx(String isCheckPostBx) {
		this.isCheckPostBx = isCheckPostBx;
	}

	public String getIsCheckAllXx() {
		return isCheckAllXx;
	}

	public void setIsCheckAllXx(String isCheckAllXx) {
		this.isCheckAllXx = isCheckAllXx;
	}

	public String getIsCheckOfficeTypeXx() {
		return isCheckOfficeTypeXx;
	}

	public void setIsCheckOfficeTypeXx(String isCheckOfficeTypeXx) {
		this.isCheckOfficeTypeXx = isCheckOfficeTypeXx;
	}

	public String getIsCheckOfficeLevelXx() {
		return isCheckOfficeLevelXx;
	}

	public void setIsCheckOfficeLevelXx(String isCheckOfficeLevelXx) {
		this.isCheckOfficeLevelXx = isCheckOfficeLevelXx;
	}

	public String getIsCheckPostXx() {
		return isCheckPostXx;
	}

	public void setIsCheckPostXx(String isCheckPostXx) {
		this.isCheckPostXx = isCheckPostXx;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}
	
}