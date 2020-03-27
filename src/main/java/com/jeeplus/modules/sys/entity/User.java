/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户Entity
 * @author jeeplus
 * @version 2013-12-05
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;
	private Office company;	// 归属公司
	private Office office;	// 归属部门
	private String loginName;// 登录名
	private String password;// 密码
	private String no;		// 工号
	private String name;	// 姓名
	private String email;	// 邮箱
	private String phone;	// 电话
	private String mobile;	// 手机
	private String userType;// 用户类型
	private String loginIp;	// 最后登陆IP
	private Date loginDate;	// 最后登陆日期
	private String loginFlag;	// 是否允许登陆
	private String photo;	// 头像
	private String qrCode;	//二维码
	private String oldLoginName;// 原登录名
	private String newPassword;	// 新密码
	private double gradeScore;
	
	private String oldLoginIp;	// 上次登陆IP
	private Date oldLoginDate;	// 上次登陆日期
	
	private String officeName;//组织名称
	private String officeTypeName;//组织分类名称
	private String officeCode;//组织编码
	private String idNum;//身份证号
	private Date entryDate;//入职日期
	private Date filingDate;//备案日期
	private String remarks;//备注
	private String officeType;//机构分类
	private String isAllOffice;//是否全选
	private String postName;//岗位名称
	private String postTypeName;
	private String postLevelName;
	private String familyName;//名族
	private String officeId;//组织机构ID
	private String postInfoStr;//岗位信息
	private String roleName;//权限类型
	private String roleEnName;//角色英文名称
	private String shStatus;//审核状态
	private String sex;//性别
	private String userActivityId;
	
	private List<UserPost> lsUserPost;//用户岗位
	
	
	private Role role;	// 根据角色查询用户条件
	
	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表
	
	private String userId;
	
	private int xxCount;//学习次数
	private String xxTime;//学习时长
	
	private int score;
	
	private String lessionTimeId;
	
	private String verifyUrl;
	
	private String onLineTime;//学员在线时长
	private String courseTime;//学习课程时长
	private String studyTime;//学习活动时长
	private String mesanTime;//浏览资料时长
	private String gradeName;
	
	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}


	public double getGradeScore() {
		return gradeScore;
	}

	public void setGradeScore(double gradeScore) {
		this.gradeScore = gradeScore;
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

	public String getMesanTime() {
		if(mesanTime !=null && !"".equals(mesanTime) && !"0".equals(mesanTime)){
			int t =(int)Float.parseFloat(mesanTime);
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

	public void setMesanTime(String mesanTime) {
		this.mesanTime = mesanTime;
	}

	public String getOnLineTime() {
		if(onLineTime !=null && !"".equals(onLineTime) && !"0".equals(onLineTime)){
			int t =(int)Float.parseFloat(onLineTime);
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

	public void setOnLineTime(String onLineTime) {
		this.onLineTime = onLineTime;
	}

	public String getVerifyUrl() {
		return verifyUrl;
	}

	public void setVerifyUrl(String verifyUrl) {
		this.verifyUrl = verifyUrl;
	}

	public String getLessionTimeId() {
		return lessionTimeId;
	}

	public void setLessionTimeId(String lessionTimeId) {
		this.lessionTimeId = lessionTimeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User() {
		super();
		this.loginFlag = Global.YES;
	}
	
	public User(String id){
		super(id);
	}

	public User(String id, String loginName){
		super(id);
		this.loginName = loginName;
	}

	public User(Role role){
		super();
		this.role = role;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getId() {
		return id;
	}

	@JsonIgnore
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}
	
	@JsonIgnore
	@NotNull(message="归属部门不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min=1, max=100, message="登录名长度必须介于 1 和 100 之间")
	@ExcelField(title="登录名", align=2, sort=30)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@JsonIgnore
	@Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ExcelField(title="姓名", align=2, sort=40)
	public String getName() {
		return name;
	}
	
	@Length(min=1, max=100, message="工号长度必须介于 1 和 100 之间")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Email(message="邮箱格式不正确")
	@Length(min=0, max=200, message="邮箱长度必须介于 1 和 200 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=200, message="电话长度必须介于 1 和 200 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=200, message="手机长度必须介于 1 和 200 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title="备注", align=1, sort=900)
	public String getRemarks() {
		return remarks;
	}
	
	@Length(min=0, max=100, message="用户类型长度必须介于 1 和 100 之间")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldLoginIp() {
		if (oldLoginIp == null){
			return loginIp;
		}
		return oldLoginIp;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOldLoginDate() {
		if (oldLoginDate == null){
			return loginDate;
		}
		return oldLoginDate;
	}

	public void setOldLoginDate(Date oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
	public List<Role> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}
	
	public boolean isAdmin(){
		return isAdmin(this.id);
	}
	
	public static boolean isAdmin(String id){
		return id != null && "1".equals(id);
	}
	
	@Override
	public String toString() {
		return id;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getQrCode() {
		return qrCode;
	}
	@ExcelField(title="组织名称", align=2, sort=50)
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeTypeName() {
		return officeTypeName;
	}

	public void setOfficeTypeName(String officeTypeName) {
		this.officeTypeName = officeTypeName;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(Date filingDate) {
		this.filingDate = filingDate;
	}

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	public String getIsAllOffice() {
		return isAllOffice;
	}

	public void setIsAllOffice(String isAllOffice) {
		this.isAllOffice = isAllOffice;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getPostInfoStr() {
		return postInfoStr;
	}

	public void setPostInfoStr(String postInfoStr) {
		this.postInfoStr = postInfoStr;
	}

	public List<UserPost> getLsUserPost() {
		return lsUserPost;
	}

	public void setLsUserPost(List<UserPost> lsUserPost) {
		this.lsUserPost = lsUserPost;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleEnName() {
		return roleEnName;
	}

	public void setRoleEnName(String roleEnName) {
		this.roleEnName = roleEnName;
	}

	public String getShStatus() {
		return shStatus;
	}

	public void setShStatus(String shStatus) {
		this.shStatus = shStatus;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserActivityId() {
		return userActivityId;
	}

	public void setUserActivityId(String userActivityId) {
		this.userActivityId = userActivityId;
	}

	public String getPostTypeName() {
		return postTypeName;
	}

	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}

	public String getPostLevelName() {
		return postLevelName;
	}

	public void setPostLevelName(String postLevelName) {
		this.postLevelName = postLevelName;
	}

	public int getXxCount() {
		return xxCount;
	}

	public void setXxCount(int xxCount) {
		this.xxCount = xxCount;
	}

	public String getXxTime() {
		if(xxTime !=null && !"".equals(xxTime) && !"0".equals(xxTime)){
			int t =(int)Float.parseFloat(xxTime);
			return (t/3600)+"时"+(t%3600)/60+"分"+((t%3600)%60)+"秒";
		}else{
			return null;
		}
	}

	public void setXxTime(String xxTime) {
		
		this.xxTime = xxTime;
	}
	@ExcelField(title="组织编码", align=2, sort=60)
	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static void main(String[] args) {
		int t = Integer.parseInt("45522");
		System.out.println((t/3600)+"时"+(t%3600)/60+"钟"+((t%3600)%60)+"秒");
	}
	
}