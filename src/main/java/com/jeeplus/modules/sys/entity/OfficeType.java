/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 机构分类Entity
 * @author panjp
 * @version 2017-03-25
 */
public class OfficeType extends DataEntity<OfficeType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 组织类别名称
	private String parentId;		// 上级ID
	private String parentIds;		// 上级IDS
	private String id;
	private OfficeType parent;
	private String updateDataId;//修改时的数据ID
	
	public String getUpdateDataId() {
		return updateDataId;
	}

	public void setUpdateDataId(String updateDataId) {
		this.updateDataId = updateDataId;
	}

	public OfficeType() {
		super();
	}

	public OfficeType(String id){
		super(id);
	}

	@Length(min=0, max=200, message="组织类别名称长度必须介于 0 和 200 之间")
	@ExcelField(title="组织类别名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="上级ID长度必须介于 0 和 64 之间")
	@ExcelField(title="上级ID", align=2, sort=8)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Length(min=0, max=1000, message="上级IDS长度必须介于 0 和 1000 之间")
	@ExcelField(title="上级IDS", align=2, sort=9)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public void setParent(OfficeType parent) {
		this.parent = parent;
	}

	

	public OfficeType getParent() {
		return parent;
	}

	
}