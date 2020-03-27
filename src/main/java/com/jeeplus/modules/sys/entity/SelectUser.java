/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import java.util.List;

import com.jeeplus.common.persistence.DataEntity;


/**
 * 用户Entity
 * @author jeeplus
 * @version 2013-12-05
 */
public class SelectUser extends DataEntity<SelectUser> {

	private static final long serialVersionUID = 1L;
	private String name;	// 姓名
	private String officeIds;//机构ID
	private String officeLevel;//机构等级
	private String areaId;//大区ID
	private String isCheckAll;//是否全选
	private String officeTypeId;//机构分类
	private String grade;
	
	private String officeIdXx;
	
	public String getOfficeIdXx() {
		return officeIdXx;
	}
	public void setOfficeIdXx(String officeIdXx) {
		this.officeIdXx = officeIdXx;
	}
	/*******************************必学用*******************************************/
	// ygq 2017-04-30 添加的字段信息
	private String[] officeIdBx;//机构ID
	
	private String isBxPostIdsCheckAll;//必学的岗位是否选中
	private String[] postIdsBx;//岗位id
	
	/***************************选学用**************************************/
	private String[] userIdsBx;//必选学员的ids
	private String isCheckAllxx;
	
	private String[]  officeIdsxx;//选学学员的组织ids
	
	private String[]  postIdsXx;//选学学员的岗位id
	private String isXxPostIdsCheckAll;//选学的岗位是否选中

	/****************************修改用**************************************/
	private String isChecked;
	private String activityId;
	
	private String searchType;//查询用
	private String courseId;//课程id
	
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getIsBxPostIdsCheckAll() {
		return isBxPostIdsCheckAll;
	}
	public void setIsBxPostIdsCheckAll(String isBxPostIdsCheckAll) {
		this.isBxPostIdsCheckAll = isBxPostIdsCheckAll;
	}
	public String getIsXxPostIdsCheckAll() {
		return isXxPostIdsCheckAll;
	}
	public void setIsXxPostIdsCheckAll(String isXxPostIdsCheckAll) {
		this.isXxPostIdsCheckAll = isXxPostIdsCheckAll;
	}
	public String[] getPostIdsXx() {
		return postIdsXx;
	}
	public void setPostIdsXx(String[] postIdsXx) {
		this.postIdsXx = postIdsXx;
	}
	public String[] getPostIdsBx() {
		return postIdsBx;
	}
	public void setPostIdsBx(String[] postIdsBx) {
		this.postIdsBx = postIdsBx;
	}
	public String[] getOfficeIdBx() {
		return officeIdBx;
	}
	public void setOfficeIdBx(String[] officeIdBx) {
		this.officeIdBx = officeIdBx;
	}
	public String[] getOfficeIdsxx() {
		return officeIdsxx;
	}
	public void setOfficeIdsxx(String[] officeIdsxx) {
		this.officeIdsxx = officeIdsxx;
	}
	public String[] getUserIdsBx() {
		return userIdsBx;
	}
	public void setUserIdsBx(String[] userIdsBx) {
		this.userIdsBx = userIdsBx;
	}
	public String getIsCheckAllxx() {
		return isCheckAllxx;
	}
	public void setIsCheckAllxx(String isCheckAllxx) {
		this.isCheckAllxx = isCheckAllxx;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	private List<String> itmesIds;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOfficeIds() {
		return officeIds;
	}
	public void setOfficeIds(String officeIds) {
		this.officeIds = officeIds;
	}
	public String getOfficeLevel() {
		return officeLevel;
	}
	public void setOfficeLevel(String officeLevel) {
		this.officeLevel = officeLevel;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getIsCheckAll() {
		return isCheckAll;
	}
	public void setIsCheckAll(String isCheckAll) {
		this.isCheckAll = isCheckAll;
	}
	public String getOfficeTypeId() {
		return officeTypeId;
	}
	public void setOfficeTypeId(String officeTypeId) {
		this.officeTypeId = officeTypeId;
	}
	public List<String> getItmesIds() {
		return itmesIds;
	}
	public void setItmesIds(List<String> itmesIds) {
		this.itmesIds = itmesIds;
	}
	
}