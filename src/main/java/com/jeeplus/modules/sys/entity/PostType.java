/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 岗位类别Entity
 * @author ygq
 * @version 2017-04-18
 */
public class PostType extends DataEntity<PostType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 岗位类别名称
	
	public PostType() {
		super();
	}

	public PostType(String id){
		super(id);
	}

	@Length(min=1, max=200, message="岗位类别名称长度必须介于 1 和 200 之间")
	@ExcelField(title="岗位类别名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}