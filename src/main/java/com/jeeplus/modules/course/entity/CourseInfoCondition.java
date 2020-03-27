/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.entity;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.Area;

/**
 * 查询条件Entity
 * @author ygq
 * @version 2017-04-26
 */
public class CourseInfoCondition extends DataEntity<CourseInfoCondition> {
	
	private static final long serialVersionUID = 1L;
	private String orgCheckAll;		// 是否选择必学学员(全选)
	private String orgType;		// 机构类别
	public String getOrgTypeName() {
		return orgTypeName;
	}
	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}

	private String orgTypeName;//机构类别名称
	private String orgLevel;		// 机构等级
	private String officeName;//机构名称
	
	private Area area;		// 区域id
	private String postCheckAll;//岗位全选
	
	private String postName;		// 岗位名称
	private String postType;		// 岗位类别
	private String userCheckAll;		// 人员全选
	private String username;		// 用户名
	private String type;		// 1.必选学员，2.全选学员
	private String courseId;		// 学习活动id
	private String isBxUser;

	
	private String distrctId;//所属区域ID
	private String provinceId;//所属省ID
	private String cityId;//所属城市ID
	
	private String officIds;//选中组织ID
	private String postIds;//选中岗位ID
	private String userBxPost;//选中岗位名称
	
	
	
	public String getDistrctId() {
		return distrctId;
	}
	public void setDistrctId(String distrctId) {
		this.distrctId = distrctId;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getIsBxUser() {
		return isBxUser;
	}
	public void setIsBxUser(String isBxUser) {
		this.isBxUser = isBxUser;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getPostCheckAll() {
		return postCheckAll;
	}
	public void setPostCheckAll(String postCheckAll) {
		this.postCheckAll = postCheckAll;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public CourseInfoCondition() {
		super();
	}
	public CourseInfoCondition(String id){
		super(id);
	}
	public String getOrgCheckAll() {
		return orgCheckAll;
	}

	public void setOrgCheckAll(String orgCheckAll) {
		this.orgCheckAll = orgCheckAll;
	}
	
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	
	public String getUserCheckAll() {
		return userCheckAll;
	}

	public void setUserCheckAll(String userCheckAll) {
		this.userCheckAll = userCheckAll;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getOfficIds() {
		return officIds;
	}
	public void setOfficIds(String officIds) {
		this.officIds = officIds;
	}
	public String getPostIds() {
		return postIds;
	}
	public void setPostIds(String postIds) {
		this.postIds = postIds;
	}
	public String getUserBxPost() {
		return userBxPost;
	}
	public void setUserBxPost(String userBxPost) {
		this.userBxPost = userBxPost;
	}
	
}