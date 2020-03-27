package com.jeeplus.modules.tcourse.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.modules.tcourse.dao.ICustomerMemberDao;
import com.jeeplus.modules.tcourse.domain.CustomerMember;
import com.yfhl.commons.domain.QueryScope;
import com.yfhl.commons.exception.UncheckedException;
import com.yfhl.commons.lang.LangUtil;
import com.yfhl.commons.utils.StringUtils;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年3月13日
 * @Description: 用户账户业务逻辑处理
 */
@Service
@Transactional(readOnly=true)
public class CustomerMemberService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ICustomerMemberDao customerMemberDao;

	
	public CustomerMember getCustomerMemberById(String id) {
		if(StringUtils.isBlank(id)){
			throw new UncheckedException("customerMemberService_getCustomerMemberById_ex_001");
		}
		if(logger.isDebugEnabled()){
			logger.debug(LangUtil.getLU().getMessage("customerMemberService_getCustomerMemberById_msg_001",new Object[]{id}));
		}
		CustomerMember cm = customerMemberDao.getById(id);
		return cm;
	}

	
	public CustomerMember getCustomerMember(CustomerMember paramCM) {
		CustomerMember cm = customerMemberDao.getByEntity(paramCM);
		return cm;
	}
	
	
	@Transactional
	public int modifyCustomerMember(CustomerMember cm) {
		int flag = customerMemberDao.update(cm);
		return flag;
	}

	
	@Transactional
	public int deleteCustomerMemberById(String id) {
		
		CustomerMember cm = customerMemberDao.getById(id);
		cm.setEnabledFlag("2");
		cm.setReadonlyFlag("1");
		cm.setUpdateTime(new Date());
		int flag = customerMemberDao.delete(cm);
		return flag;
		
	}

	
	@Transactional
	public CustomerMember addCustomerMember(CustomerMember cm) {
		customerMemberDao.insert(cm);
		CustomerMember dbCm = customerMemberDao.getById(cm.getId());
		return dbCm;
	}
	
	
	public List<CustomerMember> findListByQueryScope(QueryScope<CustomerMember> qs) {
		List<CustomerMember> list = customerMemberDao.findList(qs);
		return list;
	}
	
	
	public Page<CustomerMember> findPageByQueryScope(QueryScope<CustomerMember> qs) {
		if(qs == null){
			throw new UncheckedException("customerMemberService_findPageByQueryScope_ex_002");
		}
		if(qs.getPage() == null){
			throw new UncheckedException("customerMemberService_findPageByQueryScope_ex_003");
		}
		if(qs.getBizEntity() == null){
			throw new UncheckedException("customerMemberService_findPageByQueryScope_ex_004");
		}
		List<CustomerMember> list = customerMemberDao.findList(qs);
		Page<CustomerMember> page = qs.getPage();
		page.setList(list);
		return page;
	}
}
