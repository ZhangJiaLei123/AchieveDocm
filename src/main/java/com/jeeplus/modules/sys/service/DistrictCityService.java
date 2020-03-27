/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.DistrictCityDao;
import com.jeeplus.modules.sys.entity.DistrictCity;

/**
 * 大区城市Service
 * @author panjp
 * @version 2017-05-28
 */
@Service
@Transactional(readOnly = true)
public class DistrictCityService extends CrudService<DistrictCityDao, DistrictCity> {

	public DistrictCity get(String id) {
		return super.get(id);
	}
	
	public List<DistrictCity> findList(DistrictCity districtCity) {
		return super.findList(districtCity);
	}
	
	public Page<DistrictCity> findPage(Page<DistrictCity> page, DistrictCity districtCity) {
		return super.findPage(page, districtCity);
	}
	
	@Transactional(readOnly = false)
	public void save(DistrictCity districtCity) {
		super.save(districtCity);
	}
	
	@Transactional(readOnly = false)
	public void delete(DistrictCity districtCity) {
		super.delete(districtCity);
	}
	
	
	
	
}