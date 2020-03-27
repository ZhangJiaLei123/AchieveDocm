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
import com.jeeplus.common.service.TreeService;
import com.jeeplus.modules.sys.dao.OfficeDao;
import com.jeeplus.modules.sys.dao.OfficeTypeDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.OfficeType;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author jeeplus
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private OfficeTypeDao officeTypeDao;
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}
	public List<Office> findAllList(Office office){
		return officeDao.findList(office);
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		office.setParentIds(office.getParentIds()+"%");
		return dao.findByParentIdsLike(office);
	}
	
	@Transactional(readOnly = true)
	public Office getByCode(String code){
		return dao.getByCode(code);
	}
	
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	public List<Map> selDictList(String dicType){
		return officeDao.selDictList(dicType);
	}
	public int  findUserCountByOfficeId(String officeId){
		return officeDao.findUserCountByOfficeId(officeId);
	}
	
	@Transactional(readOnly = true)
	public List<Office> findListForOverOneThousand(Office office){
//		office.setParentIds(office.getParentIds()+"%");
		return dao.findListForOverOneThousand(office);
	}
	
	public Page<Office> findOfficeListForXx(Page<Office> page, Office office) {
		office.setPage(page);
		page.setList(dao.findOfficeListForXx(office));
		return page;
	}
	
	public Map<String,String> initOfficeType(){
	//初始化机构类别
		Map<String,String> map = new HashMap<String, String>();
		List<OfficeType> lsOfficeType = officeTypeDao.findAllList(new OfficeType());
		for (int i = 0; i < lsOfficeType.size(); i++) {
			map.put(lsOfficeType.get(i).getName(),lsOfficeType.get(i).getId());
		}
		
		List<Office> lsOffice = officeDao.findAllList(new Office());
		for (int i = 0; i < lsOffice.size(); i++) {
			map.put(lsOffice.get(i).getCode(),lsOffice.get(i).getId());
		}
		return map;
	}
}
