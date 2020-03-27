/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 岗位级别Entity
 * @author panjp
 * @version 2017-03-19
 */
public class PostLevel extends DataEntity<PostLevel> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 岗位级别名称
	private String postinfoId;		// postInfo
	private int sort;//排序
	
	public PostLevel() {
		super();
	}

	public PostLevel(String id){
		super(id);
	}

	@Length(min=1, max=300, message="岗位级别名称长度必须介于 1 和 300 之间")
	@ExcelField(title="岗位级别名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostinfoId() {
		return postinfoId;
	}

	public void setPostinfoId(String postinfoId) {
		this.postinfoId = postinfoId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	
	
}