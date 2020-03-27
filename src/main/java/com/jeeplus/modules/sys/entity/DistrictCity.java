/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 大区城市Entity
 * @author panjp
 * @version 2017-05-28
 */
public class DistrictCity extends DataEntity<DistrictCity> {
	
	private static final long serialVersionUID = 1L;
	private String districtId;		// 大区ID
	private String provinceId;		// 省份ID
	private String cityId;		// 城市ID
	
	public DistrictCity() {
		super();
	}

	public DistrictCity(String id){
		super(id);
	}

	@Length(min=0, max=64, message="大区ID长度必须介于 0 和 64 之间")
	@ExcelField(title="大区ID", align=2, sort=7)
	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	
	@Length(min=0, max=64, message="省份ID长度必须介于 0 和 64 之间")
	@ExcelField(title="省份ID", align=2, sort=8)
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	@Length(min=0, max=64, message="城市ID长度必须介于 0 和 64 之间")
	@ExcelField(title="城市ID", align=2, sort=9)
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
}