/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.ApprovalRecordDao;
import com.jeeplus.modules.sys.entity.ApprovalRecord;

/**
 * 审批意见Service
 * @author panjp
 * @version 2017-03-20
 */
@Service
@Transactional(readOnly = true)
public class ApprovalRecordService extends CrudService<ApprovalRecordDao, ApprovalRecord> {
	@Autowired
	private ApprovalRecordDao ApprovalRecordDao;
	
	public ApprovalRecord get(String id) {
		return super.get(id);
	}
	public ApprovalRecord findByResourceId(ApprovalRecord appr) {
		return  ApprovalRecordDao.findByResourceId(appr);
	}
	
	
	public List<ApprovalRecord> findList(ApprovalRecord approvalRecord) {
		return super.findList(approvalRecord);
	}
	
	public Page<ApprovalRecord> findPage(Page<ApprovalRecord> page, ApprovalRecord approvalRecord) {
		return super.findPage(page, approvalRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ApprovalRecord approvalRecord,String resourceId) {
		ApprovalRecordDao.deleteApproByResId(resourceId);
		ApprovalRecordDao.insertNew(approvalRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(ApprovalRecord approvalRecord) {
		super.delete(approvalRecord);
	}
	
	
	
}