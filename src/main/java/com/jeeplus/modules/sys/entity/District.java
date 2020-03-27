/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 区域管理Entity
 * @author panjp
 * @version 2017-05-28
 */
public class District extends DataEntity<District> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 区域名称
	private int sort;		// 排序
	private String code;//区域编码
	private String provinceids;//省份ID
	private String cityids;//城市ID
	private String provinceName;//省份名称
	private String cityName;//城市名称
	private List<Map> lsMap;//城市集合
	private String districtCityId;//大区城市ID
	
	public District() {
		super();
	}

	public District(String id){
		super(id);
	}

	@Length(min=1, max=300, message="区域名称长度必须介于 1 和 300 之间")
	@ExcelField(title="大区名称", align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	@ExcelField(title="大区编码", align=2, sort=20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProvinceids() {
		return provinceids;
	}

	public void setProvinceids(String provinceids) {
		this.provinceids = provinceids;
	}

	public String getCityids() {
		return cityids;
	}

	public void setCityids(String cityids) {
		this.cityids = cityids;
	}
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

	public List<Map> getLsMap() {
		return lsMap;
	}

	public void setLsMap(List<Map> lsMap) {
		this.lsMap = lsMap;
	}

	public String getDistrictCityId() {
		return districtCityId;
	}

	public void setDistrictCityId(String districtCityId) {
		this.districtCityId = districtCityId;
	}
	
	
	
}