/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.sys.dao.DistrictDao;
import com.jeeplus.modules.sys.entity.District;
import com.jeeplus.modules.sys.entity.DistrictCity;
import com.jeeplus.modules.sys.entity.ProvinceCity;

/**
 * 区域管理Service
 * @author panjp
 * @version 2017-05-28
 */
@Service
@Transactional(readOnly = true)
public class DistrictService extends CrudService<DistrictDao, District> {
	@Autowired
	private DistrictDao districtDao;
	@Autowired
	private DistrictCityService districtCityService;
	

	public District get(String id) {
		return super.get(id);
	}
	
	public List<District> findList(District district) {
		return super.findList(district);
	}
	
	public Page<District> findPage(Page<District> page, District district) {
		return super.findPage(page, district);
	}
	public Page<District> findPageList(Page<District> page, District district) {
		district.setPage(page);
		page.setList(districtDao.findPageList(district));
		return page;
	}
	
	public List<Map> findAllCheckedProvinceAndCity(String district){
		return districtDao.findAllCheckedProvinceAndCity(district);
	} 
	@Transactional(readOnly = false)
	public void save(District district) {
		String districtId = "";
		if(!district.getIsNewRecord()){//编辑表单保存
			districtId = district.getId();
			districtDao.deleteProvinceCity(districtId);
		}else{
		   districtId = IdGen.uuid();
		   district.setIsNewRecord(true);
		   district.setId(districtId);
		}
		super.save(district);
		//添加大区城市关系
		String cityIds = district.getCityids();
		if(null!=cityIds && !"".equals(cityIds)){
			String cityId[] = cityIds.split(",");
			for (int i = 0; i < cityId.length; i++) {
				DistrictCity discity = new DistrictCity();
				discity.setIsNewRecord(true);
				discity.setId(IdGen.uuid());
				discity.setDistrictId(districtId);
				discity.setCityId(cityId[i]);
				districtCityService.save(discity);
			}
		}
		
	}
	@Transactional(readOnly = false)
	public void saveDistrictCity(DistrictCity district) {
		districtCityService.save(district);
	}
	
	@Transactional(readOnly = false)
	public void delete(District district) {
		super.delete(district);
	}
	
	public District getDistrictByCode(String code){
		District entity = new District();
		entity.setCode(code);
		List<District> list = super.findList(entity);
		if(null !=list && !list.isEmpty() ){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List<Map> findAllProvinceList(){
		return districtDao.findAllProvinceList();
	} 
	public List<Map> getCityByProvinceId(String ids){
		String s = "";
		if(null!=ids && !"".equals(ids.trim())){
			String str[] = ids.split(",");
			
			return districtDao.getCityByProvinceId(str);
		}else{
			return null;
		}
		
	} 
	/***
	 * 初始化所有的大区省份
	 * @return
	 */
	public Map<String,String> initMapDate(){
		Map<String,String> map = new HashMap<String, String>();
		//初始化大区
		List<District> districtList = findList(new District());
		for (int i = 0; i < districtList.size(); i++) {
			map.put(districtList.get(i).getCode(), districtList.get(i).getId());
			map.put(districtList.get(i).getName(), districtList.get(i).getId());
		}
		//初始化省
		List<Map> lsMapProv =districtDao.findAllProvinceList();
		for (int i = 0; i < lsMapProv.size(); i++) {
			map.put(lsMapProv.get(i).get("proincename").toString(), lsMapProv.get(i).get("provinceid").toString());
		}
		//初始化城市
		List<Map> lsMapCity =districtDao.findAllCityList();
		for (int i = 0; i < lsMapCity.size(); i++) {
			map.put(lsMapCity.get(i).get("cityname").toString(), lsMapCity.get(i).get("cityid").toString());
		}
		return map;
	}
	
	public List<Map> getProinceByDistrictId(String districtId){
		return districtDao.getProinceByDistrictId(districtId);
	}
	public List<Map> getCityByDistrictId(String districtId,String provinceId){
		return districtDao.getCityByDistrictId(districtId,provinceId);
	}
	public Page<ProvinceCity> findProvCityPageList(Page<ProvinceCity> page, ProvinceCity district) {
		district.setPage(page);
		page.setList(districtDao.findProvCityPageList(district));
		return page;
	}
}