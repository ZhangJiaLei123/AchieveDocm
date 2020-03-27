/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.ApprovalRecord;

/**
 * 审批意见DAO接口
 * @author panjp
 * @version 2017-03-20
 */
@MyBatisDao
public interface ApprovalRecordDao extends CrudDao<ApprovalRecord> {
	
	public int deleteApproByResId(String resourceId);
	
	public void insertNew(ApprovalRecord approvalRecord);
	
	
	public ApprovalRecord findByResourceId(ApprovalRecord approvalRecord);
	
	public ApprovalRecord findApprovalRecByResourceId(@Param("resourceID")String resourceID);
}