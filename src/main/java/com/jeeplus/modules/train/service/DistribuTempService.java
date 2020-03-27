/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.train.entity.DistribuTemp;
import com.jeeplus.modules.train.dao.DistribuTempDao;

/**
 * 活动目录资源分配中间表Service
 * @author wsp
 * @version 2017-04-03
 */
@Service
@Transactional(readOnly = true)
public class DistribuTempService extends CrudService<DistribuTempDao, DistribuTemp> {

	@Autowired
	private DistribuTempDao distribuTempDao;
	
	public DistribuTemp get(String id) {
		return super.get(id);
	}
	
	public List<DistribuTemp> findList(DistribuTemp distribuTemp) {
		return super.findList(distribuTemp);
	}
	
	public Page<DistribuTemp> findPage(Page<DistribuTemp> page, DistribuTemp distribuTemp) {
		return super.findPage(page, distribuTemp);
	}
	
	@Transactional(readOnly = false)
	public void save(DistribuTemp distribuTemp) {
		super.save(distribuTemp);
	}
	
	@Transactional(readOnly = false)
	public void delete(DistribuTemp distribuTemp) {
		super.delete(distribuTemp);
	}
	/**
	 * 查询章节资源
	 * @param distribuTemp
	 * @return
	 */
	public List<DistribuTemp> findListForDir(DistribuTemp distribuTemp){
		return distribuTempDao.findListForDir(distribuTemp);
	}
	
	@Transactional(readOnly = false)
	public void updaStorce(String id,float score){
		distribuTempDao.updaStorce(id, score);
	}
	
}