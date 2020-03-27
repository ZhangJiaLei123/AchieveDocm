/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.entity;

import org.hibernate.validator.constraints.Length;
import com.jeeplus.modules.resource.entity.MesanDir;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 资源目录Entity
 * @author panjp
 * @version 2017-03-19
 */
public class MesanDir extends DataEntity<MesanDir> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 目录名称
	private String sort;		// 排序
	private MesanDir parent;		// 上级目录
	private String parentIds;		// 所有上级目录
	private String upateDateId;//修改时的dateId
	
	

	public String getUpateDateId() {
		return upateDateId;
	}

	public void setUpateDateId(String upateDateId) {
		this.upateDateId = upateDateId;
	}

	public MesanDir() {
		super();
	}

	public MesanDir(String id){
		super(id);
	}

	@Length(min=1, max=200, message="目录名称长度必须介于 1 和 200 之间")
	@ExcelField(title="目录名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="排序", align=2, sort=8)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="上级目录", align=2, sort=9)
	public MesanDir getParent() {
		return parent;
	}

	public void setParent(MesanDir parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="所有上级目录长度必须介于 0 和 2000 之间")
	@ExcelField(title="所有上级目录", align=2, sort=10)
	public String getParentIds() {
		return parentIds;
	}



	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	
}