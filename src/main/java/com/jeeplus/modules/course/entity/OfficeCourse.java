/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 组织课程关系Entity
 * @author panjp
 * @version 2017-03-24
 */
public class OfficeCourse extends DataEntity<OfficeCourse> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// 组织ID
	private String courseId;		// 课程ID
	private String type;		// 关系类别
	private String name;//课程名称
	private String officeName;//机构名称
	private String officeTypeName;//机构类别名称
	private String officeTypeId;//机构类别ID
	private String officeGrade;//机构等级
	
	private String dq;//大区
	private String sf;//市区
	private String sq;//省份
	
	private String sqId;//市区ID
	
	public OfficeCourse() {
		super();
	}

	public OfficeCourse(String id){
		super(id);
	}

	@Length(min=0, max=64, message="组织ID长度必须介于 0 和 64 之间")
	@ExcelField(title="组织ID", align=2, sort=7)
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=64, message="课程ID长度必须介于 0 和 64 之间")
	@ExcelField(title="课程ID", align=2, sort=8)
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@Length(min=0, max=10, message="关系类别长度必须介于 0 和 10 之间")
	@ExcelField(title="关系类别", align=2, sort=9)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOfficeTypeName() {
		return officeTypeName;
	}

	public void setOfficeTypeName(String officeTypeName) {
		this.officeTypeName = officeTypeName;
	}

	public String getOfficeTypeId() {
		return officeTypeId;
	}

	public void setOfficeTypeId(String officeTypeId) {
		this.officeTypeId = officeTypeId;
	}

	public String getDq() {
		return dq;
	}

	public void setDq(String dq) {
		this.dq = dq;
	}

	public String getSf() {
		return sf;
	}

	public void setSf(String sf) {
		this.sf = sf;
	}

	public String getSq() {
		return sq;
	}

	public void setSq(String sq) {
		this.sq = sq;
	}

	public String getSqId() {
		return sqId;
	}

	public void setSqId(String sqId) {
		this.sqId = sqId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeGrade() {
		return officeGrade;
	}

	public void setOfficeGrade(String officeGrade) {
		this.officeGrade = officeGrade;
	}
	
}