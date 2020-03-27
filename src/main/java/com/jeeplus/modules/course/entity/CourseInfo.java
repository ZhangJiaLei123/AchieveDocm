/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/;JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 课程管理Entity
 * @author panjp
 * @version 2017-03-21
 */
public class CourseInfo extends DataEntity<CourseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String couName;		// 课程名称
	private String couDescribe;		// 描述
	private String resourceId;		//资源ID
	private String resResource;		// 资源信息
	private String resResourceName; //资源名称
	private Date regStartTime;		// 报名开始时间
	private Date regEndTime;		// 报名结束时间
	private Date stuStartTime;		// 学习开始时间
	private Date stuEndTime;		// 学习结束时间
	private String isBxUser;		// 是否选择必学学员
	private String isXxUser;		// 是否选择选学学员
	private String stuImg;		// 课程图片
	private String userRole;		// 人员岗位
	private String couStatus;		// 课程信息状态
	private String byStandard;	//通过标准
	private Float byMark;//通过分数
	
	private String isImportUser;//是否导入学员
	private String isImportXxUser;//是否导入选学学员

	private String isCreateAdmin;//是否管理员添加
	private String userId;//用戶ID
	private String courseTime;//学习时长
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourseTime() {
		if(courseTime !=null && !"".equals(courseTime) && !"0".equals(courseTime)){
			int t =(int)Float.parseFloat(courseTime);
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

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
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
	
	private String userIdsBx;//必学学员ID
	private String postIdsBx;//必学人员岗位ID
	private String officIdsBx;//必学学员机构组织ID
	
	private String userIdsXx;//选学学员ID
	private String postIdsXx;//选学人员岗位ID
	private String officIdsXx;//选学学员机构组织ID
	
	private String resourceStatus;//审核状态
	public List<UserCourse> userCourseList;//员工岗位
	
	private String isCheckAllBx;//是否全选必学组织
	private String isCheckOfficeTypeBx; //全选必学组织类别
	private String isCheckOfficeLevelBx; //全选必学组织级别
	private String isCheckPostBx; //全选必学组织机构
	
	private String isCheckAllXx;//是否全选选学组织
	private String isCheckOfficeTypeXx; //全选选学组织类别
	private String isCheckOfficeLevelXx; //全选选学组织类别
	private String isCheckPostXx;//全选选学组织类别
	
	private String isFb;//是否发布，1发布，0或空未发布

	private String teacher;//增加讲师字段id
	private String actUser;//增加讲师字段名字
	
	public String getActUser() {
		return actUser;
	}
	public void setActUser(String actUser) {
		this.actUser = actUser;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public CourseInfo() {
		super();
	}

	public CourseInfo(String id){
		super(id);
	}

	@Length(min=0, max=200, message="课程名称长度必须介于 0 和 200 之间")
	@ExcelField(title="课程名称", align=2, sort=7)
	public String getCouName() {
		return couName;
	}

	public void setCouName(String couName) {
		this.couName = couName;
	}
	
	@Length(min=0, max=200, message="描述长度必须介于 0 和 200 之间")
	@ExcelField(title="描述", align=2, sort=8)
	public String getCouDescribe() {
		return couDescribe;
	}

	public void setCouDescribe(String couDescribe) {
		this.couDescribe = couDescribe;
	}
	
	@Length(min=0, max=64, message="资源信息长度必须介于 0 和 64 之间")
	@ExcelField(title="资源信息", align=2, sort=9)
	public String getResResource() {
		return resResource;
	}

	public void setResResource(String resResource) {
		this.resResource = resResource;
	}
	
	
	
	@Length(min=0, max=10, message="是否选择学员长度必须介于 0 和 10 之间")
	@ExcelField(title="是否选择学员", align=2, sort=14)
	public String getByStandard() {
		return byStandard;
	}

	public void setByStandard(String byStandard) {
		this.byStandard = byStandard;
	}

	public Float getByMark() {
		if(null == byMark){
			return 0f;
		}
		return byMark;
	}

	public void setByMark(Float byMark) {
		this.byMark = byMark;
	}
	
	@Length(min=0, max=255, message="课程图片长度必须介于 0 和 255 之间")
	@ExcelField(title="课程图片", align=2, sort=15)
	public String getStuImg() {
		return stuImg;
	}

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

	public void setStuImg(String stuImg) {
		this.stuImg = stuImg;
	}
	
	@Length(min=0, max=200, message="人员岗位长度必须介于 0 和 200 之间")
	@ExcelField(title="人员岗位", align=2, sort=16)
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	@Length(min=0, max=10, message="课程信息状态长度必须介于 0 和 10 之间")
	@ExcelField(title="课程信息状态", align=2, sort=17)
	public String getCouStatus() {
		return couStatus;
	}

	public void setCouStatus(String couStatus) {
		this.couStatus = couStatus;
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

	public String getResResourceName() {
		return resResourceName;
	}

	public void setResResourceName(String resResourceName) {
		this.resResourceName = resResourceName;
	}

	public Date getRegStartTime() {
		return regStartTime;
	}

	public void setRegStartTime(Date regStartTime) {
		this.regStartTime = regStartTime;
	}

	public Date getRegEndTime() {
		return regEndTime;
	}

	public void setRegEndTime(Date regEndTime) {
		this.regEndTime = regEndTime;
	}

	public Date getStuStartTime() {
		return stuStartTime;
	}

	public void setStuStartTime(Date stuStartTime) {
		this.stuStartTime = stuStartTime;
	}

	public Date getStuEndTime() {
		return stuEndTime;
	}

	public void setStuEndTime(Date stuEndTime) {
		this.stuEndTime = stuEndTime;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public List<UserCourse> getUserCourseList() {
		return userCourseList;
	}

	public void setUserCourseList(List<UserCourse> userCourseList) {
		this.userCourseList = userCourseList;
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

	public String getIsFb() {
		return isFb;
	}

	public void setIsFb(String isFb) {
		this.isFb = isFb;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getIsCreateAdmin() {
		return isCreateAdmin;
	}

	public void setIsCreateAdmin(String isCreateAdmin) {
		this.isCreateAdmin = isCreateAdmin;
	}

}