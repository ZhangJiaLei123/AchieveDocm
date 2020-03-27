/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.resource.entity.DownlondRecord;
import com.jeeplus.modules.resource.dao.DownlondRecordDao;

/**
 * 资料下载记录表Service
 * @author wsp
 * @version 2017-04-10
 */
@Service
@Transactional(readOnly = true)
public class DownlondRecordService extends CrudService<DownlondRecordDao, DownlondRecord> {

	public DownlondRecord get(String id) {
		return super.get(id);
	}
	
	public List<DownlondRecord> findList(DownlondRecord downlondRecord) {
		return super.findList(downlondRecord);
	}
	
	public Page<DownlondRecord> findPage(Page<DownlondRecord> page, DownlondRecord downlondRecord) {
		return super.findPage(page, downlondRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(DownlondRecord downlondRecord) {
		super.save(downlondRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(DownlondRecord downlondRecord) {
		super.delete(downlondRecord);
	}
	
	
	
	
}