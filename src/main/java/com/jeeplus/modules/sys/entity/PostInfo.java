/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 岗位管理Entity
 * @author panjp
 * @version 2017-03-19
 */
public class PostInfo extends DataEntity<PostInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 岗位名称
	private String postType;		// 岗位类别
	private int countPostLevel;     //岗位级别
	private String posotionLevelName;//岗位级别
	private String postinfoTypeName;//岗位类别名称
	private String positLevelId;//岗位级别id
	private  String postLevelName;//岗位级别名称
	private String checked;//是否选中
	
	
	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getPostLevelName() {
		return postLevelName;
	}

	public void setPostLevelName(String postLevelName) {
		this.postLevelName = postLevelName;
	}

	public PostInfo() {
		super();
	}

	public PostInfo(String id){
		super(id);
	}

	@Length(min=1, max=200, message="岗位名称长度必须介于 1 和 200 之间")
	@ExcelField(title="岗位名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="岗位类型长度必须介于 1 和 64 之间")
	@ExcelField(title="岗位类型", dictType="post_type", align=2, sort=8)
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public int getCountPostLevel() {
		return countPostLevel;
	}

	public void setCountPostLevel(int countPostLevel) {
		this.countPostLevel = countPostLevel;
	}

	public String getPosotionLevelName() {
		return posotionLevelName;
	}

	public void setPosotionLevelName(String posotionLevelName) {
		this.posotionLevelName = posotionLevelName;
	}

	public String getPostinfoTypeName() {
		return postinfoTypeName;
	}

	public void setPostinfoTypeName(String postinfoTypeName) {
		this.postinfoTypeName = postinfoTypeName;
	}

	public String getPositLevelId() {
		return positLevelId;
	}

	public void setPositLevelId(String positLevelId) {
		this.positLevelId = positLevelId;
	}
	
}