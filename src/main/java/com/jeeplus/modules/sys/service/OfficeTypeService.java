/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.OfficeTypeDao;
import com.jeeplus.modules.sys.entity.OfficeType;

/**
 * 机构分类Service
 * @author panjp
 * @version 2017-03-25
 */
@Service
@Transactional(readOnly = true)
public class OfficeTypeService extends CrudService<OfficeTypeDao, OfficeType> {
	@Autowired
	private OfficeTypeDao officeTypeDao;
	
	public OfficeType get(String id) {
		return super.get(id);
	}
	
	public List<OfficeType> findList(OfficeType officeType) {
		return super.findList(officeType);
	}
	
	public Page<OfficeType> findPage(Page<OfficeType> page, OfficeType officeType) {
		return super.findPage(page, officeType);
	}
	
	@Transactional(readOnly = false)
	public void save(OfficeType officeType) {
		super.save(officeType);
	}
	
	@Transactional(readOnly = false)
	public void delete(OfficeType officeType) {
		super.delete(officeType);
	}
	
	
	public List<OfficeType> findChildrenOfficeType(OfficeType officeType){
		return officeTypeDao.findChildrenOfficeType(officeType);
	}
	
	public List<Map> findOfficeByTypeId(String typeId){
		return officeTypeDao.findOfficeByTypeId(typeId);
	}
	/***
	 * 查询组织类别树
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月22日 上午10:11:08
	  * @return
	 */
	public List<Map> findTreeOffice(){
		return officeTypeDao.findTreeOffice();
	}
	
	
}