/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.District;
import com.jeeplus.modules.sys.entity.ProvinceCity;

/**
 * 区域管理DAO接口
 * @author panjp
 * @version 2017-05-28
 */
@MyBatisDao
public interface DistrictDao extends CrudDao<District> {

	public List<Map> findAllProvinceList();
	
	public List<Map> getCityByProvinceId(String[] ids);
	
	public List<District> findPageList(District district);
	
	public List<Map> findAllCheckedProvinceAndCity(String  districtId);
	
	public void deleteProvinceCity(String districtId);
	
	public List<Map> findAllCityList();
	
	public List<Map> getProinceByDistrictId(String provinceId);
	
	public List<Map> getCityByDistrictId(@Param("districtId")String districtId,@Param("provinceId")String provinceId);
	
	public List<ProvinceCity> findProvCityPageList(ProvinceCity district);
	
}