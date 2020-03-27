/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.TreeEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 机构Entity
 * @author jeeplus
 * @version 2013-05-15
 */
public class Office extends TreeEntity<Office> {

	private static final long serialVersionUID = -121312313L;
//	private Office parent;	// 父级编号
//	private String parentIds; // 所有父级编号
	private Area area;		// 归属区域
	private String areaId;//归属区域Id
	private String areaName;
	private String code; 	// 机构编码
	private String name; 	// 机构名称
//	private Integer sort;		// 排序
	private String type; 	// 机构类型（1：公司；2：部门；3：小组）
	private String grade; 	// 机构等级（1：一级；2：二级；3：三级；4：四级）
	private String address; // 联系地址
	private String zipCode; // 邮政编码
	private String master; 	// 负责人
	private String phone; 	// 电话
	private String fax; 	// 传真
	private String email; 	// 邮箱
	private String useable;//是否可用
	private User primaryPerson;//主负责人
	private User deputyPerson;//副负责人
	private List<String> childDeptList;//快速添加子部门
	
	private String SQ;//市区
	private String SF;//省份
	private String DQ;//大区
	private String fedLevel;//fed等级
	private String parentName;
	private String xsCode;//销售代码
	private String legalPerson;//法人
	private String contacts;//联系人
	private String areaCode;//区号
	private String brand;//品牌
	private String phone24;//24销售热线
	private String officeType;//机构分类
	private String officeTypeName;//机构分类名称

	/*************************页面查询用的筛选条件******************************************/
	private String[] officeIdsBx;//已经选中的组织id
	
	private String checked;//是否被选中
	
	/*********************************修改用**************************************************/
	private String activityId;
	/***************查询用*****************/
	private String officeTypeId;//机构分类
	
	
	private String courseId;
	
	private String userCourseType;
	
	private String distrctId;//所属区域ID
	private String distrctName;//所属区域名称
	private String provinceId;//所属省ID
	private String cityId;//所属城市ID
	private String provinceName;//所属省名称
	private String cityName;//所属城市名称
	
	
	public String getOfficeTypeId() {
		return officeTypeId;
	}

	public void setOfficeTypeId(String officeTypeId) {
		this.officeTypeId = officeTypeId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String[] getOfficeIdsBx() {
		return officeIdsBx;
	}

	public void setOfficeIdsBx(String[] officeIdsBx) {
		this.officeIdsBx = officeIdsBx;
	}

	public Office(){
		super();
//		this.sort = 30;
		this.type = "2";
	}

	public Office(String id){
		super(id);
	}
	
	public List<String> getChildDeptList() {
		return childDeptList;
	}

	public void setChildDeptList(List<String> childDeptList) {
		this.childDeptList = childDeptList;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public User getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(User primaryPerson) {
		this.primaryPerson = primaryPerson;
	}

	public User getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(User deputyPerson) {
		this.deputyPerson = deputyPerson;
	}

//	@JsonBackReference
//	@NotNull
	public Office getParent() {
		return parent;
	}

	public void setParent(Office parent) {
		this.parent = parent;
	}
//
//	@Length(min=1, max=2000)
//	public String getParentIds() {
//		return parentIds;
//	}
//
//	public void setParentIds(String parentIds) {
//		this.parentIds = parentIds;
//	}

	/*@NotNull*/
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Length(min=1, max=100)
	@ExcelField(title="机构名称", align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
//
//	public Integer getSort() {
//		return sort;
//	}
//
//	public void setSort(Integer sort) {
//		this.sort = sort;
//	}
	
	@Length(min=1, max=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min=1, max=1)
	public String getGrade() {
		if("null".equals(grade)){
			return null;
		}
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Length(min=0, max=255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min=0, max=100)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min=0, max=100)
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Length(min=0, max=200)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=200)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Length(min=0, max=200)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min=0, max=100)
	@ExcelField(title="机构编码", align=2, sort=20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getSQ() {
		return SQ;
	}

	public void setSQ(String sQ) {
		SQ = sQ;
	}

	public String getSF() {
		return SF;
	}

	public void setSF(String sF) {
		SF = sF;
	}

	public String getDQ() {
		return DQ;
	}

	public void setDQ(String dQ) {
		DQ = dQ;
	}

	public String getFedLevel() {
		return fedLevel;
	}

	public void setFedLevel(String fedLevel) {
		this.fedLevel = fedLevel;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	public String getXsCode() {
		return xsCode;
	}

	public void setXsCode(String xsCode) {
		this.xsCode = xsCode;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPhone24() {
		return phone24;
	}

	public void setPhone24(String phone24) {
		this.phone24 = phone24;
	}

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}
	@ExcelField(title="机构分类", align=2, sort=30)
	public String getOfficeTypeName() {
		return officeTypeName;
	}

	public void setOfficeTypeName(String officeTypeName) {
		this.officeTypeName = officeTypeName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getUserCourseType() {
		return userCourseType;
	}

	public void setUserCourseType(String userCourseType) {
		this.userCourseType = userCourseType;
	}

	public String getDistrctId() {
		return distrctId;
	}

	public void setDistrctId(String distrctId) {
		this.distrctId = distrctId;
	}
	@ExcelField(title="大区", align=2, sort=40)
	public String getDistrctName() {
		return distrctName;
	}

	public void setDistrctName(String distrctName) {
		this.distrctName = distrctName;
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
	@ExcelField(title="省", align=2, sort=50)
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	@ExcelField(title="市", align=2, sort=60)
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	
}