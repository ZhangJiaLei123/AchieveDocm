/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 轮播图Entity
 * @author panjp
 * @version 2017-03-26
 */
public class CarouselImg extends DataEntity<CarouselImg> {
	
	private static final long serialVersionUID = 1L;
	private String titl;		// 标题
	private String url;		// 链接地址
	private String img;		// 图片地址
	private String sort;		// 排序
	
	public CarouselImg() {
		super();
	}

	public CarouselImg(String id){
		super(id);
	}

	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	@ExcelField(title="标题", align=2, sort=7)
	public String getTitl() {
		return titl;
	}

	public void setTitl(String titl) {
		this.titl = titl;
	}
	
	@Length(min=0, max=300, message="链接地址长度必须介于 0 和 300 之间")
	@ExcelField(title="链接地址", align=2, sort=8)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=300, message="图片地址长度必须介于 0 和 300 之间")
	@ExcelField(title="图片地址", align=2, sort=9)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@ExcelField(title="排序", align=2, sort=10)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}