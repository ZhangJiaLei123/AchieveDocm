/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.train.entity.OfficeActivity;
import com.jeeplus.modules.train.dao.OfficeActivityDao;

/**
 * 组织活动Service
 * @author panjp
 * @version 2017-03-26
 */
@Service
@Transactional(readOnly = true)
public class OfficeActivityService extends CrudService<OfficeActivityDao, OfficeActivity> {
	@Autowired
	private OfficeActivityDao officeActivityDao;
	public OfficeActivity get(String id) {
		return super.get(id);
	}
	
	public List<OfficeActivity> findList(OfficeActivity officeActivity) {
		return super.findList(officeActivity);
	}
	
	public Page<OfficeActivity> findPage(Page<OfficeActivity> page, OfficeActivity officeActivity) {
		return super.findPage(page, officeActivity);
	}
	public Page<OfficeActivity> findOfficeListMap(Page<OfficeActivity> page, OfficeActivity officeActivity) {
		officeActivity.setPage(page);
		page.setList(officeActivityDao.findOfficeListMap(officeActivity));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(OfficeActivity officeActivity) {
		super.save(officeActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(OfficeActivity officeActivity) {
		super.delete(officeActivity);
	}
	
	@Transactional(readOnly = false)
	public void deleteOfficeActivityByActivId(String activId,String type){
		officeActivityDao.deleteOfficeActivityByActivId(activId,type);
	}
	
	public int findUserOfficeTypeCount(String courseId,String type){
		return officeActivityDao.findUserOfficeTypeCount(courseId,type);
	}
	
	public int findUserCount(String courseId,String type){
		return officeActivityDao.findUserCount(courseId,type);
	}
	/***
	 * 查询关联的组织信息
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月5日 上午8:35:54
	  * @param officeActivity
	  * @return
	 */
	public List<OfficeActivity> findOfficeListMap(OfficeActivity officeActivity){
		return officeActivityDao.findOfficeListMap(officeActivity);
	}
}