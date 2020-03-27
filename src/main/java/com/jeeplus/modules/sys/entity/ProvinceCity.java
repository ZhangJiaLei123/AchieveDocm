/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;



import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 区域管理Entity
 * @author panjp
 * @version 2017-05-28
 */
public class ProvinceCity extends DataEntity<ProvinceCity>{
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private String provinceName;//省份名称
	private String cityName;//城市名称
	
	@ExcelField(title="省", align=2, sort=30)
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	@ExcelField(title="市", align=2, sort=40)
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
	
}