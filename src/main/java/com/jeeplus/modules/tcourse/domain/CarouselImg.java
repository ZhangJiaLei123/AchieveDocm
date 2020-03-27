package com.jeeplus.modules.tcourse.domain;

import com.yfhl.commons.domain.BizEntity;

/**
 * 轮播图
 * @author Panjp
 *
 */
public class CarouselImg extends BizEntity{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;
	private String url;
	private String img;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
